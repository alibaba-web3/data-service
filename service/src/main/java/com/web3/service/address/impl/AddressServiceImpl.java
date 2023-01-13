package com.web3.service.address.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.web3.dal.data.entity.EthereumBalanceLatest;
import com.web3.dal.data.service.EthereumBalanceLatestMapperService;
import com.web3.framework.resouce.binance.BinanceService;
import com.web3.service.address.AddressService;
import com.web3.service.address.dto.AddressProfileDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

/**
 * 地址 service
 *
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final Web3j web3;

    @Resource
    private EthereumBalanceLatestMapperService ethereumBalanceLatestMapperService;

    @Resource
    private BinanceService binanceService;

    public ExecutorService processUpdateExecutor;

    public AddressServiceImpl(@Value("${ethereum.node.rpc}") String nodeRpcUrl) {

        web3 = Web3j.build(new HttpService(nodeRpcUrl));

        processUpdateExecutor = new ThreadPoolExecutor(100, 100, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());
    }

    @Override
    public AddressProfileDTO getProfile(String address) {
        return null;
    }

    @Override
    public String getWeb3ClientVersion() throws IOException {
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();

        return web3ClientVersion.getWeb3ClientVersion();
    }

    @Override
    public BigInteger getEthWeiBalance(String address) throws IOException {
        EthGetBalance ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();

        return ethGetBalance.getBalance();
    }

    public BigInteger getEthWeiBalance(String address, BigInteger blockNumber) throws IOException {
        EthGetBalance ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameter.valueOf(blockNumber)).send();

        return ethGetBalance.getBalance();
    }

    @Override
    public BigDecimal getEthBalance(String address) throws IOException {
        BigInteger wei = getEthWeiBalance(address);

        return Convert.fromWei(String.valueOf(wei), Convert.Unit.ETHER);
    }

    @Override
    public BigDecimal getEthBalance(String address, BigInteger blockNumber) throws IOException {
        BigInteger wei = getEthWeiBalance(address, blockNumber);

        return Convert.fromWei(String.valueOf(wei), Convert.Unit.ETHER);
    }

    @Override
    public void updateLatestBalance(String address) {
        String price = binanceService.getTickerPrice("ETHUSDT");
        updateLatestBalance(address, price);
    }

    public void updateLatestBalance(String address, String price) {
        try {
            BigInteger weiBalance = getEthWeiBalance(address);
            BigDecimal etherBalance = Convert.fromWei(String.valueOf(weiBalance), Convert.Unit.ETHER);
            BigDecimal value = etherBalance.multiply(BigDecimal.valueOf(Float.parseFloat(price)));

            UpdateWrapper<EthereumBalanceLatest> wrapper = new UpdateWrapper<>();
            wrapper.eq("wallet_address", address);
            wrapper.set("amount_raw", weiBalance);
            wrapper.set("amount", etherBalance);
            wrapper.set("amount_usd", value);

            ethereumBalanceLatestMapperService.update(wrapper);
        } catch (Exception e) {
            log.error(String.format("updateLatestBalance address error: %s", address), e);
        }
    }

    @Override
    public void updateLatestBalanceBatch(List<String> addressList) throws InterruptedException {
        String priceStr = binanceService.getTickerPrice("ETHUSDT");
        CountDownLatch countDownLatch = new CountDownLatch(addressList.size());

        for (String address : addressList) {
            processUpdateExecutor.submit(() -> {
                updateLatestBalance(address, priceStr);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

    }

    @Override
    public void updateLatestBalanceBatch() {
        List<EthereumBalanceLatest> list = ethereumBalanceLatestMapperService.listAmountEmpty();

        int count = 0;
        while (!CollectionUtils.isEmpty(list)) {
            List<String> addressList = list.stream().map(EthereumBalanceLatest::getWalletAddress).toList();
            try {
                updateLatestBalanceBatch(addressList);
            } catch (Exception e) {
                log.error("updateLatestBalanceBatch error", e);
            }

            count += list.size();
            log.info("has updated {} address", count);

            list = ethereumBalanceLatestMapperService.listAmountEmpty();
        }

    }
}
