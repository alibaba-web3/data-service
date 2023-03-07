package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2023-03-07
 */
@Getter
@Setter
@TableName("ethereum_erc20_event_transfer")
public class EthereumErc20EventTransfer {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String contractAddress;

    private String from;

    private String to;

    private BigDecimal value;

    private Integer blockNumber;

    private LocalDateTime blockTimestamp;

    private Integer transactionIndex;

    private String transactionHash;

    private Integer logIndex;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;
}
