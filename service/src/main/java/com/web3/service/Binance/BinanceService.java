package com.web3.service.Binance;

import java.util.List;

import com.web3.service.Binance.dto.KLineDTO;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
public interface BinanceService {

    List<KLineDTO> getKLines(String symbol, String interval);

}
