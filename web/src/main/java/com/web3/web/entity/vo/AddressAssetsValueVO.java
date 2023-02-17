package com.web3.web.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author fuxian
 * @Date 2023/2/15
 */
@Data
public class AddressAssetsValueVO implements Serializable {
    /**
     * 地址
     */
    private String address;

    /**
     * 资产总价值
     */
    private BigDecimal value;

    /**
     * 目标货币
     */
    private String targetCurrency;

    /**
     * 实时资产涨跌
     */
    private BigDecimal valueChange;

    /**
     * 日期
     */
    private String date;
}
