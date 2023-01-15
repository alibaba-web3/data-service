package com.web3.web.controller;

import com.web3.crawler.dto.Task;
import com.web3.crawler.processors.Price1dProcessor;
import com.web3.service.address.AddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: smy
 * @Date: 2023/1/9 5:08 PM
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private Price1dProcessor price1dProcessor;

    @Resource
    private AddressService addressService;

    @GetMapping("/executePrice1dJob")
    public void executePrice1dJob() {
        Task task = new Task();
        task.setScheduleTime(new Date());
        price1dProcessor.execute(task);
    }

    @GetMapping("/executeFillBalance")
    public void executeFillBalance() {
        addressService.updateLatestBalanceBatch();
    }
}
