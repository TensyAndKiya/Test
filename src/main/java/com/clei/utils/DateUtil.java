package com.clei.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.concurrent.ConcurrentHashMap;

/**
 * java8 日期时间格式化
 */
public class DateUtil {

    private final static String FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static String FORMATTER_PATTERN_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 默认parse的日期类型
     */
    private final static TemporalQuery<LocalDateTime> QUERY = LocalDateTime::from;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMATTER_PATTERN);

    private final static DateTimeFormatter DATE_TIME_FORMATTER_MS = DateTimeFormatter.ofPattern(FORMATTER_PATTERN_MS);

    private final static ConcurrentHashMap<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap(4);

    private final static ZoneId ZONE_ID = ZoneId.of("GMT+8");

    private final static ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    static {
        FORMATTER_MAP.put(FORMATTER_PATTERN, DATE_TIME_FORMATTER);
        FORMATTER_MAP.put(FORMATTER_PATTERN_MS, DATE_TIME_FORMATTER_MS);
    }

    public static String getDefaultPattern() {
        return FORMATTER_PATTERN;
    }

    /**
     * 返回默认格式的 当前日期时间字符串
     *
     * @return
     */
    public static String currentDateTime() {
        return format(LocalDateTime.now());
    }

    /**
     * 返回指定格式的格式的 当前日期时间字符串
     *
     * @param ms 是否打印毫秒
     * @return
     */
    public static String currentDateTime(boolean ms) {
        return format(LocalDateTime.now(), ms ? DATE_TIME_FORMATTER_MS : DATE_TIME_FORMATTER);
    }

    /**
     * 返回指定格式的格式的 当前日期时间字符串
     *
     * @param pattern 指定日期格式
     * @return
     */
    public static String currentDateTime(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    /**
     * 根据epochMilli返回格式化日期字符串
     *
     * @param millis
     * @return
     */
    public static String formatMillis(long millis) {
        return format(fromMillis(millis));
    }

    /**
     * 根据epochMilli返回格式化日期字符串
     *
     * @param millis
     * @param pattern 指定格式化样例
     * @return
     */
    public static String formatMillis(long millis, String pattern) {
        return format(fromMillis(millis), pattern);
    }

    /**
     * 根据epochMilli返回格式化日期字符串
     *
     * @param millis
     * @param dateTimeFormatter 指定格式化器
     * @return
     */
    public static String formatMillis(long millis, DateTimeFormatter dateTimeFormatter) {
        return format(fromMillis(millis), dateTimeFormatter);
    }

    /**
     * 使用TemporalAccessor增加扩展性
     *
     * @param temporal
     * @return
     */
    public static String format(TemporalAccessor temporal) {
        return format(temporal, DATE_TIME_FORMATTER);
    }

    public static String format(TemporalAccessor temporal, String pattern) {
        validateStr(pattern);
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(pattern);
        if (null != dateTimeFormatter) {
            return format(temporal, dateTimeFormatter);
        } else {
            try {
                dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                String result = format(temporal, dateTimeFormatter);
                //放入格式化器map里
                FORMATTER_MAP.put(pattern, dateTimeFormatter);
                return result;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private static String format(TemporalAccessor temporal, DateTimeFormatter dateTimeFormatter) {
        validateDateTime(temporal);
        return dateTimeFormatter.format(temporal);
    }

    /**
     * 昨日之始
     *
     * @return
     */
    public static LocalDateTime yesterdayBegin() {
        return todayBegin().plusDays(-1);
    }

    /**
     * 今日之始
     *
     * @return
     */
    public static LocalDateTime todayBegin() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 明日之始
     *
     * @return
     */
    public static LocalDateTime tomorrowBegin() {
        return todayBegin().plusDays(1);
    }

    /**
     * 根据epochMilli获取 LocalDateTime
     *
     * @param millis
     * @return
     */
    public static LocalDateTime fromMillis(long millis) {
        validateMilliOrSecond(millis);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZONE_ID);
    }

    /**
     * 根据epochSecond获取 LocalDateTime
     *
     * @param seconds
     * @return
     */
    public static LocalDateTime fromSeconds(long seconds) {
        validateMilliOrSecond(seconds);
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZONE_ID);
    }

    public static LocalDateTime parse(String date) {
        return parse(date, QUERY);
    }


    public static <T> T parse(String date, TemporalQuery<T> query) {
        return parse(date, DATE_TIME_FORMATTER, query);
    }

    public static LocalDateTime parse(String date, String pattern) {
        return parse(date, pattern, QUERY);
    }

    public static <T> T parse(String date, String pattern, TemporalQuery<T> query) {
        validateStr(pattern);
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(pattern);
        if (null != dateTimeFormatter) {
            return parse(date, dateTimeFormatter, query);
        } else {
            try {
                dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                T result = parse(date, dateTimeFormatter, query);
                //放入格式化器map里
                FORMATTER_MAP.put(pattern, dateTimeFormatter);
                return result;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private static <T> T parse(String date, DateTimeFormatter dateTimeFormatter, TemporalQuery<T> query) {
        validateStr(date);
        return dateTimeFormatter.parse(date, query);
    }

    public static Instant toInstant(LocalDateTime localDateTime) {
        validateDateTime(localDateTime);
        return localDateTime.toInstant(ZONE_OFFSET);
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return toInstant(localDateTime).toEpochMilli();
    }

    public static long toEpochSecond(LocalDateTime localDateTime) {
        validateDateTime(localDateTime);
        return localDateTime.toEpochSecond(ZONE_OFFSET);
        // return toInstant(localDateTime).getEpochSecond();
    }

    private static void validateMilliOrSecond(long l) {
        if (l < 0) {
            throw new RuntimeException("传入参数小于0！");
        }
    }

    private static void validateDateTime(TemporalAccessor temporal) {
        if (null == temporal) {
            throw new RuntimeException("传入参数为null！");
        }
    }

    private static void validateStr(String str) {
        if (null == str || "".equals(str)) {
            throw new RuntimeException("传入字符串为" + null == str ? "null！" : "空串！");
        }
    }
}