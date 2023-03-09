package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author fuxian
 * @Date 2023/2/28
 */
@Getter
@Setter
@TableName("protocol_profit")
public class ProtocolProfit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 协议名称
     */
    private String protocol;

    /**
     * 协议分类
     */
    private String category;

    /**
     * 协议 Token
     */
    private String symbol;

    /**
     * 协议收益
     */
    private BigDecimal protocolRevenue;

    /**
     * 协议总 gas 费
     */
    private BigDecimal totalFees;

    /**
     * 日期
     */
    private Date date;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

}
