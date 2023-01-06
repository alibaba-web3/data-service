package com.web3.service.Binance;

import java.util.List;

import com.web3.service.Binance.dto.KLineDTO;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
public interface BinanceService {

    /**
     * 获取最近 1500 根 k 线
     *
     * @param symbol   交易对
     * @param interval K线间隔
     * @return
     */
    List<KLineDTO> getKLines(String symbol, String interval);

    String getTickerPrice(String symbol);

}
