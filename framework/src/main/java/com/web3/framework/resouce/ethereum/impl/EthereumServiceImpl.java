package com.web3.framework.resouce.ethereum.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.web3.framework.resouce.ethereum.EthereumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.ens.EnsResolver;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

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

    @Override
    public String getAddressByEns(String ens) {
        EnsResolver ensResolver = new EnsResolver(web3j);

        try {
            return ensResolver.resolve(ens);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public String getEnsDomain(String address) {
        EnsResolver ensResolver = new EnsResolver(web3j);

        try {
            return ensResolver.reverseResolve(address);
        } catch (RuntimeException e) {
            return null;
        }
    }


    @Override
    public BigInteger getEthWeiBalance(String address, BigInteger blockNumber) throws IOException {
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf(blockNumber)).send();

        return ethGetBalance.getBalance();
    }

    @Override
    public BigDecimal getEthBalance(String address) throws IOException {
        BigInteger wei = getEthWeiBalance(address);

        return Convert.fromWei(String.valueOf(wei), Convert.Unit.ETHER);
    }

    @Override
    public BigDecimal getEthBalance(String address, BigInteger blockNumber) throws IOException {
        BigInteger wei = getEthWeiBalance(address, blockNumber);

        return Convert.fromWei(String.valueOf(wei), Convert.Unit.ETHER);
    }

    @Override
    public String getWeb3ClientVersion() throws IOException {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();

        return web3ClientVersion.getWeb3ClientVersion();
    }

    @Override
    public BigInteger getEthWeiBalance(String address) throws IOException {
        // TODO 增加重试机制
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();

        return ethGetBalance.getBalance();
    }

    @Override
    public Integer getAccountType(String address) {
        if (StringUtils.isBlank(address)) {
            return null;
        }
        try {
            Request<?, EthGetCode> ethGetCodeRequest = web3j.ethGetCode(address, DefaultBlockParameterName.LATEST);
            String code = ethGetCodeRequest.send().getCode();
            // "0x" 开头的结果也有可能是未部署上线的合约账户
            return "0x".equals(code) ? 1 : 2;
        } catch (IOException e) {
            log.error("get accountType error: {}", e.getMessage());
            return null;
        }
    }
}
