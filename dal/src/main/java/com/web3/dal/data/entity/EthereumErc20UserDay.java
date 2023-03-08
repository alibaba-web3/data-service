package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * erc20 每日活跃用户数
 * </p>
 *
 * @author system
 * @since 2023-03-08
 */
@Getter
@Setter
@TableName("ethereum_erc20_user_day")
public class EthereumErc20UserDay {

    /**
     * id
     */
    private Long id;

    /**
     * 日期
     */
    private LocalDateTime date;

    /**
     * 代币
     */
    private String symbol;

    /**
     * 合约地址
     */
    private String contractAddress;

    /**
     * 合约调用次数
     */
    private Long callCount;

    /**
     * gas 费消耗
     */
    private Long gas;
}
