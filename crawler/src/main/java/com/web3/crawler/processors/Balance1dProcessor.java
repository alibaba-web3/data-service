package com.web3.crawler.processors;

import com.web3.crawler.constants.TaskStatus;
import com.web3.crawler.constants.TaskType;
import com.web3.crawler.service.TaskService;
import com.web3.dal.meta.service.CrawlerTaskMapperService;
import com.web3.crawler.annotation.ProcessorConfig;
import com.web3.crawler.dto.Task;
import com.web3.framework.resouce.ethereum.EthereumService;
import com.web3.service.address.BalanceService;
import groovy.lang.Tuple2;
import io.netty.util.concurrent.DefaultThreadFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: smy
 * @Date: 2023/1/12 3:12 PM
 */
@Component
@Slf4j
@ProcessorConfig(name = "Balance1dProcessor")
public class Balance1dProcessor implements IProcessor {


    @Resource
    private BalanceService balanceService;

    @Resource
    private TaskService taskService;

    @Override
    public void execute(Task task) {
        task.setStatus(TaskStatus.Running);
        task.addExtraInfo("begin_time", task.getScheduleTime().plusDays(-1));
        task.addExtraInfo("end_time", task.getScheduleTime());
        taskService.record(task);
        try {
            log.info("start per hour balance task {} ", task);
            _do(task);
            task.setStatus(TaskStatus.Success);
        } catch (Throwable e) {
            task.setStatus(TaskStatus.Failed);
            task.addExtraInfo("error_msg", e.getMessage());
        }
        taskService.record(task);
    }

    public void _do(Task task) throws Exception {
        balanceService.addBalanceRecord(task.getScheduleTime().plusDays(-1), task.getScheduleTime());
    }

}


