package com.web3.web.controller;

import java.math.BigInteger;

import com.web3.framework.resouce.ethereum.EthereumService;
import com.web3.web.entity.ResultUtils;
import com.web3.web.entity.vo.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/2
 */
@RestController
@RequestMapping("/api/ethereum")
public class EthereumController {

    @Resource
    private EthereumService ethereumService;

    @GetMapping("/gasPrice")
    Result<BigInteger> getGasPrice() {
        return ResultUtils.createSuccessRes(ethereumService.getGasPrice());
    }

}
