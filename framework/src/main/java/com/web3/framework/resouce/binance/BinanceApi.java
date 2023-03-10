package com.web3.framework.resouce.binance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.web3.framework.resouce.binance.dto.TickerPriceDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * binance 文档：https://binance-docs.github.io/apidocs/spot/cn/#k
 *
 * @Author: mianyun.yt
 * @Date: 2023/1/5
 */
@HttpExchange
public interface BinanceApi {

    @GetExchange("/api/v3/ping")
    Object ping();

    /**
     * 获取k线数据
     */
    @GetExchange("/api/v3/klines")
    ArrayList<ArrayList<BigDecimal>> getKLines(@RequestParam String symbol, @RequestParam String interval,
        @RequestParam(required = false) Long startTime, @RequestParam(required = false) Long endTime,
        @RequestParam(defaultValue = "500") Integer limit);

    /**
     * 获取单个交易对价格
     *
     * @param symbol 交易对，例 ETHUSDT
     */
    @GetExchange("/api/v3/ticker/price")
    TickerPriceDTO getTickerPrice(@RequestParam String symbol);

    /**
     * 获取所有现货交易对价格
     */
    @GetExchange("/api/v3/ticker/price")
    List<TickerPriceDTO> getAllTickerPrice();

}
