package com.web3.service.defi;

import com.web3.framework.resouce.defillama.dto.HistoryTvlRes;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/6
 */
public interface DefiService {

    /**
     * tvl 数据导入数据库
     *
     * @param protocol 协议名
     */
    void syncTvl(String protocol) throws InterruptedException;

    /**
     * 所有协议 tvl 数据导入数据库
     */
    void syncAllProtocolTvl();

    /**
     * 查询协议 tvl
     *
     * @param protocol 协议名
     * @return tvl 历史数据
     */
    HistoryTvlRes getHistoryTvl(String protocol);

}
