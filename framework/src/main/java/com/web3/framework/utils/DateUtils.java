package com.web3.framework.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

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

    /**
     * 获取两个时间之间的所有日期
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 中间的所有日期（包括开始和结束）
     */
    static public List<LocalDateTime> getBetweenDate(LocalDateTime start, LocalDateTime end) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(start, localDate -> localDate.plusDays(1))
            // 截断无限流，长度为起始时间和结束时间的差+1个
            .limit(ChronoUnit.DAYS.between(start, end) + 1)
            .toList();
    }

}
