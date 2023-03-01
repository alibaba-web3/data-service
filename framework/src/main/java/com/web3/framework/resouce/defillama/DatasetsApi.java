package com.web3.framework.resouce.defillama;

import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/28
 */
@HttpExchange
public interface DatasetsApi {

    /**
     * 查询 curve tvl
     *
     * @return tvl 历史数据
     */
    @GetExchange("/temp/protocol-Curve.json")
    HistoryTvlRes getCurveTvlHistory();

    /**
     * 查询 Uniswap tvl
     *
     * @return tvl 历史数据
     */
    @GetExchange("/temp/protocol-Uniswap.json")
    HistoryTvlRes getUniswapTvlHistory();

}
