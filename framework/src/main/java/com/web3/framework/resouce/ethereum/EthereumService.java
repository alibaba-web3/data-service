package com.web3.framework.resouce.ethereum;

import java.io.IOException;
import java.math.BigDecimal;
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

    /**
     * address 查 ens
     *
     * @param address 地址
     * @return ens 域名
     */
    String getEnsDomain(String address);

    /**
     * ens 转 Address
     *
     * @param ens ens 域名
     * @return Address
     */
    String getAddressByEns(String ens);

    /**
     * 查询 geth 信息
     *
     * @return geth 版本
     */
    String getWeb3ClientVersion() throws IOException;

    /**
     * 查询最新 eth 余额
     *
     * @param address 地址
     * @return eth 余额（wei）
     */
    BigInteger getEthWeiBalance(String address) throws IOException;

    /**
     * 指定区块高度查询 eth 余额
     *
     * @param address 地址
     * @return eth 余额（wei）
     */
    BigInteger getEthWeiBalance(String address, BigInteger blockNumber) throws IOException;

    /**
     * 查询最新 eth 余额
     *
     * @param address 地址
     * @return eth 余额（wei）
     */
    BigDecimal getEthBalance(String address) throws IOException;

    /**
     * 指定区块高度查询 eth 余额
     *
     * @param address 地址
     * @return eth 余额（wei）
     */
    BigDecimal getEthBalance(String address, BigInteger blockNumber) throws IOException;

    /**
     * 获取以太坊账户类型
     *
     * @param address 地址
     * @return Integer 类型(外部-1 | 合约-2)
     */
    Integer getAccountType(String address);

}
