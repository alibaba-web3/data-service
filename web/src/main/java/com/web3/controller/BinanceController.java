package com.web3.controller;

import java.util.List;

import com.web3.service.Binance.BinanceApi;
import com.web3.service.Binance.BinanceService;
import com.web3.service.Binance.dto.KLineDTO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
        @RequestParam(defaultValue = "500") Integer limit) {
        return binanceService.getKLines(symbol, interval);
    }

}
