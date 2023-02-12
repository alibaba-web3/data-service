
package com.web3.framework.resouce.defillama.dto;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

/**
 * @author thomsonyang
 */
@Data
public class ProtocolRes {

    private Object address;

    private Object auditNote;

    private String audits;

    private String category;

    private String chain;

    private Object chainTvls;

    private List<String> chains;

    private Double change1D;

    private Double change1H;

    private Double change7D;

    private Object cmcId;

    private String description;

    private List<Object> forkedFrom;

    private Object geckoId;

    private String id;

    private Long listedAt;

    private String logo;

    private String module;

    private String name;

    private List<Object> oracles;

    private String slug;

    private String symbol;

    private Double tvl;

    private String twitter;

    private String url;

}
