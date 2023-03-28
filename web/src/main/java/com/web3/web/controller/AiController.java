package com.web3.web.controller;

import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/28
 */
@RestController
public class AiController {

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @GetMapping("/.well-known/ai-plugin.json")
    Object ping() throws NacosException {

        String dataId = "ai-plugin";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);

        return JSON.parse(configService.getConfig(dataId, group, 5000));
    }

}
