package com.web3.framework.resouce.blocknative;

/**
 * BlockNative 平台接口
 * 文档：https://docs.blocknative.com/gas-platform
 *
 * @Author: mianyun.yt
 * @Date: 2023/2/2
 */
public interface BlockNativeApi {

    /**
     * 查询区块gas费
     *
     * @return
     */
    Object getBlockPrices();

}
