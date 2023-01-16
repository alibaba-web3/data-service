package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 地址余额变化中间表
 * </p>
 *
 * @author system
 * @since 2023-01-13
 */
@Getter
@Setter
@TableName("address_change_temp")
public class AddressChangeTemp implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 地址
     */
    private String address;

    /**
     * 区块时间
     */
    private LocalDateTime time;

    /**
     * 余额
     */
    private BigDecimal amount;

    /**
     * wei单位的余额
     */
    private BigDecimal amountRaw;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
