package com.clei.utils;

/**
 * 方便自己的输出打印
 *
 * @author KIyA
 */
public class PrintUtil {

    private final static String EMPTY = "";
    private final static char TAB = '\t';
    private final static char LINEFEED = '\n';
    private final static String PLACE_STR = "{}";
    private final static int PLACE_LEN = PLACE_STR.length();

    /**
     * 默认分隔符
     */
    private final static char SEPARATOR = '-';

    /**
     * 默认分隔线长度
     */
    private final static int SEPARATOR_LINE_LENGTH = 128;

    /**
     * 默认分割线
     */
    private final static String SEPARATOR_LINE;

    static {
        StringBuilder sb = new StringBuilder(SEPARATOR_LINE_LENGTH);
        for (int i = 0; i < SEPARATOR_LINE_LENGTH; i++) {
            sb.append(SEPARATOR);
        }
        SEPARATOR_LINE = sb.toString();
    }

    public static void println() {
        System.out.println();
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void println(final String str, Object... args) {
        System.out.println(formatStr(str, args));
    }

    public static void print(final String str, Object... args) {
        System.out.print(formatStr(str, args));
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void log() {
        log(EMPTY);
    }

    public static void log(final String str, Object... args) {
        dateLine(true, str, args);
    }

    public static void log(Object obj) {
        dateLine(true, String.valueOf(obj));
    }

    /**
     * 打印当前日期 + 换行
     */
    public static void dateLine() {
        log(DateUtil.currentDateTime());
    }

    /**
     * 打印当前日期 + 数据 + 换行
     *
     * @param obj
     */
    public static void dateLine(Object obj) {
        log(DateUtil.currentDateTime() + ' ' + obj);
    }

    /**
     * 打印当前日期 + 数据 + 换行
     *
     * @param str
     * @param args
     */
    public static void dateLine(final String str, Object... args) {
        String head = DateUtil.currentDateTime() + ' ' + str;
        println(head, args);
    }

    /**
     * 打印当前日期 + 数据 + 换行
     *
     * @param ms   是否打印毫秒数
     * @param str
     * @param args
     */
    public static void dateLine(boolean ms, final String str, Object... args) {
        String head = DateUtil.currentDateTime(ms) + " [" + Thread.currentThread().getName() + "] " + str;
        println(head, args);
    }

    /**
     * 打印当前日期
     */
    public static void date() {
        print(DateUtil.currentDateTime());
    }

    /**
     * 打印当前日期 + 数据
     *
     * @param obj
     */
    public static void date(Object obj) {
        print(DateUtil.currentDateTime() + " - " + obj);
    }

    /**
     * 打印当前日期 + 数据 + 换行
     *
     * @param str
     * @param args
     */
    public static void date(final String str, Object... args) {
        print(DateUtil.currentDateTime() + " - " + str, args);
    }

    /**
     * 打印当前虚拟机内存情况
     */
    public static void printMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        dateLine("maxMemory : {}M", runtime.maxMemory() / 1024 / 1024);
        dateLine("totalMemory : {}M", runtime.totalMemory() / 1024 / 1024);
        dateLine("freeMemory : {}M", runtime.freeMemory() / 1024 / 1024);
    }

    private static String formatStr(String str, Object... args) {
        if (null == args || args.length == 0) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        // 普通参数
        for (Object arg : args) {
            int position = sb.indexOf(PLACE_STR);
            if (position > -1) {
                sb.replace(position, position + PLACE_LEN, String.valueOf(arg));
            } else {
                break;
            }
        }
        // 异常
        Object lastArg = args[args.length - 1];
        if (lastArg instanceof Throwable) {
            sb.append(getStackTrace((Throwable) lastArg));
        }
        return sb.toString();
    }

    /**
     * 打印分割线
     */
    public static void separatorLine() {
        separatorLine(false);
    }

    /**
     * 打印分割线
     *
     * @param line 是否先换行
     */
    public static void separatorLine(boolean line) {
        if (line) {
            println();
        }
        log(SEPARATOR_LINE);
    }

    /**
     * 打印分割线 使用指定分隔符
     *
     * @param separator 分隔符
     * @param line      是否先换行
     */
    public static void separatorLine(char separator, boolean line) {
        StringBuilder sb = new StringBuilder(SEPARATOR_LINE_LENGTH);
        if (line) {
            sb.append('\n');
        }
        for (int i = 0; i < SEPARATOR_LINE_LENGTH; i++) {
            sb.append(separator);
        }
        log(sb.toString());
    }

    /**
     * Throwable stackTrace 字符串
     *
     * @param t
     * @return
     */
    private static String getStackTrace(Throwable t) {
        StringBuilder sb = new StringBuilder();

        sb.append(LINEFEED).append(t.toString()).append(LINEFEED);
        for (StackTraceElement s : t.getStackTrace()) {
            sb.append(TAB + "at ").append(s.toString()).append(LINEFEED);
        }
        for (Throwable se : t.getSuppressed()) {
            sb.append(se.toString()).append(LINEFEED);
        }
        Throwable cause = t.getCause();
        if (null != cause) {
            sb.append(cause.toString()).append(LINEFEED);
        }
        return sb.toString();
    }

    /**
     * 获得当前线程名
     *
     * @return
     */
    private static String getThreadName() {
        return Thread.currentThread().getName();
    }
}
