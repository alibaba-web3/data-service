package com.web3.framework.resouce.coinmarketcap.impl;

import java.util.Map;

import com.web3.framework.resouce.coinmarketcap.CoinMarketCapApi;
import com.web3.framework.resouce.coinmarketcap.CoinMarketCapService;
import com.web3.framework.resouce.coinmarketcap.dto.CmcInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/3
 */
@Service
public class CoinMarketCapServiceImpl implements CoinMarketCapService {

    @Resource
    private CoinMarketCapApi coinMarketCapApi;

    @Override
    public CmcInfo getInfoById(String id) {
        Map<String, CmcInfo> map = coinMarketCapApi.getCryptoCurrencyInfo(id, null, null, null, null, null).getData();
        return map.get(id);
    }
}
