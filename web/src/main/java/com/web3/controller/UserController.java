package com.web3.controller;

import com.web3.data.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mianyun.yt
 * @Date: 2022/12/23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/name")
    String getUser() {
        return userService.getName();
    }

}
