package com.clei.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;

/**
 * java8 日期时间格式化
 */
public class DateUtil {

    private final static String FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATTER_PATTERN);

    private final static ConcurrentHashMap<String,DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap(4);

    private final static ZoneId ZONE_ID = ZoneId.of("GMT+8");

    private final static ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    static {
        FORMATTER_MAP.put(FORMATTER_PATTERN,FORMATTER);
    }

    public static String getDefaultPattern() {
        return FORMATTER_PATTERN;
    }

    /**
     * 返回默认格式的 当前日期时间字符串
     * @return
     */
    public static String currentDateTime(){
        return format(LocalDateTime.now());
    }

    public static String format(LocalDateTime localDateTime){
        return format(localDateTime,FORMATTER);
    }

    public static String format(LocalDateTime localDateTime,String pattern){
        validateStr(pattern);
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(pattern);
        if(null != dateTimeFormatter){
            return format(localDateTime,dateTimeFormatter);
        }else{
            try{
                dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                String result = format(localDateTime,dateTimeFormatter);
                //放入格式化器map里
                FORMATTER_MAP.put(pattern,dateTimeFormatter);
                return result;
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private static String format(LocalDateTime localDateTime,DateTimeFormatter formatter){
        validateLocalDateTime(localDateTime);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime parse(String date){
        return parse(date,FORMATTER);
    }

    public static LocalDateTime parse(String date,String pattern){
        validateStr(pattern);
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(pattern);
        if(null != dateTimeFormatter){
            return parse(date,dateTimeFormatter);
        }else{
            try{
                dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                LocalDateTime result = parse(date,dateTimeFormatter);
                //放入格式化器map里
                FORMATTER_MAP.put(pattern,dateTimeFormatter);
                return result;
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private static LocalDateTime parse(String date,DateTimeFormatter formatter){
        validateStr(date);
        return LocalDateTime.parse(date,formatter);
    }

    public static Instant toInstant(LocalDateTime localDateTime){
        validateLocalDateTime(localDateTime);
        return localDateTime.toInstant(ZONE_OFFSET);
    }

    public static long toEpochMilli(LocalDateTime localDateTime){
        return toInstant(localDateTime).toEpochMilli();
    }

    public static long toEpochSecond(LocalDateTime localDateTime){
        validateLocalDateTime(localDateTime);
        return localDateTime.toEpochSecond(ZONE_OFFSET);
        // return toInstant(localDateTime).getEpochSecond();
    }

    private static void validateLocalDateTime(LocalDateTime localDateTime){
        if(null == localDateTime){
            throw new RuntimeException("传入参数为null！");
        }
    }

    private static void validateStr(String str){
        if(null == str || "".equals(str)){
            throw new RuntimeException("传入字符串为" + null == str ? "null！" : "空串！");
        }
    }
}
