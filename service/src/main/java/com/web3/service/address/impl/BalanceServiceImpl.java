package com.web3.service.address.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web3.dal.data.entity.AddressChangeTemp;
import com.web3.dal.data.entity.EthereumBlocks;
import com.web3.dal.data.entity.EthereumTransactions;
import com.web3.dal.data.service.AddressChangeTempMapperService;
import com.web3.dal.data.service.EthereumBlocksMapperService;
import com.web3.dal.data.service.EthereumTransactionsMapperService;
import com.web3.framework.resouce.ethereum.EthereumService;
import com.web3.framework.utils.DateUtils;
import com.web3.service.address.AddressService;
import com.web3.service.address.BalanceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.web3j.utils.Convert;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/13
 */
@Service
@Slf4j
public class BalanceServiceImpl implements BalanceService {

    @Resource
    private AddressService addressService;

    @Resource
    private EthereumService ethereumService;

    @Resource
    private AddressChangeTempMapperService addressChangeTempMapperService;

    @Resource
    private EthereumBlocksMapperService ethereumBlocksMapperService;

    @Resource
    private EthereumTransactionsMapperService ethereumTransactionsMapperService;

    public ExecutorService processBalanceExecutor;

    public BalanceServiceImpl() {
        processBalanceExecutor = new ThreadPoolExecutor(100, 100, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());
    }

    @Override
    public void addBalanceRecord(LocalDateTime start, LocalDateTime end) throws InterruptedException, ExecutionException {
        List<LocalDateTime> localDateTimeList = DateUtils.getBetweenDate(start, end);

        for (int i = 0; i < localDateTimeList.size(); i++) {

            LocalDateTime localDateTime = localDateTimeList.get(i);

            log.info("start add balance record: {} {} {}", localDateTime, i, localDateTimeList.size());

            // 日维度开始、结束时间
            LocalDateTime s;
            LocalDateTime e;
            LocalDateTime now = LocalDateTime.now();

            if (i == 0) {
                s = localDateTime;
                e = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth() + 1, 0, 0);
            } else if (i == localDateTimeList.size() - 1) {
                s = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0);
                e = s.plusDays(1);
            } else {
                s = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0);
                e = localDateTime;
            }

            // 当天余额变化的地址
            Set<String> addressSet = new HashSet<>();
            Future<List<EthereumBlocks>> blocksListFuture = processBalanceExecutor.submit(() -> ethereumBlocksMapperService.list(s, e));
            Future<List<EthereumTransactions>> transactionsListFuture = processBalanceExecutor.submit(() -> ethereumTransactionsMapperService.list(s, e));

            List<EthereumBlocks> blocksList = blocksListFuture.get();
            List<EthereumTransactions> transactionsList = transactionsListFuture.get();

            if (CollectionUtils.isEmpty(blocksList) || CollectionUtils.isEmpty(transactionsList)) {
                return;
            }

            blocksList.forEach(block -> {
                if (StringUtils.isNotEmpty(block.getMiner())) {
                    addressSet.add(block.getMiner());
                }
            });
            transactionsList.forEach(transaction -> {
                if (StringUtils.isNotEmpty(transaction.getFrom())) {
                    addressSet.add(transaction.getFrom());
                }
                if (StringUtils.isNotEmpty(transaction.getTo())) {
                    addressSet.add(transaction.getTo());
                }
            });

            // 升序排序
            blocksList.sort(Comparator.comparing(EthereumBlocks::getTimestamp));
            EthereumBlocks firstBlock = blocksList.get(0);
            EthereumBlocks lastBlock = blocksList.get(blocksList.size() - 1);

            log.info("number of address to update: {} {}", addressSet.size(), s);
            CountDownLatch countDownLatch = new CountDownLatch(addressSet.size());

            addressSet.forEach(address -> processBalanceExecutor.submit(() -> {
                try {
                    QueryWrapper<AddressChangeTemp> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("address", address);
                    queryWrapper.eq("time", firstBlock.getTimestamp());
                    queryWrapper.isNotNull("amount_raw");

                    AddressChangeTemp exit = addressChangeTempMapperService.getOne(queryWrapper);

                    if (exit == null) {
                        BigInteger weiBalance = ethereumService.getEthWeiBalance(address, BigInteger.valueOf(lastBlock.getBlockNumber()));
                        BigDecimal etherBalance = Convert.fromWei(String.valueOf(weiBalance), Convert.Unit.ETHER);

                        AddressChangeTemp addressChangeTemp = new AddressChangeTemp();
                        addressChangeTemp.setAddress(address);
                        addressChangeTemp.setTime(firstBlock.getTimestamp());
                        addressChangeTemp.setAmountRaw(new BigDecimal(weiBalance));
                        addressChangeTemp.setAmount(etherBalance);
                        addressChangeTemp.setGmtCreate(now);

                        addressChangeTempMapperService.save(addressChangeTemp);
                    } else if (exit.getTime().isBefore(lastBlock.getTimestamp())) {
                        BigInteger weiBalance = ethereumService.getEthWeiBalance(address, BigInteger.valueOf(lastBlock.getBlockNumber()));
                        BigDecimal etherBalance = Convert.fromWei(String.valueOf(weiBalance), Convert.Unit.ETHER);

                        AddressChangeTemp addressChangeTemp = new AddressChangeTemp();
                        addressChangeTemp.setId(exit.getId());
                        addressChangeTemp.setTime(firstBlock.getTimestamp());
                        addressChangeTemp.setAmountRaw(new BigDecimal(weiBalance));
                        addressChangeTemp.setAmount(etherBalance);

                        addressChangeTempMapperService.updateById(addressChangeTemp);
                    }

                } catch (Exception exception) {
                    log.error(String.format("add eth balance record error: %s", address), exception);
                } finally {
                    countDownLatch.countDown();

                    log.info("number of address has bean updated: {}", addressSet.size() - countDownLatch.getCount());
                }
            }));
            countDownLatch.await();
            addressSet.clear();
        }

    }

}
