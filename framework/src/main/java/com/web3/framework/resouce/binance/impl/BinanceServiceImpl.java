package com.web3.framework.resouce.binance.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.web3.framework.resouce.binance.BinanceApi;
import com.web3.framework.resouce.binance.BinanceService;
import com.web3.framework.resouce.binance.dto.TickerPriceDTO;
import com.web3.framework.resouce.binance.dto.KLineDTO;
import com.web3.framework.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
@Service
public class BinanceServiceImpl implements BinanceService {

    @Resource
    private BinanceApi binanceApi;

    @Override
    public List<KLineDTO> getKlines(String symbol, String interval, Long startTime, Long endTime, Integer limit) {
        ArrayList<ArrayList<BigDecimal>> klines = binanceApi.getKLines(symbol, interval, startTime, endTime, limit);

        if (CollectionUtils.isEmpty(klines)) {
            return null;
        } else {
            return klines.stream().map(this::transform).collect(Collectors.toList());
        }

    }

    @Override
    public List<KLineDTO> getAllKlines(String symbol, String interval) {

        int maxLimit = 1000;

        List<KLineDTO> klineList = getKlines(symbol, interval, null, null, maxLimit);
        List<KLineDTO> result = klineList;

        while (klineList.size() == maxLimit) {
            klineList = getKlines(symbol, interval, null, klineList.get(0).getOpenTimestamp(), maxLimit);
            result.addAll(klineList);
        }
        result.sort(Comparator.comparingLong(KLineDTO::getOpenTimestamp));
        return result;
    }

    KLineDTO transform(ArrayList<BigDecimal> kline) {
        KLineDTO kLineDTO = new KLineDTO();

        kLineDTO.setOpenTime(DateUtils.ofTimestamp(kline.get(0).longValue()));
        kLineDTO.setOpenTimestamp(kline.get(0).longValue());
        kLineDTO.setOpen(kline.get(1));
        kLineDTO.setHeight(kline.get(2));
        kLineDTO.setLow(kline.get(3));
        kLineDTO.setClose(kline.get(4));
        kLineDTO.setVolume(kline.get(5));
        kLineDTO.setCloseTime(DateUtils.ofTimestamp(kline.get(6).longValue()));
        kLineDTO.setCloseTimestamp(kline.get(6).longValue());
        kLineDTO.setTurnover(kline.get(7));
        kLineDTO.setTradingVolume(kline.get(8));
        kLineDTO.setBuyingVolume(kline.get(9));
        kLineDTO.setBuyingTurnover(kline.get(10));

        return kLineDTO;
    }

    @Override
    public String getTickerPrice(String symbol) {
        TickerPriceDTO dto = binanceApi.getTickerPrice(symbol);

        if (dto == null) {
            return null;
        }

        return dto.getPrice();
    }

    @Override
    public List<String> getAllSymbol() {
        List<TickerPriceDTO> tickerPriceDTOList = binanceApi.getAllTickerPrice();

        if (CollectionUtils.isEmpty(tickerPriceDTOList)) {
            return null;
        }

        return tickerPriceDTOList.stream().map(TickerPriceDTO::getSymbol).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllUsdtSymbol() {
        List<TickerPriceDTO> tickerPriceDTOList = binanceApi.getAllTickerPrice();

        if (CollectionUtils.isEmpty(tickerPriceDTOList)) {
            return null;
        }

        return tickerPriceDTOList.stream()
            .map(TickerPriceDTO::getSymbol)
            .filter(symbol -> symbol.contains("USDT"))
            .collect(Collectors.toList());
    }
}
