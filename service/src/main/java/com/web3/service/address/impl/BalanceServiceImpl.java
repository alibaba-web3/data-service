package com.web3.service.address.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.web3.dal.data.entity.AddressChangeTemp;
import com.web3.dal.data.entity.EthereumBlocks;
import com.web3.dal.data.entity.EthereumTraces;
import com.web3.dal.data.entity.EthereumTransactions;
import com.web3.dal.data.service.AddressChangeTempMapperService;
import com.web3.dal.data.service.EthereumBlocksMapperService;
import com.web3.dal.data.service.EthereumTracesMapperService;
import com.web3.dal.data.service.EthereumTransactionsMapperService;
import com.web3.framework.consts.GuavaCacheKeys;
import com.web3.framework.resouce.binance.BinanceService;
import com.web3.framework.resouce.ethereum.EthereumService;
import com.web3.framework.utils.DateUtils;
import com.web3.framework.utils.EnvUtils;
import com.web3.framework.utils.GuavaCacheUtils;
import com.web3.service.address.AddressService;
import com.web3.service.address.BalanceService;
import com.web3.service.address.dto.BalanceChangeAddressInfo;
import com.web3.service.address.param.TransformBalanceReq;
import com.web3.service.file.FileService;
import com.web3.service.file.dto.TraceCsvDTO;
import jakarta.annotation.PostConstruct;
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
    private BinanceService binanceService;

    @Resource
    private AddressChangeTempMapperService addressChangeTempMapperService;

    @Resource
    private EthereumBlocksMapperService ethereumBlocksMapperService;

    @Resource
    private EthereumTransactionsMapperService ethereumTransactionsMapperService;

    @Resource
    private EthereumTracesMapperService ethereumTracesMapperService;

    @Resource
    private FileService fileService;

    @Resource
    private EnvUtils envUtils;

    public ExecutorService processBalanceExecutor;

    public ExecutorService processAddressExecutor;

    public BalanceServiceImpl() {

        processBalanceExecutor = new ThreadPoolExecutor(800, 800, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("get-balance-%d").build());

        processAddressExecutor = new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("get-address-%d").build());
    }

    @PostConstruct
    public void init() {
        if (!envUtils.isLocal()) {
            fillHistoryRecord();
        }

    }

    @Override
    public void addBalanceRecord(LocalDateTime start, LocalDateTime end) throws InterruptedException, ExecutionException {
        List<LocalDateTime> localDateTimeList = DateUtils.getBetweenDate(start, end);

        Map<LocalDateTime, Future<BalanceChangeAddressInfo>> balanceChangeAddressInfoMap = new HashMap<>(localDateTimeList.size());

        for (int i = 0; i < localDateTimeList.size(); i++) {
            LocalDateTime localDateTime = localDateTimeList.get(i);
            // 日维度开始、结束时间
            LocalDateTime s;
            LocalDateTime e;
            if (i == 0) {
                s = localDateTime;
                e = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth() + 1, 0, 0);
            } else {
                s = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0);
                e = s.plusDays(1);
            }

            Future<BalanceChangeAddressInfo> future = processAddressExecutor.submit(() -> getBalanceChangeAddress(s, e));
            balanceChangeAddressInfoMap.put(s, future);
        }

        for (int i = 0; i < localDateTimeList.size(); i++) {

            LocalDateTime localDateTime = localDateTimeList.get(i);

            long record1 = System.currentTimeMillis();
            log.info("start add balance record: {} {} {}", localDateTime, i, localDateTimeList.size());

            LocalDateTime s;

            if (i == 0) {
                s = localDateTime;
            } else {
                s = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0);
            }

            // 当天余额变化的地址
            BalanceChangeAddressInfo balanceChangeAddressInfo = balanceChangeAddressInfoMap.get(s).get();

            Set<String> addressSet = balanceChangeAddressInfo.getAddressSet();
            EthereumBlocks firstBlock = balanceChangeAddressInfo.getFirstBlock();
            EthereumBlocks lastBlock = balanceChangeAddressInfo.getLastBlock();

            if (CollectionUtils.isEmpty(addressSet)) {
                continue;
            }

            long record2 = System.currentTimeMillis();
            log.info("number of address to update: {} {} {}", addressSet.size(), s, (record2 - record1) / 1000);

            List<Future<AddressChangeTemp>> entityFutureList = new ArrayList<>();

            for (String address : addressSet) {
                Future<AddressChangeTemp> future = processBalanceExecutor.submit(
                    () -> {
                        try {
                            BigInteger weiBalance = ethereumService.getEthWeiBalance(address, BigInteger.valueOf(lastBlock.getBlockNumber()));
                            BigDecimal etherBalance = Convert.fromWei(String.valueOf(weiBalance), Convert.Unit.ETHER);
                            AddressChangeTemp addressChangeTemp = new AddressChangeTemp();
                            addressChangeTemp.setTime(firstBlock.getTimestamp());
                            addressChangeTemp.setAmountRaw(new BigDecimal(weiBalance));
                            addressChangeTemp.setAmount(etherBalance);
                            addressChangeTemp.setAddress(address);

                            return addressChangeTemp;
                        } catch (Exception exception) {
                            log.error(String.format("add eth balance record error: %s", address), exception);
                            return null;
                        }
                    }
                );
                entityFutureList.add(future);
            }

            List<AddressChangeTemp> entityList = entityFutureList.stream().map(future -> {
                try {
                    return future.get();
                } catch (Exception exception) {
                    log.error("addressChangeTempMapperService get future error", exception);
                    return null;
                }
            }).filter(Objects::nonNull).toList();

            long record3 = System.currentTimeMillis();
            log.info("get balance future: {}", (record3 - record2) / 1000);

            addressChangeTempMapperService.replaceIntoBatch(entityList);

            balanceChangeAddressInfoMap.remove(s);
            entityFutureList.clear();

            long record4 = System.currentTimeMillis();
            log.info("add address end: {}", (record4 - record3) / 1000);
        }

    }

    @Override
    public void fillHistoryRecord() {
        AddressChangeTemp latest = addressChangeTempMapperService.getLatest();
        LocalDateTime now = LocalDateTime.now();
        try {
            if (latest.getTime().isBefore(now)) {
                addBalanceRecord(latest.getTime(), now);
            }
        } catch (Exception e) {
            log.error("addBalanceRecord error", e);
        }
    }

    @Override
    public BigDecimal transformBalance(TransformBalanceReq req) {
        if (Objects.isNull(req) || StringUtils.isBlank(req.getDealPair())) {
            return new BigDecimal(0);
        }
        String tickerPrice = binanceService.getTickerPrice(req.getDealPair());
        log.info("BalanceService#transformBalance#getTickerPrice dealPair: {}, tickerPrice: {}", req.getDealPair(), tickerPrice);
        if (StringUtils.isBlank(tickerPrice)) {
            return new BigDecimal(0);
        }
        BigDecimal price = new BigDecimal(tickerPrice);
        return req.getCount() == null ? new BigDecimal(0) : price.multiply(req.getCount());
    }

    @Override
    public Map<String, BigDecimal> transformBalance(List<TransformBalanceReq> req) {
        if (CollectionUtils.isEmpty(req)) {
            return null;
        }
        Map<String, BigDecimal> map = new HashMap<>(req.size());
        try {
            CountDownLatch countDownLatch = new CountDownLatch(req.size());
            req.forEach(item -> processBalanceExecutor.submit(() -> {
                map.put(item.getFromAsset(), transformBalance(item));
                countDownLatch.countDown();
            }));
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("BalanceService#batchTransformBalance error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public BigDecimal balanceValueTrend(String address, String assetsType, BigDecimal balanceValue) {
        if (StringUtils.isAnyBlank(address, assetsType)) {
            return new BigDecimal(0);
        }
        String key = GuavaCacheKeys.BALANCE_VALUE_KEY_PREFIX.concat(address).concat(assetsType);
        BigDecimal oldBalanceValue = (BigDecimal)GuavaCacheUtils.get(key);
        GuavaCacheUtils.put(key, balanceValue);
        if (Objects.nonNull(oldBalanceValue)) {
            return balanceValue.subtract(oldBalanceValue);
        }
        return new BigDecimal(0);
    }

    BalanceChangeAddressInfo getBalanceChangeAddress(LocalDateTime start, LocalDateTime end) throws ExecutionException, InterruptedException, TimeoutException {

        BalanceChangeAddressInfo result = new BalanceChangeAddressInfo();

        // 当天余额变化的地址
        Set<String> addressSet = new HashSet<>();
        Future<List<EthereumBlocks>> blocksListFuture = processBalanceExecutor.submit(() -> ethereumBlocksMapperService.list(start, end));
        Future<List<EthereumTransactions>> transactionsListFuture = processBalanceExecutor.submit(() -> ethereumTransactionsMapperService.list(start, end));
        //Future<List<EthereumTraces>> traceListFuture = processBalanceExecutor.submit(() -> ethereumTracesMapperService.list(start, end));

        List<EthereumBlocks> blocksList = blocksListFuture.get(1800, TimeUnit.SECONDS);
        List<EthereumTransactions> transactionsList = transactionsListFuture.get(1800, TimeUnit.SECONDS);
        //List<EthereumTraces> traceList = traceListFuture.get(1800, TimeUnit.SECONDS);

        if (CollectionUtils.isEmpty(blocksList) || CollectionUtils.isEmpty(transactionsList)) {
            log.info("blocks or transactions set is empty {} {} {} {}", start, end, blocksList.size(), transactionsList.size());
            return result;
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
        //traceList.forEach(trace->{
        //    if (StringUtils.isNotEmpty(trace.getFrom())) {
        //        addressSet.add(trace.getFrom());
        //    }
        //    if (StringUtils.isNotEmpty(trace.getTo())) {
        //        addressSet.add(trace.getTo());
        //    }
        //});

        //Set<String> traceList = getTraceAddressList(start);
        //addressSet.addAll(traceList);

        // 升序排序
        blocksList.sort(Comparator.comparing(EthereumBlocks::getTimestamp));
        EthereumBlocks firstBlock = blocksList.get(0);
        EthereumBlocks lastBlock = blocksList.get(blocksList.size() - 1);

        result.setAddressSet(addressSet);
        result.setFirstBlock(firstBlock);
        result.setLastBlock(lastBlock);

        return result;
    }

    @Override
    public Set<String> getTraceAddressList(LocalDateTime start) {

        List<TraceCsvDTO> traceCsvDTOList = fileService.readTraceCsv();

        Set<String> result = new HashSet<>();

        for (TraceCsvDTO traceCsvDTO : traceCsvDTOList) {
            String date = start.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (Objects.equals(traceCsvDTO.getDate(), date)) {
                result.addAll(traceCsvDTO.getToList());
                result.addAll(traceCsvDTO.getFromList());
            }
        }

        return result;
    }
}
