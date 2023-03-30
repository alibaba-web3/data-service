package com.web3.message.subscribeRule;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.web3.framework.resouce.dingtalk.DingtalkService;
import com.web3.message.TransactionSubscribeRule;
import lombok.extern.slf4j.Slf4j;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/30
 */
@Slf4j
public class LargeTransactionSubscribeRule implements TransactionSubscribeRule {

    private final DingtalkService dingtalkService;
    private final int largeAmount;

    public LargeTransactionSubscribeRule(DingtalkService dingtalkService, int largeAmount) {
        this.dingtalkService = dingtalkService;
        this.largeAmount = largeAmount;
    }

    @Override
    public void apply(Transaction transaction) {
        BigDecimal amount = Convert.fromWei(String.valueOf(transaction.getValue()), Unit.ETHER);
        if (amount.longValue() >= largeAmount) {
            dingtalkService.send(String.format("以太坊大额转账, 转账数量: %s, %s", amount.setScale(0, RoundingMode.HALF_UP), getEtherScanTransactionUrl(transaction.getHash())));
        }
    }

    String getEtherScanTransactionUrl(String hash) {
        return "https://etherscan.io/tx/" + hash;
    }

}
