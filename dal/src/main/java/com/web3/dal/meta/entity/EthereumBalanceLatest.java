package com.web3.dal.meta.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 最新 eth 余额
 * </p>
 *
 * @author system
 * @since 2023-01-11
 */
@Getter
@Setter
@TableName("ethereum_balance_latest")
public class EthereumBalanceLatest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * eth 余额
     */
    private BigDecimal amount;

    /**
     * wei 单位的余额
     */
    private BigDecimal amountRaw;

    /**
     * 美元计价余额
     */
    private BigDecimal amountUsd;
}
