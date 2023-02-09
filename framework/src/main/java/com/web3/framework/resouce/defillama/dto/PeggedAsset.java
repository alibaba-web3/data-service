package com.web3.framework.resouce.defillama.dto;

import java.util.List;

import lombok.Data;

/**
 * @Author: mianyun.yt
 * @Date: 2023/2/9
 */
@Data
public class PeggedAsset {

    public String id;

    public String name;

    public String symbol;
    public String gecko_id;
    public String pegType;
    public String priceSource;
    public String pegMechanism;
    public CirculatingPegged circulating;
    public CirculatingPegged circulatingPrevDay;
    public CirculatingPegged circulatingPrevWeek;
    public CirculatingPegged circulatingPrevMonth;

    public List<String> chains;
    public double price;

}
