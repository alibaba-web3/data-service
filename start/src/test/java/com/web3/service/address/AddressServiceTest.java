package com.web3.service.address;

import com.alibaba.fastjson.JSON;
import com.web3.service.address.dto.AddressProfileDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AddressServiceTest {

    @Resource
    private AddressService addressService;

    @Test
    public void getProfile() {
        AddressProfileDTO profile = addressService.getProfile("0x9e53fAD7Cb9343333809A049C322ebC1F72eF544");
        log.info("test address profile: {}", JSON.toJSONString(profile));
    }
}
