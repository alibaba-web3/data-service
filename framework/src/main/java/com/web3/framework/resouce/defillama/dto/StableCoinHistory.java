package com.web3.framework.resouce.defillama.dto;

import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/9
 */
@Data
public class StableCoinHistory {
    
    private String date;
    private CirculatingPegged totalCirculating;
    private CirculatingPegged totalUnreleased;
    private CirculatingPegged totalCirculatingUSD;
    private CirculatingPegged totalMintedUSD;
    private CirculatingPegged totalBridgedToUSD;
}
