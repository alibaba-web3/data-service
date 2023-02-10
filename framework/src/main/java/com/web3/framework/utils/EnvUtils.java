package com.web3.framework.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/5
 */
@Component
public class EnvUtils {

    @Value("${spring.profiles.active}")
    private String env;

    public Boolean isLocal() {
        return "local".equals(env);
    }

    public Boolean isStage() {
        return "stage".equals(env);
    }

    public Boolean isProd() {
        return "prod".equals(env);
    }

    public String getEnv() {
        return env;
    }

}
