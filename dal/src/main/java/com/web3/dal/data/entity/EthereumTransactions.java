package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("ethereum_transactions")
public class EthereumTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionHash;

    private Integer transactionIndex;

    private Integer blockNumber;

    private String blockHash;

    private LocalDateTime blockTimestamp;

    private String from;

    private String to;

    private Long value;

    private String input;

    private Integer gasUsed;

    private Long gasPrice;

    private Long maxFeePerGas;

    private Long maxPriorityFeePerGas;

    private Long effectiveGasPrice;

    private Long cumulativeGasUsed;

    private Boolean success;

    private Integer nonce;

    private String type;

    private String accessList;
}
