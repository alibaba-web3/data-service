package com.web3.framework.resouce.defillama.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/9
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllStableCoinRes {

    private List<PeggedAsset> peggedAssets;

}
