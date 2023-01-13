package com.web3.framework.resouce.ethereum.impl;

import com.web3.framework.resouce.ethereum.EthereumService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @Author: smy
 * @Date: 2023/1/11 2:31 PM
 */
@Service
public class EthereumServiceImpl implements EthereumService {

    private Web3j web3;
    @Value("${ethereum.node.rpc}")
    private String nodeRpcUrl;

    public EthereumServiceImpl() {
        this.web3 = Web3j.build(new HttpService(nodeRpcUrl));
    }


    @Override
    public Web3j getWeb3j() {
        return web3;
    }
}
