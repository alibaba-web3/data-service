package com.web3.service.address;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.web3.service.address.dto.AddressProfileDTO;
import com.web3.service.address.dto.AddressSearchDTO;

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

    /**
     * 地址搜索
     *
     * @param searchKey 关键词
     * @return 搜索结果
     */
    List<AddressSearchDTO> search(String searchKey);

}
