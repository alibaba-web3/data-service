package com.web3.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: smy
 * @Date: 2023/1/10 11:23 AM
 */
@Configuration
//开启调度任务，日常不开启
@EnableScheduling
@ConditionalOnProperty(prefix = "scheduled", name = "enable", havingValue = "true")
public class ScheduleConfig {
}
