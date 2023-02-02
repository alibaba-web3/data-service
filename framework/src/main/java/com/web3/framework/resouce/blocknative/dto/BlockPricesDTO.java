package com.web3.framework.resouce.blocknative.dto;

import java.util.List;

import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/2
 */
@Data
public class BlockPricesDTO {

    private String system;

    private String network;

    private String unit;

    private Integer maxPrice;

    private Integer currentBlockNumber;

    private Integer msSinceLastBlock;

    private List<BlockPriceDTO> blockPrices;

    @Data
    public static class BlockPriceDTO {

        private Integer blockNumber;

        private Integer estimatedTransactionCount;

        private Float baseFeePerGas;

        private List<EstimatedPriceDTO> estimatedPrices;

    }

    @Data
    public static class EstimatedPriceDTO {

        private Float confidence;

        private Float price;

        private Float maxPriorityFeePerGas;

        private Float maxFeePerGas;

    }
}
