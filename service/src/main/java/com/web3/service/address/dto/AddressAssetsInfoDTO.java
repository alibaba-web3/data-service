package com.web3.service.address.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author fuxian
 * @Date 2023/2/15
 */
@Data
public class AddressAssetsInfoDTO {
    /**
     * 资产符号
     */
    private String symbol;

    /**
     * 数量
     */
    private BigDecimal count;
}
