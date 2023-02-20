package com.web3.service.address.param;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @Author fuxian
 * @Date 2023/2/15
 */
public class TransformBalanceReq {
    /**
     * from 资产
     */
    private String fromAsset;

    /**
     * 目标资产
     */
    private String toAsset;

    /**
     * 交易对
     */
    String dealPair;

    /**
     * 数量
     */
    BigDecimal count;

    public String getFromAsset() {
        return fromAsset;
    }

    public void setFromAsset(String fromAsset) {
        this.fromAsset = fromAsset;
    }

    public String getToAsset() {
        return toAsset;
    }

    public void setToAsset(String toAsset) {
        this.toAsset = toAsset;
    }

    public String getDealPair() {
        if (!StringUtils.isAnyBlank(fromAsset, toAsset)) {
            this.dealPair = fromAsset.concat(toAsset);
        }
        return dealPair;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
