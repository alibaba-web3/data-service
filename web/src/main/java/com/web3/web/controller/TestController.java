package com.web3.web.controller;

import com.web3.framework.crawler.jobs.Price1dJob;
import com.web3.service.address.AddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: smy
 * @Date: 2023/1/9 5:08 PM
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private Price1dJob price1dJob;

    @Resource
    private AddressService addressService;

    @GetMapping("/executePrice1dJob")
    public void executePrice1dJob() {
        price1dJob.execute();
    }

    @GetMapping("/executeFillBalance")
    public void executeFillBalance() {
        addressService.updateLatestBalanceBatch();
    }
}
