package com.web3.controller;

import com.web3.service.Binance.BinanceApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/ping")
    Object ping() {
        return binanceApi.ping();
    }

}
