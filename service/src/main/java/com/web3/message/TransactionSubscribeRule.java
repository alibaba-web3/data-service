package com.web3.message;

import org.web3j.protocol.core.methods.response.Transaction;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/30
 */
public interface TransactionSubscribeRule {

    void apply(Transaction transaction);

}
