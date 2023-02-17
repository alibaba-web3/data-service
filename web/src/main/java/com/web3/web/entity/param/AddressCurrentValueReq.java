package com.web3.web.entity.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author fuxian
 * @Date 2023/2/15
 */
@Data
public class AddressCurrentValueReq implements Serializable {
    /**
     * 地址
     */
    private String address;

    /**
     * 总价值转换的目标货币
     */
    private String targetCurrency;

    /**
     * 链
     */
    private String chain;
}
