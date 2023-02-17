package com.web3.service.pos.impl;

import com.web3.dal.data.entity.EthereumV2Data;
import com.web3.dal.data.service.EthereumV2MapperService;
import com.web3.framework.resouce.glassnode.GlassNodeApi;
import com.web3.framework.resouce.glassnode.dto.GlassNodeEthV2Res;
import com.web3.framework.utils.DateUtils;
import com.web3.service.pos.EthereumV2Service;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/10
 */
@Slf4j
@Service
public class EthereumV2ServiceImpl implements EthereumV2Service {

    private static final String ETH_ASSETS = "ETH";

    @Resource
    private GlassNodeApi glassNodeApi;

    @Resource
    private EthereumV2MapperService ethereumV2MapperService;

    @Value("${glassnode.api.key}")
    private String glassNodeApiKey;

    @Override
    public void syncActiveValidators() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.activeValidators(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "activeValidators"));
        }
    }

    @Override
    public void syncDepositsCount() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.depositsCount(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "depositsCount"));
        }
    }

    @Override
    public void syncAvgEffectiveBalance() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.avgEffectiveBalance(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "avgEffectiveBalance"));
        }
    }

    @Override
    public void syncEffectiveBalanceSum() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.effectiveBalanceSum(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "effectiveBalanceSum"));
        }
    }

    @Override
    public void syncEpochHeight() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.epochHeight(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "epochHeight"));
        }
    }

    @Override
    public void syncEstAnnualRoiValidator() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.estAnnualRoiValidator(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "estAnnualRoiValidator"));
        }
    }

    @Override
    public void syncParticipationRate() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.participationRate(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "participationRate"));
        }
    }

    @Override
    public void syncTotalDepositsCount() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.stakingTotalDepositsCount(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "totalDepositsCount"));
        }
    }

    @Override
    public void syncTotalValidatorsCount() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.totalValidatorsCount(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "totalValidatorsCount"));
        }
    }

    @Override
    public void syncTotalVolumeSum() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.stakingTotalVolumeSum(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "totalVolumeSum"));
        }
    }

    @Override
    public void syncNewValidatorsCount() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.stakingValidatorsCount(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "newValidatorsCount"));
        }
    }

    @Override
    public void syncNewVolumeSum() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.stakingVolumeSum(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "newVolumeSum"));
        }
    }

    @Override
    public void syncAvgValidatorBalance() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.validatorBalanceMean(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "avgValidatorBalance"));
        }
    }

    @Override
    public void syncVoluntaryExitCount() {
        List<GlassNodeEthV2Res> glassNodeEthV2Res = glassNodeApi.voluntaryExitCount(ETH_ASSETS, glassNodeApiKey);
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            ethereumV2MapperService.batchInsertOrUpdateData(buildData(glassNodeEthV2Res, "voluntaryExitCount"));
        }
    }

    /**
     * 构造数据
     *
     * @param glassNodeEthV2Res
     * @param property
     * @return
     */
    protected static List<EthereumV2Data> buildData(List<GlassNodeEthV2Res> glassNodeEthV2Res, String property) {
        if (!CollectionUtils.isEmpty(glassNodeEthV2Res)) {
            List<EthereumV2Data> list = new ArrayList<>();
            glassNodeEthV2Res.forEach(item -> {
                EthereumV2Data data = new EthereumV2Data();
                data.setTimestamp(DateUtils.convert2Date(item.getTimestamp()));
                try {
                    Field field = data.getClass().getDeclaredField(property);
                    field.setAccessible(true);
                    field.set(data, item.getValue());
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                list.add(data);
            });
            return list;
        }
        return new ArrayList<>();
    }

}
