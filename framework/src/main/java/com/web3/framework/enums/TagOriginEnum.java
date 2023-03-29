package com.web3.framework.enums;

import lombok.Getter;

@Getter
public enum TagOriginEnum {

    EtherScan("etherScan", "etherScan"),

    User("user", "user");

    private final String name;

    /**
     * Token
     */
    private final String key;

    TagOriginEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }
}
