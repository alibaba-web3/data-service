package com.web3.framework.resouce.ethereum.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ConnectException;

import com.web3.framework.resouce.dingtalk.DingtalkService;
import com.web3.framework.resouce.ethereum.EthereumService;
import com.web3.framework.utils.EnvUtils;
import io.reactivex.disposables.Disposable;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
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
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

/**
 * @Author: smy
 * @Date: 2023/1/11 2:31 PM
 */
@Service
@Slf4j
@Lazy
public class EthereumServiceImpl implements EthereumService {

    private final Web3j httpClient;

    private final Web3j wsClient;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private DingtalkService dingtalkService;

    public EthereumServiceImpl(@Value("${ethereum.node.rpc}") String httpUrl, @Value("${ethereum.node.ws}") String wsUrl, @Value("${spring.profiles.active}") String env) {

        httpClient = Web3j.build(new HttpService(httpUrl));

        if (EnvUtils.isLocal(env)) {
            wsClient = httpClient;
        } else {
            WebSocketService webSocketService = new WebSocketService(wsUrl, true);
            try {
                webSocketService.connect();
            } catch (ConnectException e) {
                log.error("web3j websocket connect error:", e);
            }

            wsClient = Web3j.build(webSocketService);
        }

    }

    @PostConstruct
    public void init() {
        Disposable subscribe = wsClient.transactionFlowable().subscribe((transaction) -> {
            BigDecimal amount = Convert.fromWei(String.valueOf(transaction.getValue()), Unit.ETHER);

            int largeAmount = 10000;

            if (amount.longValue() >= largeAmount) {
                dingtalkService.send(String.format("eth large amount transfer: %s, %s", amount, getEtherScanTransactionUrl(transaction.getHash())));
            }

        }, Throwable::printStackTrace);

        if (!subscribe.isDisposed()) {
            log.info("eth transaction subscribe start");
        }

    }

    @Override
    public Web3j getHttpClient() {
        return httpClient;
    }

    @Override
    public BigInteger getGasPrice() {
        try {
            EthGasPrice ethGasPrice = httpClient.ethGasPrice().send();
            return ethGasPrice.getGasPrice();
        } catch (IOException e) {
            log.error("get gas price error");
            return null;
        }
    }

    @Override
    public String getAddressByEns(String ens) {
        EnsResolver ensResolver = new EnsResolver(httpClient);

        try {
            return ensResolver.resolve(ens);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public String getEnsDomain(String address) {
        EnsResolver ensResolver = new EnsResolver(httpClient);

        try {
            return ensResolver.reverseResolve(address);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public BigInteger getEthWeiBalance(String address, BigInteger blockNumber) throws IOException {
        EthGetBalance ethGetBalance = httpClient.ethGetBalance(address, DefaultBlockParameter.valueOf(blockNumber)).send();

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
        Web3ClientVersion web3ClientVersion = httpClient.web3ClientVersion().send();

        return web3ClientVersion.getWeb3ClientVersion();
    }

    @Override
    public BigInteger getEthWeiBalance(String address) throws IOException {
        // TODO 增加重试机制
        EthGetBalance ethGetBalance = httpClient.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();

        return ethGetBalance.getBalance();
    }

    @Override
    public Integer getAccountType(String address) {
        if (StringUtils.isBlank(address)) {
            return null;
        }
        try {
            Request<?, EthGetCode> ethGetCodeRequest = httpClient.ethGetCode(address, DefaultBlockParameterName.LATEST);
            String code = ethGetCodeRequest.send().getCode();
            // "0x" 开头的结果也有可能是未部署上线的合约账户
            return "0x".equals(code) ? 1 : 2;
        } catch (IOException e) {
            log.error("get accountType error: {}", e.getMessage());
            return null;
        }
    }

    String getEtherScanTransactionUrl(String hash) {
        return "https://etherscan.io/tx/" + hash;
    }
}
