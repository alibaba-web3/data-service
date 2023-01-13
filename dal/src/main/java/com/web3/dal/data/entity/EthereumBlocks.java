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
@TableName("ethereum_blocks")
public class EthereumBlocks implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer blockNumber;

    private String blockHash;

    private String parentBlockHash;

    private Integer gasLimit;

    private Integer gasUsed;

    private Long baseFeePerGas;

    private Integer size;

    private String miner;

    private Integer nonce;

    private LocalDateTime timestamp;

    private Integer transactionsCount;
}
