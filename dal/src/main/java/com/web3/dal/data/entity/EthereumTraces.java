package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2023-02-17
 */
@Getter
@Setter
@TableName("ethereum_traces_tmp")
public class EthereumTraces {

    @TableId(value = "trace_id", type = IdType.AUTO)
    private Integer traceId;

    private String traceAddress;

    private Integer traceChildrenCount;

    private Boolean traceSuccess;

    private String transactionHash;

    private Integer transactionIndex;

    private Boolean transactionSuccess;

    private Integer blockNumber;

    private String blockHash;

    private LocalDateTime blockTimestamp;

    private String type;

    @TableField(value = "`from`")
    private String from;

    @TableField(value = "`to`")
    private String to;

    private BigDecimal value;

    private Integer gasLimit;

    private Integer gasUsed;

    private String input;

    private String output;

    private String methodId;

    private String error;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
}
