package com.web3.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastTimeZone;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;
import java.util.TimeZone;

/**
 * @Author: mianyun.yt
 * @Date: 2023/1/6
 */
@Slf4j
public class DateUtils {

    public final static String ZERO_TIMEZONE = "GMT+00:00";

    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转 LocalDateTime
     *
     * @param timestamp 时间戳
     * @return 时间
     */
    static public LocalDateTime ofTimestamp(long timestamp) {
        return ofEpochSecond(timestamp / 1000);
    }

    static public long convert2Timestamp(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.ofHours(8)) * 1000;
    }

    static public Date convert2Date(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        dateFormat.setTimeZone(TimeZone.getTimeZone(ZERO_TIMEZONE));
        try {
            return dateFormat.parse(dateFormat.format(new Date(timestamp * 1000L)));
        } catch (ParseException e) {
            log.error("convert2Date error: {}", e.getMessage());
        }
        return null;
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

    /**
     * 格式化
     *
     * @param date
     * @param format
     * @return
     */
    static public String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * String -> LocalDateTime
     * @param time
     * @param format
     * @return
     */
    static public LocalDateTime parse(String time, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, dateTimeFormatter);
    }

}
