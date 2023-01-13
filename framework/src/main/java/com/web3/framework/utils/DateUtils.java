package com.web3.framework.utils;

import org.apache.commons.lang3.time.FastTimeZone;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
public class DateUtils {

    public final static String ZERO_TIMEZONE = "GMT+00:00";

    /**
     * 时间戳转 LocalDateTime
     *
     * @param timestamp 时间戳
     * @return 时间
     */
    static public LocalDateTime ofTimestamp(long timestamp) {
        return ofEpochSecond(timestamp / 1000);
    }

    static public long ofLocalDateTime(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.ofHours(8));
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

    public static TimeZone getZeroTimeZone() {
        return FastTimeZone.getGmtTimeZone(ZERO_TIMEZONE);
    }

    public static ZoneId getZeroZoneId() {
        return FastTimeZone.getGmtTimeZone(ZERO_TIMEZONE).toZoneId();
    }

}
