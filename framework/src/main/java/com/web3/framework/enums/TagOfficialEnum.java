package com.web3.framework.enums;

import lombok.Getter;

/**
 * @Author: mianyun.yt
 * @Date: 2023/3/29
 */
@Getter
public enum TagOfficialEnum {

    OFFICIAL("official", "1"),

    UNOFFICIAL("unofficial", "0");

    private final String name;

    /**
     * Token
     */
    private final String key;

    TagOfficialEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }
}
