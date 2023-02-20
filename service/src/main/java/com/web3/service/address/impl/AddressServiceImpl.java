package com.web3.service.address.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.web3.dal.data.entity.EthereumBalanceLatest;
import com.web3.dal.data.entity.EthereumErc20;
import com.web3.dal.data.entity.EthereumErc20BalanceDay;
import com.web3.dal.data.service.EthereumBalanceLatestMapperService;
import com.web3.dal.data.service.EthereumErc20BalanceDayMapperService;
import com.web3.dal.data.service.EthereumErc20MapperService;
import com.web3.framework.resouce.binance.BinanceService;
import com.web3.framework.resouce.ethereum.EthereumService;
import com.web3.framework.utils.StreamUtils;
import com.web3.service.address.AddressService;
import com.web3.service.address.dto.AddressAssetsInfoDTO;
import com.web3.service.address.dto.AddressProfileDTO;
import com.web3.service.address.dto.AddressSearchDTO;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.web3j.crypto.WalletUtils;
import org.web3j.ens.EnsResolver;
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

    @Resource
    private EthereumBalanceLatestMapperService ethereumBalanceLatestMapperService;

    @Resource
    private BinanceService binanceService;

    @Resource
    private EthereumService ethereumService;

    @Resource
    private EthereumErc20MapperService ethereumErc20MapperService;

    @Resource
    private EthereumErc20BalanceDayMapperService ethereumErc20BalanceDayMapperService;

    public ExecutorService processUpdateExecutor;

    @PostConstruct
    public void init() {
        processUpdateExecutor = new ThreadPoolExecutor(0, 100, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());
    }

    @Override
    public AddressProfileDTO getProfile(String address) {

        AddressProfileDTO result = new AddressProfileDTO();
        result.setAddress(address);
        result.setEns(ethereumService.getEnsDomain(address));
        result.setType(ethereumService.getAccountType(address));

        return result;
    }

    @Override
    public void updateLatestBalance(String address) {
        String price = binanceService.getTickerPrice("ETHUSDT");
        updateLatestBalance(address, price);
    }

    public void updateLatestBalance(String address, String price) {
        try {
            BigInteger weiBalance = ethereumService.getEthWeiBalance(address);
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

    @Override
    public List<AddressSearchDTO> search(String searchKey) {
        AddressSearchDTO dto = new AddressSearchDTO();

        if (WalletUtils.isValidAddress(searchKey)) {
            // 根据 Address 搜索
            dto.setAddress(searchKey);
            dto.setEns(ethereumService.getEnsDomain(searchKey));
        } else if (EnsResolver.isValidEnsName(searchKey)) {
            // 根据 ens 搜索
            dto.setAddress(ethereumService.getAddressByEns(searchKey));
            dto.setEns(searchKey);
        } else {
            // 根据 symbol 搜索
            List<EthereumErc20> erc20List = ethereumErc20MapperService.searchBySymbol(searchKey);
            if (!CollectionUtils.isEmpty(erc20List)) {
                return erc20List.stream().map(erc20 -> {
                    AddressSearchDTO addressSearchDTO = new AddressSearchDTO();
                    addressSearchDTO.setSymbol(erc20.getSymbol());
                    addressSearchDTO.setAddress(erc20.getContractAddress());
                    return addressSearchDTO;
                }).toList();
            }
        }

        return Collections.singletonList(dto);
    }

    @Override
    public List<AddressAssetsInfoDTO> getAllAssetsInfo(String address) {
        if (StringUtils.isBlank(address)) {
            return new ArrayList<>();
        }
        List<AddressAssetsInfoDTO> result = new ArrayList<>();
        // 某个地址的所有token & num
        List<EthereumErc20BalanceDay> erc20BalanceDays = ethereumErc20BalanceDayMapperService.queryByAddress(address);
        if (!CollectionUtils.isEmpty(erc20BalanceDays)) {
            List<EthereumErc20BalanceDay> ethereumErc20BalanceDays = erc20BalanceDays
                    .stream()
                    .filter(StreamUtils.distinctByKey(EthereumErc20BalanceDay::getContractAddress))
                    .toList();

            List<EthereumErc20> ethereumErc20s = ethereumErc20MapperService
                    .listByContractAddress(ethereumErc20BalanceDays.stream().map(EthereumErc20BalanceDay::getContractAddress).toList());

            if (!CollectionUtils.isEmpty(ethereumErc20s)) {
                List<EthereumErc20> erc20List = ethereumErc20s.stream().filter(StreamUtils.distinctByKey(EthereumErc20::getContractAddress)).toList();
                for (EthereumErc20BalanceDay erc20BalanceDay : ethereumErc20BalanceDays) {
                    for (EthereumErc20 erc20 : erc20List) {
                        if (erc20BalanceDay.getContractAddress().equals(erc20.getContractAddress())) {
                            AddressAssetsInfoDTO dto = new AddressAssetsInfoDTO();
                            dto.setCount(BigDecimal.valueOf(erc20BalanceDay.getAmountRaw()));
                            dto.setSymbol(erc20.getSymbol());
                            result.add(dto);
                            continue;
                        }
                    }
                }
            }
        }

        return result;
    }
}
