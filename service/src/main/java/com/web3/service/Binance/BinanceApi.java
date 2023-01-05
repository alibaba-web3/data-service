package com.web3.service.Binance;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/5
 */
@HttpExchange
public interface BinanceApi {

    @GetExchange("/api/v3/ping")
    Object ping();

}
