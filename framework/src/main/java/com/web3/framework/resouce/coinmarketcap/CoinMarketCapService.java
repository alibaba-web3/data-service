package com.web3.framework.resouce.coinmarketcap;

import com.web3.framework.resouce.coinmarketcap.dto.CmcInfo;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/3
 */
public interface CoinMarketCapService {

    /**
     * 通过 id 查询代币信息
     *
     * @param id id
     * @return 代币信息
     */
    CmcInfo getInfoById(String id);
}
