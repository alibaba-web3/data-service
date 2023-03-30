package com.web3.message.subscribeRule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.web3.framework.resouce.dingtalk.DingtalkService;
import com.web3.message.TransactionSubscribeRule;
import com.web3.service.tag.AddressTagService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/30
 */
@Slf4j
@Component
public class LargeTransactionSubscribeRule implements TransactionSubscribeRule {

    @Resource
    private DingtalkService dingtalkService;

    @Resource
    private AddressTagService addressTagService;

    @Override
    public void apply(Transaction transaction) {
        BigDecimal amount = Convert.fromWei(String.valueOf(transaction.getValue()), Unit.ETHER);
        int largeAmount = 30000;
        if (amount.longValue() >= largeAmount) {
            List<String> binanceAddressList = addressTagService.listBinanceAddress();
            if (binanceAddressList.contains(transaction.getFrom())) {
                dingtalkService.send(String.format("以太坊大额转入 Binance, 转账数量: %s, %s", amount.setScale(0, RoundingMode.HALF_UP), getEtherScanTransactionUrl(transaction.getHash())));
            } else if (binanceAddressList.contains(transaction.getTo())) {
                dingtalkService.send(String.format("以太坊大额转出 Binance, 转账数量: %s, %s", amount.setScale(0, RoundingMode.HALF_UP), getEtherScanTransactionUrl(transaction.getHash())));
            }
        }
    }

    String getEtherScanTransactionUrl(String hash) {
        return "https://etherscan.io/tx/" + hash;
    }

}
