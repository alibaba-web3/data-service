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
 * TVL 日维度数据
 * </p>
 *
 * @author system
 * @since 2023-02-06
 */
@Getter
@Setter
@TableName("tvl_1d")
public class Tvl1d {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 协议名称
     */
    private String name;

    /**
     * 日期
     */
    private LocalDateTime date;

    /**
     * 锁仓量，美元计价
     */
    private BigDecimal tvl;
}
