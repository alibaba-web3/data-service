package com.web3.framework.resouce.ethereum;

import java.math.BigInteger;

import org.web3j.protocol.Web3j;

/**
 * @Author: smy
 * @Date: 2023/1/11 2:31 PM
 */
public interface EthereumService {

    Web3j getWeb3j();

    /**
     * gas 费查询
     *
     * @return gas 费
     */
    BigInteger getGasPrice();

}
