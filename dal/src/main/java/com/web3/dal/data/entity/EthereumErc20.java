package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-02-03
 */
@Getter
@Setter
@TableName("ethereum_erc20")
public class EthereumErc20 {

    private String contractAddress;

    private String name;

    private String symbol;

    private Byte decimals;

    private Boolean isStable;

    private String deployer;

    private LocalDateTime deployTime;

    private String description;

    private BigDecimal totalSupply;

    private BigDecimal circulatingSupply;

    private BigDecimal marketCapUsdLatest;

    @TableField(value = "volume_usd_24h")
    private BigDecimal volumeUsd24h;

    private LocalDateTime lastUpdated;
}
