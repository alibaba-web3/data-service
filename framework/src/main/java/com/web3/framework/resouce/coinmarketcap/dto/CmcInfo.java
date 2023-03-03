
package com.web3.framework.resouce.coinmarketcap.dto;

import java.util.List;

import lombok.Data;

@Data
public class CmcInfo {

    private String category;

    private List<Object> contract_address;

    private String date_added;

    private Object date_launched;

    private String description;

    private Long id;

    private Long is_hidden;

    private String logo;

    private String name;

    private String notice;

    private Object platform;

    private String slug;

    private String subreddit;

    private String symbol;

    private List<String> tags;
}
