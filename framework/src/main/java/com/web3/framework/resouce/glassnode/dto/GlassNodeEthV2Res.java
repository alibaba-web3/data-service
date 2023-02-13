package com.web3.framework.resouce.glassnode.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author fuxian
 * @Date 2023/2/10
 */
@Data
public class GlassNodeEthV2Res {
    /**
     * 时间
     */
    @JsonProperty(value = "t", required = true)
    private Long timestamp;

    /**
     * 值
     */
    @JsonProperty(value = "v", required = true)
    private Object value;
}
