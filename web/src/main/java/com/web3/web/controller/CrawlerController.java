package com.web3.web.controller;

import com.web3.crawler.constants.TaskType;
import com.web3.crawler.dto.Task;
import com.web3.crawler.processors.IProcessor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author: smy
 * @Date: 2023/1/17 5:25 PM
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    @Resource
    @Qualifier("price1dProcessor")
    private IProcessor price1dProcessor;

    @GetMapping("/price1d")
    public void doPrice1dProcessor(LocalDateTime scheduleTime) {
        Task task = Task.generate(price1dProcessor, scheduleTime, TaskType.Manual);
        price1dProcessor.execute(task);
    }
}
