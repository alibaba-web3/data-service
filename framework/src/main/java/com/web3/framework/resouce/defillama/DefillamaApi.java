package com.web3.framework.resouce.defillama;

import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/3
 */
@HttpExchange
public interface DefillamaApi {

    /**
     * 查询 tvl 历史数据，日维度
     *
     * @param symbol 币种
     * @return tvl
     */
    @GetExchange("/protocol/{symbol}")
    HistoryTvlRes getHistoryTvl(@PathVariable("symbol") String symbol);

}
