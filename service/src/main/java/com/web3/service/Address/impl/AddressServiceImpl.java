package com.web3.service.Address.impl;

import java.io.IOException;
import java.math.BigInteger;

import com.web3.service.Address.AddressService;
import com.web3.service.Address.dto.AddressProfileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

/**
 * 地址 service
 *
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final Web3j web3;

    @Value("${ethereum.node.rpc}")
    private String nodeRpcUrl;

    public AddressServiceImpl() {
        this.web3 = Web3j.build(new HttpService(nodeRpcUrl));
    }

    @Override
    public AddressProfileDTO getProfile(String address) {
        return null;
    }

    @Override
    public String getWeb3ClientVersion() throws IOException {
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();

        return web3ClientVersion.getWeb3ClientVersion();
    }

    @Override
    public BigInteger getEthBalance(String address) throws IOException {
        return web3.ethGetBalance(address, null).send().getBalance();
    }
}
