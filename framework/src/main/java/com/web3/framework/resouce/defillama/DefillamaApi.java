package com.web3.framework.resouce.defillama;

import java.util.List;

import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;
import com.web3.framework.resouce.defillama.dto.ProtocolRes;
import com.web3.framework.resouce.defillama.dto.ProtocolsFeesRes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/3
 */
@HttpExchange
public interface DefillamaApi {

    /**
     * 查询所有协议
     *
     * @return 协议列表
     */
    @GetExchange("/protocols")
    List<ProtocolRes> getProtocols();

    /**
     * 查询 tvl 历史数据，日维度
     *
     * @param symbol 币种
     * @return tvl
     */
    @GetExchange("/protocol/{symbol}")
    HistoryTvlRes getHistoryTvl(@PathVariable("symbol") String symbol);

    /**
     * 获取协议利润信息
     *
     * @param protocol 协议
     * @param dataType 数据类型
     * @return
     */
    @GetExchange("/summary/fees/{protocol}")
    ProtocolsFeesRes getProtocolsFees(@PathVariable("protocol") String protocol, @RequestParam(value = "dataType") String dataType);

}
