package com.web3.service.tvl;

import java.io.IOException;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/6
 */
public interface TvlService {

    /**
     * tvl 数据导入数据库
     *
     * @param protocol 协议名
     */
    void sync(String protocol) throws IOException;

}
