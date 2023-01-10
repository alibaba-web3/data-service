package com.web3.web.controller;

import com.web3.framework.resouce.binance.BinanceApi;
import com.web3.framework.resouce.binance.BinanceService;
import com.web3.framework.resouce.binance.dto.KLineDTO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/5
 */
@RestController
@RequestMapping("/api/binance")
public class BinanceController {

    @Resource
    private BinanceApi binanceApi;

    @Resource
    private BinanceService binanceService;

    @GetMapping("/ping")
    Object ping() {
        return binanceApi.ping();
    }

    @GetMapping("/kLines")
    List<KLineDTO> getKLines(@RequestParam String symbol, @RequestParam String interval,
                             @RequestParam(required = false) Long startTime, @RequestParam(required = false) Long endTime,
                             @RequestParam(defaultValue = "500") Integer limit) {
        return binanceService.getKLines(symbol, interval, startTime, endTime, limit);
    }

    @GetMapping("/tickerPrice")
    String getTickerPrice(@RequestParam String symbol) {
        return binanceService.getTickerPrice(symbol);
    }

}
