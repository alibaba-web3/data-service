package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2023-01-09
 */
@Getter
@Setter
@TableName("price_1d")
public class Price1d implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据日期
     */
    private String date;

    /**
     * 数据来源
     */
    private String source;

    /**
     * 交易对
     */
    private String symbol;

    /**
     * k线开盘时间
     */
    private LocalDateTime openTime;

    /**
     * k线收盘时间
     */
    private LocalDateTime closeTime;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 收盘价(当前K线未结束的即为最新价)
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

    @Override
    public int hashCode() {
        return Objects.hash(date, source, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price1d price1d = (Price1d) o;
        return date.equals(price1d.date) && source.equals(price1d.source) && symbol.equals(price1d.symbol);
    }
}
