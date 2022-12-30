package com.web3.service.impl;

import com.web3.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: mianyun.yt
 * @Date: 2022/12/23
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getName() {
        return "name";
    }

}
