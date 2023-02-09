package com.web3.framework.resouce.defillama;

import java.util.List;

import com.web3.framework.resouce.defillama.dto.AllStableCoinRes;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * 稳定币接口
 *
 * @Author: mianyun.yt
 * @Date: 2023/2/9
 */
@HttpExchange
public interface StablecoinApi {

    /**
     * 查询所有稳定币
     *
     * @return 协议列表
     */
    @GetExchange("/stablecoins")
    List<AllStableCoinRes> getAllStablecoin();



}
