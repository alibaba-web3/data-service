package com.web3.crawler.jobs;

import com.web3.service.defi.DefiService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/9
 */
@Service
public class PerDayJob {

    @Resource
    private DefiService defiService;

    /**
     * 每天凌晨 2 点执行
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void execute() {
        defiService.syncAllProtocolTvl();
    }

}
