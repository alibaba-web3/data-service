package com.web3.framework.resouce.coinmarketcap;

import java.util.List;
import java.util.Map;

import com.web3.framework.resouce.coinmarketcap.dto.CmcInfo;
import com.web3.framework.resouce.coinmarketcap.dto.CmcRes;
import com.web3.framework.resouce.defillama.dto.ProtocolRes;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/1
 */
@HttpExchange
public interface CoinMarketCapApi {

    /**
     * 查询代币信息
     * https://coinmarketcap.com/api/documentation/v1/#operation/getV2CryptocurrencyInfo
     *
     * @param id           cmc id
     * @param slug         slug
     * @param symbol       symbol
     * @param address      address
     * @param skip_invalid skip_invalid
     * @param aux          aux
     * @return 基础信息
     */
    @GetExchange("/v2/cryptocurrency/info")
    CmcRes<Map<String, CmcInfo>> getCryptoCurrencyInfo(@RequestParam(required = false) String id, @RequestParam(required = false) String slug, @RequestParam(required = false) String symbol,
        @RequestParam(required = false) String address, @RequestParam(required = false) Boolean skip_invalid, @RequestParam(required = false) String aux);

}
