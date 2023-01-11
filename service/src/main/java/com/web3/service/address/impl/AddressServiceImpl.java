package com.web3.service.address.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.web3.service.address.AddressService;
import com.web3.service.address.dto.AddressProfileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

/**
 * 地址 service
 *
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final Web3j web3;

    public AddressServiceImpl(@Value("${ethereum.node.rpc}") String nodeRpcUrl) {
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
    public BigInteger getEthWeiBalance(String address) throws IOException {
        EthGetBalance ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();

        return ethGetBalance.getBalance();
    }

    @Override
    public BigDecimal getEthBalance(String address) throws IOException {
        BigInteger wei = getEthWeiBalance(address);

        return Convert.fromWei(String.valueOf(wei), Convert.Unit.ETHER);
    }
}
