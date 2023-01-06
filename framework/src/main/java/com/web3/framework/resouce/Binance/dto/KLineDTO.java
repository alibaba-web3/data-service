package com.web3.framework.resouce.Binance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
@Data
public class KLineDTO {

    /**
     * k 线开始时间
     */
    private LocalDateTime openTime;

    /**
     * k 线开始时间戳
     */
    private Long openTimestamp;

    /**
     * k 线结束时间
     */
    private LocalDateTime closeTime;

    /**
     * k 线结束时间戳
     */
    private Long closeTimestamp;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 最高价
     */
    private BigDecimal height;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 收盘价
     */
    private BigDecimal close;

    /**
     * 成交量
     */
    private BigDecimal volume;

    /**
     * 成交额
     */
    private BigDecimal turnover;

    /**
     * 成交笔数
     */
    private BigDecimal tradingVolume;

    /**
     * 主动买入成交量
     */
    private BigDecimal buyingVolume;

    /**
     * 主动买入成交额
     */
    private BigDecimal buyingTurnover;

}
