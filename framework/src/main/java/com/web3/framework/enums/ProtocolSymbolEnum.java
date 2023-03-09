package com.web3.framework.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Author fuxian
 * @Date 2023/2/28
 */
@Getter
public enum ProtocolSymbolEnum {

    /**
     * 以太坊
     */
    ETHEREUM("Ethereum", "ETH"),

    /**
     * Uniswap
     */
    UNISWAP("Uniswap", "UNI"),

    /**
     * 波场
     */
    TRON("Tron", "TRX"),

    /**
     * 币安智能链
     */
    BSC("BSC", "BSC"),

    /**
     * Lido
     */
    LIDO("Lido", "LDO");

    /**
     * 协议
     */
    private String protocol;

    /**
     * Token
     */
    private String symbol;

    ProtocolSymbolEnum(String protocol, String symbol) {
        this.protocol = protocol;
        this.symbol = symbol;
    }

    /**
     * 获取 symbol
     *
     * @param protocol
     * @return
     */
    public static ProtocolSymbolEnum getSymbol(String protocol) {
        if (StringUtils.isNotBlank(protocol)) {
            ProtocolSymbolEnum[] values = ProtocolSymbolEnum.values();
            for (ProtocolSymbolEnum value : values) {
                if (value.protocol.equals(protocol)) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 获取协议列表
     *
     * @return
     */
    public static List<String> getProtocols() {
        return Arrays.stream(ProtocolSymbolEnum.values()).map(ProtocolSymbolEnum::getProtocol).toList();
    }

}
