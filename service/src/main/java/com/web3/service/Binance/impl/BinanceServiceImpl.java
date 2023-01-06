package com.web3.service.Binance.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.web3.common.DateUtils;
import com.web3.service.Binance.BinanceApi;
import com.web3.service.Binance.BinanceService;
import com.web3.service.Binance.dto.KLineDTO;
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
    public List<KLineDTO> getKLines(String symbol, String interval) {
        Integer maxLimit = 1500;
        return getKLines(symbol, interval, maxLimit);
    }

    public List<KLineDTO> getKLines(String symbol, String interval, Integer limit) {
        ArrayList<ArrayList<BigDecimal>> klines = binanceApi.getKLines(symbol, interval, null, null, limit);

        if (CollectionUtils.isEmpty(klines)) {
            return null;
        } else {
            return klines.stream().map(kline -> {
                KLineDTO kLineDTO = new KLineDTO();

                kLineDTO.setOpenTime(DateUtils.ofTimestamp(kline.get(0).longValue()));
                kLineDTO.setOpen(kline.get(1));
                kLineDTO.setHeight(kline.get(2));
                kLineDTO.setLow(kline.get(3));
                kLineDTO.setClose(kline.get(4));
                kLineDTO.setVolume(kline.get(5));
                kLineDTO.setCloseTime(DateUtils.ofTimestamp(kline.get(6).longValue()));
                kLineDTO.setTurnover(kline.get(7));
                kLineDTO.setTradingVolume(kline.get(8));
                kLineDTO.setBuyingVolume(kline.get(9));
                kLineDTO.setBuyingTurnover(kline.get(10));

                return kLineDTO;
            }).collect(Collectors.toList());
        }

    }
}