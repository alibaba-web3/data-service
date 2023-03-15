package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2023-01-13
 */
@Getter
@Setter
@TableName("ethereum_transactions_tmp2")
public class EthereumTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionHash;

    private Integer transactionIndex;

    private Long blockNumber;

    private String blockHash;

    private LocalDateTime blockTimestamp;

    @TableField(value = "`from`")
    private String from;

    @TableField(value = "`to`")
    private String to;

    private BigInteger value;

    private String input;

    private BigInteger gasUsed;

    private BigInteger gasPrice;

    private BigInteger maxFeePerGas;

    private BigInteger maxPriorityFeePerGas;

    private BigInteger effectiveGasPrice;

    private BigInteger cumulativeGasUsed;

    private Boolean success;

    private Integer nonce;

    private String type;

    private String accessList;
}
