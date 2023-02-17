package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * @Author fuxian
 * @Date 2023/2/16
 */
@Getter
@Setter
@TableName("ethereum_erc20_balance_day")
public class EthereumErc20BalanceDay {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String contractAddress;

    private String owner;

    private Double amountRaw;

    private Double amountUsd;

    private Date date;

}
