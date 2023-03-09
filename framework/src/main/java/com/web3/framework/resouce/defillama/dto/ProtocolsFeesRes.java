package com.web3.framework.resouce.defillama.dto;

import com.web3.framework.enums.ProtocolSymbolEnum;
import com.web3.framework.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/28
 */
public class ProtocolsFeesRes implements Serializable {
    /**
     * 协议名称
     */
    private String name;

    /**
     * 协议类型
     */
    private String category;

    /**
     * 协议 Token
     */
    private String symbol;

    /**
     * 数据详情
     */
    private List<List<Long>> totalDataChart;

    /**
     * 协议相关数据
     */
    private List<ProtocolProfitData> protocolProfitData = new ArrayList<>();

    /**
     * 获取协议数据
     *
     * @return List<ProtocolProfitData>
     */
    public List<ProtocolProfitData> getProtocolProfitData(String dataType) {
        if (StringUtils.isBlank(dataType)) {
            return new ArrayList<>();
        }
        if (!CollectionUtils.isEmpty(totalDataChart)) {
            totalDataChart.forEach(item -> {
                ProtocolProfitData profitData = new ProtocolProfitData();
                profitData.setProtocol(name);
                profitData.setCategory(category);
                // 设置 symbol
                profitData.setSymbol(name);
                if ("dailyFees".equals(dataType)) {
                    profitData.setTotalFees(BigDecimal.valueOf(item.get(1)));
                }
                if ("dailyRevenue".equals(dataType)) {
                    profitData.setProtocolRevenue(BigDecimal.valueOf(item.get(1)));
                }
                profitData.setDate(DateUtils.convert2Date(item.get(0)));
                protocolProfitData.add(profitData);
            });
        }
        return protocolProfitData.stream().filter(item -> StringUtils.isNotBlank(item.getSymbol())).toList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<List<Long>> getTotalDataChart() {
        return totalDataChart;
    }

    public void setTotalDataChart(List<List<Long>> totalDataChart) {
        this.totalDataChart = totalDataChart;
    }

    public void setProtocolProfitData(List<ProtocolProfitData> protocolProfitData) {
        this.protocolProfitData = protocolProfitData;
    }

    /**
     * 协议利润数据
     */
    public static class ProtocolProfitData {
        /**
         * 协议
         */
        private String protocol;

        /**
         * 分类
         */
        private String category;

        /**
         * 资产符号
         */
        private String symbol;

        /**
         * 协议利润
         */
        private BigDecimal protocolRevenue;

        /**
         * 协议产生的总费用
         */
        private BigDecimal totalFees;

        /**
         * 日期
         */
        private Date date;

        /**
         * 设置 symbol
         *
         * @param protocol 协议
         */
        public void setSymbol(String protocol) {
            ProtocolSymbolEnum symbolEnum = ProtocolSymbolEnum.getSymbol(protocol);
            if (symbolEnum != null) {
                this.symbol = symbolEnum.getSymbol();
            }
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSymbol() {
            return symbol;
        }

        public BigDecimal getProtocolRevenue() {
            return protocolRevenue;
        }

        public void setProtocolRevenue(BigDecimal protocolRevenue) {
            this.protocolRevenue = protocolRevenue;
        }

        public BigDecimal getTotalFees() {
            return totalFees;
        }

        public void setTotalFees(BigDecimal totalFees) {
            this.totalFees = totalFees;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

}
