package com.web3.service.address.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.web3.dal.data.entity.AddressChangeTemp;
import com.web3.dal.data.entity.EthereumBlocks;
import com.web3.dal.data.entity.EthereumTransactions;
import com.web3.dal.data.service.AddressChangeTempMapperService;
import com.web3.dal.data.service.EthereumBlocksMapperService;
import com.web3.dal.data.service.EthereumTransactionsMapperService;
import com.web3.framework.utils.DateUtils;
import com.web3.service.address.AddressService;
import com.web3.service.address.BalanceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
    private AddressChangeTempMapperService addressChangeTempMapperService;

    @Resource
    private EthereumBlocksMapperService ethereumBlocksMapperService;

    @Resource
    private EthereumTransactionsMapperService ethereumTransactionsMapperService;

    @Override
    public void addBalanceRecord(LocalDateTime start, LocalDateTime end) {
        List<LocalDateTime> localDateTimeList = DateUtils.getBetweenDate(start, end);

        for (int i = 0; i < localDateTimeList.size(); i++) {
            LocalDateTime localDateTime = localDateTimeList.get(i);
            // 日维度开始、结束时间
            LocalDateTime s;
            LocalDateTime e;

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
            List<EthereumBlocks> blocksList = ethereumBlocksMapperService.list(s, e);
            List<EthereumTransactions> transactionsList = ethereumTransactionsMapperService.list(s, e);

            if (!CollectionUtils.isEmpty(blocksList) && !CollectionUtils.isEmpty(transactionsList)) {
                blocksList.forEach(block -> {
                    addressSet.add(block.getMiner());
                });
                transactionsList.forEach(transaction -> {
                    addressSet.add(transaction.getFrom());
                    addressSet.add(transaction.getTo());
                });
            }

            // 升序排序
            blocksList.sort(Comparator.comparing(EthereumBlocks::getTimestamp));
            EthereumBlocks lastBlock = blocksList.get(blocksList.size() - 1);

            addressSet.forEach(address -> {
                try {
                    BigInteger weiBalance = addressService.getEthWeiBalance(address, BigInteger.valueOf(lastBlock.getBlockNumber()));
                    BigDecimal etherBalance = Convert.fromWei(String.valueOf(weiBalance), Convert.Unit.ETHER);

                    AddressChangeTemp addressChangeTemp = new AddressChangeTemp();
                    addressChangeTemp.setAddress(address);
                    addressChangeTemp.setTime(lastBlock.getTimestamp());
                    addressChangeTemp.setAmountRaw(new BigDecimal(weiBalance));
                    addressChangeTemp.setAmount(etherBalance);

                    addressChangeTempMapperService.saveOrUpdate(addressChangeTemp);
                } catch (Exception exception) {
                    log.error(String.format("add eth balance record error: %s", address), e);
                }

            });

        }

    }

}
