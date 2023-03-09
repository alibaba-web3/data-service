package com.web3.crawler.jobs;

import com.web3.service.defi.DefiService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 每天凌晨 3 点执行
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void syncProtocolProfit() {
        List<String> dateTypes = new ArrayList<>();
        dateTypes.add("dailyFees");
        dateTypes.add("dailyRevenue");
        for (String dateType : dateTypes) {
            defiService.syncProtocolProfit(dateType);
        }
    }

}
