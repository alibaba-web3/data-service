package com.web3.crawler.jobs;

import com.web3.service.pos.EthereumV2Service;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author fuxian
 * @Date 2023/2/13
 */
@Slf4j
@Service
public class EthereumV2Job {

    @Resource
    private EthereumV2Service ethereumV2Service;

    /**
     * 每天凌晨 1 点执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void execute() throws InterruptedException, NoSuchMethodException {
        long start = System.currentTimeMillis();
        log.info("EthereumV2Job start...");
        executeAllMethod();
        log.info("EthereumV2Job end... cost: {}", System.currentTimeMillis() - start);
    }

    private void executeAllMethod() {
        ethereumV2Service.syncActiveValidators();
        ethereumV2Service.syncDepositsCount();
        ethereumV2Service.syncAvgEffectiveBalance();
        ethereumV2Service.syncEffectiveBalanceSum();
        ethereumV2Service.syncEpochHeight();
        ethereumV2Service.syncEstAnnualRoiValidator();
        ethereumV2Service.syncParticipationRate();
        ethereumV2Service.syncTotalDepositsCount();
        ethereumV2Service.syncTotalValidatorsCount();
        ethereumV2Service.syncTotalVolumeSum();
        ethereumV2Service.syncNewValidatorsCount();
        ethereumV2Service.syncNewVolumeSum();
        ethereumV2Service.syncAvgValidatorBalance();
        ethereumV2Service.syncVoluntaryExitCount();
    }

}
