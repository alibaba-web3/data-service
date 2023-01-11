package com.web3.service.address;

import java.io.IOException;
import java.math.BigInteger;

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

    String getWeb3ClientVersion() throws IOException;

    /**
     * 查询最新 eth 余额
     *
     * @param address 地址
     * @return eth 余额（wei）
     */
    BigInteger getEthBalance(String address) throws IOException;

}
