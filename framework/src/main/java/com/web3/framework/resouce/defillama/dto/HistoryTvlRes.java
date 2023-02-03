
package com.web3.framework.resouce.defillama.dto;

import java.util.List;

import lombok.Data;

/**
 * @author thomsonyang
 */
@Data
public class HistoryTvlRes {

    private List<Object> chains;

    private String cmcId;

    private String description;

    private String geckoId;

    private String id;

    private Boolean isParentProtocol;

    private String logo;

    private String name;

    private List<Tvl> tvl;

    private String twitter;

    private String url;

}
