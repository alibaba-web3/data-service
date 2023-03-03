package com.web3.framework.resouce.coinmarketcap.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/3
 */
@Data
public class CmcRes<T> {

    private CmcStatus status;

    private T data;

    @Data
    public static class CmcStatus {
        private LocalDateTime timestamp;
        private Integer error_code;
        private String error_message;
        private String elapsed;
        private String credit_count;
        private String notice;
    }
}
