package com.web3.service.address;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.web3.service.address.dto.AddressProfileDTO;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/3
 */
public interface AddressService {

    /**
     * 地址基本信息
     *
     * @param address 地址
     * @return 基本信息
     */
    AddressProfileDTO getProfile(String address);

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
    public BigInteger getEthWeiBalance(String address, BigInteger blockNumber) throws IOException;

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
     * 更新最新余额表数据
     *
     * @param address 钱包地址
     */
    void updateLatestBalance(String address);

    /**
     * 批量更新余额表数据
     *
     * @param addressList 地址列表
     */
    void updateLatestBalanceBatch(List<String> addressList) throws InterruptedException;

    /**
     * 批量更新余额为空的数据
     */
    void updateLatestBalanceBatch();

}
