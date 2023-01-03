package com.web3.service.Address;

import com.web3.service.Address.dto.AddressProfileDTO;

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

}
