package com.web3.service.defi;

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

}
