package com.web3.dal.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author fuxian
 * @Date 2023/2/10
 */
@Getter
@Setter
@TableName("ethereum_beacon_data")
public class EthereumV2Data implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日期
     */
    private Date timestamp;

    /**
     * 活跃的验证者
     */
    private Integer activeValidators;

    /**
     * 新的32ETH质押存款交易数量
     */
    private Integer depositsCount;

    /**
     * 平均有效余额
     */
    private Double avgEffectiveBalance;

    /**
     * 总有效余额
     */
    private Integer effectiveBalanceSum;

    /**
     * 纪元高度
     */
    private Integer epochHeight;

    /**
     * 验证者预估年度发行投资回报率
     */
    private Double estAnnualRoiValidator;

    /**
     * 参与率
     */
    private Double participationRate;

    /**
     * ETH2存款合约的交易总数
     */
    private Integer totalDepositsCount;

    /**
     * ETH2验证者总数
     */
    private Integer totalValidatorsCount;

    /**
     * ETH2存款合约上的余额
     */
    private Integer totalVolumeSum;

    /**
     * 向ETH2存款合约存入32ETH的新地址数量
     */
    private Integer newValidatorsCount;

    /**
     * 新存入ETH2存款合约的ETH数量
     */
    private Integer newVolumeSum;

    /**
     * 验证者的平均总质押余额
     */
    private Double avgValidatorBalance;

    /**
     * 自愿退出验证者池的验证者总数
     */
    private Integer voluntaryExitCount;
}
