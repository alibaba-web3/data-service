package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("ethereum_traces")
public class EthereumTraces {

    @TableId(value = "trace_id", type = IdType.AUTO)
    private Integer traceId;

    private String traceAddress;

    private Integer traceChildrenCount;

    private Integer traceSuccess;

    private String transactionHash;

    private Integer transactionIndex;

    private Integer transactionSuccess;

    private Integer blockNumber;

    private String blockHash;

    private LocalDateTime blockTimestamp;

    private String type;

    private String from;

    private String to;

    private BigInteger value;

    private Integer gasLimit;

    private Integer gasUsed;

    private String input;

    private String output;

    private String methodId;

    private String error;
}
