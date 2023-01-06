package com.web3.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
public class DateUtils {

    /**
     * 时间戳转 LocalDateTime
     *
     * @param timestamp 时间戳
     * @return 时间
     */
    static public LocalDateTime ofTimestamp(long timestamp) {
        return ofEpochSecond(timestamp / 1000);
    }

    /**
     * 秒转 LocalDateTime
     *
     * @param epochSecond the number of seconds from the epoch of 1970-01-01T00:00:00Z
     * @return 时间
     */
    static public LocalDateTime ofEpochSecond(long epochSecond) {
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.ofHours(8));
    }

}
