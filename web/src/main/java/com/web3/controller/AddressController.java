package com.web3.controller;

import com.web3.entity.vo.AddressProfileVO;
import com.web3.service.Address.AddressService;
import com.web3.service.Address.dto.AddressProfileDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mianyun.yt
 * @Date: 2022/12/23
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("/profile")
    AddressProfileVO getProfile(@RequestParam String address) {
        AddressProfileDTO dto = addressService.getProfile(address);
        if (dto == null) {
            return null;
        }
        AddressProfileVO result = new AddressProfileVO();
        BeanUtils.copyProperties(dto, result);
        return result;
    }

}
