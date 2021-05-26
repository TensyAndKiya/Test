package com.clei.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * java8 日期时间格式化
 */
public class DateUtil {

    public final static String FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String FORMATTER_PATTERN_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 默认parse的日期类型
     */
    private final static TemporalQuery<LocalDateTime> QUERY = LocalDateTime::from;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMATTER_PATTERN);

    private final static DateTimeFormatter DATE_TIME_FORMATTER_MS = DateTimeFormatter.ofPattern(FORMATTER_PATTERN_MS);

    private final static ConcurrentHashMap<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap<>(4);

    private final static ZoneId ZONE_ID = ZoneId.of("GMT+8");

    private final static ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    /**
     * 一秒钟毫秒数
     */
    private final static long SECOND_MILLIS = 1000L;

    /**
     * 一分钟毫秒数
     */
    private final static long MINUTE_MILLIS = SECOND_MILLIS * 60;

    /**
     * 一小时毫秒数
     */
    private final static long HOUR_MILLIS = MINUTE_MILLIS * 60;

    /**
     * 一天毫秒数
     */
    private final static long DAY_MILLIS = HOUR_MILLIS * 24;

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
        DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(pattern);
        return format(temporal, dateTimeFormatter);
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
        DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(pattern);
        return parse(date, dateTimeFormatter, query);
    }

    public static Instant toInstant(LocalDateTime localDateTime) {
        validateDateTime(localDateTime);
        return localDateTime.toInstant(ZONE_OFFSET);
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return toInstant(localDateTime).toEpochMilli();
    }

    /**
     * String date -> epochMilli
     *
     * @param date
     * @return
     */
    public static long toEpochMilli(String date) {
        return toEpochMilli(parse(date));
    }

    /**
     * LocalDateTime -> epochSecond
     *
     * @param localDateTime
     * @return
     */
    public static long toEpochSecond(LocalDateTime localDateTime) {
        return toEpochSecond(localDateTime, false);
    }

    /**
     * String -> epochSecond
     *
     * @param date
     * @return
     */
    public static long toEpochSecond(String date) {
        return toEpochSecond(parse(date), true);
    }

    /**
     * LocalDateTime -> Date
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZONE_OFFSET));
    }

    /**
     * Date -> LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZONE_OFFSET);
    }

    /**
     * 两个日期的时间差 3天2小时1分这样子
     *
     * @param start
     * @param end
     * @return
     */
    public static String getDuration(LocalDateTime start, LocalDateTime end) {
        return getDuration(ChronoUnit.MILLIS.between(start, end));
    }

    /**
     * 两个日期的时间差 3天2小时1分这样子
     *
     * @param start
     * @param end
     * @return
     */
    public static String getDuration(Date start, Date end) {
        return getDuration(start.getTime(), end.getTime());
    }

    /**
     * 两个日期的时间差 3天2小时1分这样子
     *
     * @param start
     * @param end
     * @return
     */
    public static String getDuration(long start, long end) {
        return getDuration(end - start);
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param temporal1
     * @param temporal2
     * @return
     */
    public static boolean isSameDay(TemporalAccessor temporal1, TemporalAccessor temporal2) {
        return temporal1.get(ChronoField.YEAR) == temporal2.get(ChronoField.YEAR)
                && temporal1.get(ChronoField.DAY_OF_YEAR) == temporal2.get(ChronoField.DAY_OF_YEAR);
    }

    /**
     * 毫秒数转时间长度 3天2小时1分这样子
     *
     * @param millis 毫秒数
     * @return
     */
    private static String getDuration(long millis) {
        if (0 > millis) {
            throw new RuntimeException("时间错误！");
        }
        // 否则至少一分钟
        if (MINUTE_MILLIS > millis) {
            return "1分";
        }
        StringBuilder sb = new StringBuilder();
        long dayDiff = millis / DAY_MILLIS;
        if (0 < dayDiff) {
            sb.append(dayDiff);
            sb.append("天");
        }
        millis = millis % DAY_MILLIS;
        long hourDiff = millis / HOUR_MILLIS;
        if (0 < hourDiff) {
            sb.append(hourDiff);
            sb.append("小时");
        }
        millis = millis % HOUR_MILLIS;
        long minuteDiff = millis / MINUTE_MILLIS;
        if (0 < minuteDiff) {
            sb.append(minuteDiff);
            sb.append("分");
        }
        return sb.toString();
    }

    private static String format(TemporalAccessor temporal, DateTimeFormatter dateTimeFormatter) {
        validateDateTime(temporal);
        return dateTimeFormatter.format(temporal);
    }

    private static <T> T parse(String date, DateTimeFormatter dateTimeFormatter, TemporalQuery<T> query) {
        validateStr(date);
        return dateTimeFormatter.parse(date, query);
    }

    private static void validateMilliOrSecond(long l) {
        if (l < 0) {
            throw new IllegalArgumentException("传入参数小于0！");
        }
    }

    private static void validateDateTime(TemporalAccessor temporal) {
        if (null == temporal) {
            throw new IllegalArgumentException("传入参数为null！");
        }
    }

    private static void validateStr(String str) {
        if (null == str || "".equals(str)) {
            throw new IllegalArgumentException("传入字符串为" + (null == str ? "null！" : "空串！"));
        }
    }

    /**
     * LocalDateTime -> epochSecond
     *
     * @param localDateTime
     * @param valid         数据是否可信 true是 false否
     * @return
     */
    private static long toEpochSecond(LocalDateTime localDateTime, boolean valid) {
        if (!valid) {
            validateDateTime(localDateTime);
        }
        return localDateTime.toEpochSecond(ZONE_OFFSET);
        // return toInstant(localDateTime).getEpochSecond();
    }

    /**
     * 根据pattern获取DateTimeFormatter
     *
     * @param pattern
     * @return
     */
    private static DateTimeFormatter getDateTimeFormatter(String pattern) {
        validateStr(pattern);
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(pattern);
        if (null == dateTimeFormatter) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            //放入格式化器map里
            FORMATTER_MAP.put(pattern, dateTimeFormatter);
        }
        return dateTimeFormatter;
    }
}