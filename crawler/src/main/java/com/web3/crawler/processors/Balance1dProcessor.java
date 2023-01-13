package com.web3.crawler.processors;

import com.web3.crawler.constants.TaskStatus;
import com.web3.crawler.constants.TaskType;
import com.web3.crawler.service.TaskService;
import com.web3.dal.meta.service.CrawlerTaskMapperService;
import com.web3.crawler.annotation.ProcessorConfig;
import com.web3.crawler.dto.Task;
import com.web3.framework.resouce.ethereum.EthereumService;
import groovy.lang.Tuple2;
import io.netty.util.concurrent.DefaultThreadFactory;
import jakarta.annotation.Resource;
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
@ProcessorConfig(name = "Balance1dProcessor")
public class Balance1dProcessor implements IProcessor {


    @Resource
    private TaskService taskService;

    @Override
    public void execute(Task task) {
        task.setStatus(TaskStatus.Running);
        taskService.record(task);
        //TODO balance fetch
        try {
            task.setStatus(TaskStatus.Success);
        } catch (Throwable e) {
            task.setStatus(TaskStatus.Failed);
            task.setExtraInfo(e.getMessage());
        }
        taskService.record(task);
    }

    public void _do(Task task) throws Exception {
        _do(task);
    }

}


