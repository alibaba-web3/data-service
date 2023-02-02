package com.web3.crawler.processors;

import com.web3.crawler.annotation.ProcessorConfig;
import com.web3.crawler.constants.TaskStatus;
import com.web3.crawler.dto.Task;
import com.web3.crawler.service.TaskService;
import com.web3.service.address.BalanceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: smy
 * @Date: 2023/1/12 3:12 PM
 */
@Component("balance1dProcessor")
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
            log.info("start Balance1dProcessor task {} ", task);
            _do(task);
            task.setStatus(TaskStatus.Success);
        } catch (Throwable e) {
            task.setStatus(TaskStatus.Failed);
            task.addExtraInfo("error_msg", e.getMessage());
            log.error("Balance1dProcessor failed, task {}, {}", task, e);
        }
        taskService.record(task);
    }

    public void _do(Task task) throws Exception {
        balanceService.addBalanceRecord(task.getScheduleTime().plusDays(-1), task.getScheduleTime());
    }

}


