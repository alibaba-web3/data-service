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
public class PeggedAsset {

    private String id;

    private String name;

    private String symbol;
    private String gecko_id;
    private String pegType;
    private String priceSource;
    private String pegMechanism;
    private CirculatingPegged circulating;
    //private CirculatingPegged circulatingPrevDay;
    //private CirculatingPegged circulatingPrevWeek;
    //private CirculatingPegged circulatingPrevMonth;

    //private Object chainCirculating;

    private List<String> chains;
    private double price;

}
