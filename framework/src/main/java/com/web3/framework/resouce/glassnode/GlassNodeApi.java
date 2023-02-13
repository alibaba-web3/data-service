package com.web3.framework.resouce.glassnode;

import com.web3.framework.resouce.glassnode.dto.GlassNodeEthV2Res;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

/**
 * @Author: fuxian
 * @Date: 2023/2/10
 */
@HttpExchange
public interface GlassNodeApi {

    /**
     * 活跃的验证者数量
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/active_validators_count")
    List<GlassNodeEthV2Res> activeValidators(@RequestParam String a, @RequestParam String api_key);

    /**
     * 新的 32ETH 质押存款交易数量
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/deposits_count")
    List<GlassNodeEthV2Res> depositsCount(@RequestParam String a, @RequestParam String api_key);

    /**
     * 平均有效余额
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/effective_balance_per_validator_mean")
    List<GlassNodeEthV2Res> avgEffectiveBalance(@RequestParam String a, @RequestParam String api_key);

    /**
     * 总有效余额
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/effective_balance_sum")
    List<GlassNodeEthV2Res> effectiveBalanceSum(@RequestParam String a, @RequestParam String api_key);

    /**
     * 纪元高度
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/epoch_height")
    List<GlassNodeEthV2Res> epochHeight(@RequestParam String a, @RequestParam String api_key);

    /**
     * 验证者预估年度发行投资回报率
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/estimated_annual_issuance_roi_per_validator")
    List<GlassNodeEthV2Res> estAnnualRoiValidator(@RequestParam String a, @RequestParam String api_key);

    /**
     * 参与率
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/participation_rate_mean")
    List<GlassNodeEthV2Res> participationRate(@RequestParam String a, @RequestParam String api_key);

    /**
     * ETH2 存款合约的交易总数
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/staking_total_deposits_count")
    List<GlassNodeEthV2Res> stakingTotalDepositsCount(@RequestParam String a, @RequestParam String api_key);

    /**
     * ETH2 验证者总数
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/staking_total_validators_count")
    List<GlassNodeEthV2Res> totalValidatorsCount(@RequestParam String a, @RequestParam String api_key);

    /**
     * ETH2 存款合约上的余额
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/staking_total_volume_sum")
    List<GlassNodeEthV2Res> stakingTotalVolumeSum(@RequestParam String a, @RequestParam String api_key);

    /**
     * 向 ETH2 存款合约存入 32ETH 的新地址数量
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/staking_validators_count")
    List<GlassNodeEthV2Res> stakingValidatorsCount(@RequestParam String a, @RequestParam String api_key);

    /**
     * 新存入 ETH2 存款合约的ETH数量
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/staking_volume_sum")
    List<GlassNodeEthV2Res> stakingVolumeSum(@RequestParam String a, @RequestParam String api_key);

    /**
     * 验证者的平均总质押余额
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/validator_balance_mean")
    List<GlassNodeEthV2Res> validatorBalanceMean(@RequestParam String a, @RequestParam String api_key);

    /**
     * 自愿退出验证者池的验证者总数
     *
     * @param a
     * @param api_key
     * @return
     */
    @GetExchange("/eth2/voluntary_exit_count")
    List<GlassNodeEthV2Res> voluntaryExitCount(@RequestParam String a, @RequestParam String api_key);
}
