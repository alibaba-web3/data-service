package com.web3.service.pos;

import com.web3.framework.resouce.glassnode.dto.GlassNodeEthV2Res;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/10
 */
public interface EthereumV2Service {

    /**
     * 同步 Ethereum v2 活跃的验证者
     *
     * @return
     */
    void syncActiveValidators();

    /**
     * 同步 Ethereum v2 新的 32ETH 质押存款交易数量
     *
     * @return
     */
    void syncDepositsCount();

    /**
     * 同步 Ethereum v2 平均有效余额
     *
     * @return
     */
    void syncAvgEffectiveBalance();

    /**
     * 同步 Ethereum v2 总有效余额
     *
     * @return
     */
    void syncEffectiveBalanceSum();

    /**
     * 同步 Ethereum v2 纪元高度
     *
     * @return
     */
    void syncEpochHeight();

    /**
     * 同步 Ethereum v2 验证者预估年度发行投资回报率
     *
     * @return
     */
    void syncEstAnnualRoiValidator();

    /**
     * 同步 Ethereum v2 参与率
     *
     * @return
     */
    void syncParticipationRate();

    /**
     * 同步 Ethereum v2 ETH2存款合约的交易总数
     *
     * @return
     */
    void syncTotalDepositsCount();

    /**
     * 同步 Ethereum v2 ETH2验证者总数
     *
     * @return
     */
    void syncTotalValidatorsCount();

    /**
     * 同步 Ethereum v2 ETH2存款合约上的余额
     *
     * @return
     */
    void syncTotalVolumeSum();

    /**
     * 同步 Ethereum v2 向ETH2存款合约存入32ETH的新地址数量
     *
     * @return
     */
    void syncNewValidatorsCount();

    /**
     * 同步 Ethereum v2 新存入ETH2存款合约的ETH数量
     *
     * @return
     */
    void syncNewVolumeSum();

    /**
     * 同步 Ethereum v2 验证者的平均总质押余额
     *
     * @return
     */
    void syncAvgValidatorBalance();

    /**
     * 同步 Ethereum v2 自愿退出验证者池的验证者总数
     *
     * @return
     */
    void syncVoluntaryExitCount();

}
