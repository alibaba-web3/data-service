package com.web3.message.consumer;

import java.util.ArrayList;
import java.util.List;

import com.web3.framework.resouce.dingtalk.DingtalkService;
import com.web3.framework.resouce.ethereum.EthereumService;
import com.web3.message.Consumer;
import com.web3.message.TransactionSubscribeRule;
import com.web3.message.subscribeRule.LargeTransactionSubscribeRule;
import io.reactivex.disposables.Disposable;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/14
 */
@Component
@Slf4j
public class EthereumTransactionConsumer implements Consumer<TransactionSubscribeRule> {

    @Resource
    private EthereumService ethereumService;

    @Resource
    private DingtalkService dingtalkService;

    @PostConstruct
    @Override
    public void consume() {

        Web3j httpClient = ethereumService.getHttpClient();
        List<TransactionSubscribeRule> transactionRules = getSubscribeRules();

        Disposable subscribe = httpClient.transactionFlowable().subscribe((transaction) -> {
            for (TransactionSubscribeRule rule : transactionRules) {
                rule.apply(transaction);
            }
        }, Throwable::printStackTrace);

        if (!subscribe.isDisposed()) {
            log.info("eth transaction subscribe start");
        }

    }

    public List<TransactionSubscribeRule> getSubscribeRules() {
        List<TransactionSubscribeRule> transactionRules = new ArrayList<>();
        transactionRules.add(new LargeTransactionSubscribeRule(dingtalkService, 30000));

        return transactionRules;

    }

}
