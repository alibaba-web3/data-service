package com.web3.framework.resouce.ethereum.impl;

import java.io.IOException;
import java.math.BigInteger;

import com.web3.framework.resouce.ethereum.EthereumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.http.HttpService;

/**
 * @Author: smy
 * @Date: 2023/1/11 2:31 PM
 */
@Service
@Slf4j
public class EthereumServiceImpl implements EthereumService {

    private final Web3j web3j;

    public EthereumServiceImpl(@Value("${ethereum.node.rpc}") String nodeRpcUrl) {
        this.web3j = Web3j.build(new HttpService(nodeRpcUrl));
    }

    @Override
    public Web3j getWeb3j() {
        return web3j;
    }

    @Override
    public BigInteger getGasPrice() {
        try {
            EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
            return ethGasPrice.getGasPrice();
        } catch (IOException e) {
            log.error("get gas price error");
            return null;
        }
    }
}
