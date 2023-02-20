package com.web3.framework.resouce.binance;

import java.util.List;

import com.web3.framework.resouce.binance.dto.KLineDTO;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
public interface BinanceService {

    /**
     * 获取最近 1000 根 k 线
     *
     * @param symbol    交易对
     * @param interval  K线间隔
     * @param endTime   结束时间
     * @param limit     k 线数量
     * @param startTime 开始时间
     * @return k 线
     */
    List<KLineDTO> getKlines(String symbol, String interval, Long startTime, Long endTime, Integer limit);

    /**
     * 查询所有k线数据
     *
     * @param symbol   交易对
     * @param interval k线间隔
     * @return K线
     */
    List<KLineDTO> getAllKlines(String symbol, String interval);

    /**
     * 查询单个交易对价格
     *
     * @param symbol 交易对
     * @return
     */
    String getTickerPrice(String symbol);

    /**
     * 查询所有的交易对(包含 btc、eth 计价的交易对)
     *
     * @return 所有现货交易对
     */
    List<String> getAllSymbol();

    /**
     * 查询所有 USDT 计价的交易对
     *
     * @return 交易对
     */
    List<String> getAllUsdtSymbol();

}
