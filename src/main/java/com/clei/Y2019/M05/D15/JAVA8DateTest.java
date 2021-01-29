package com.clei.Y2019.M05.D15;

import com.clei.utils.PrintUtil;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 为啥要有这个东西呢。。因为SimpleDateFormat和Calendar有点问题。。所以学习一下。。
 *
 * @author KIyA
 * @since 2019-05-15
 */
public class JAVA8DateTest {

    private final static int ONE_HOUR_MILLS = 60 * 60 * 1000;
    private final static int ONE_DAY_MILLS = 24 * 60 * 60 * 1000;

    public static void main(String[] args) {

        Instant now = Instant.now();
        PrintUtil.log(now.toEpochMilli());

        LocalDateTime localDateTime1 = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(now, ZoneId.of("GMT+8"));

        long diff_mills = ChronoUnit.MILLIS.between(localDateTime1, localDateTime2);
        PrintUtil.log(diff_mills);

        PrintUtil.log((System.currentTimeMillis() + 8 * ONE_HOUR_MILLS) % ONE_DAY_MILLS / ONE_HOUR_MILLS);
        //介绍
        //以下都是不可变类型且线程安全的哦
        //Instant 代表时间戳
        //LocalDate 不包含具体时间的日期 如 2019-05-15
        //LocalTime 不包含日期的时间 如19:42:42.333  竟然是 HH:mm:ss:SSS
        //LocalDateTime 这个虽然根据上面的能猜出来了。。但是。。没错。。它有日期和时间。。却没有时区信息
        //ZonedDateTime 包含时区的日期时间 偏移量是以UTC/格林威治时间为基准的
        //MonthDay 这个就是阉割版的LocalDate 不含年，只有月日，常用于判断是否某个节日或重复事件
        //YearMonth 类上 可以得到这个月有多少天。。
        //Clock 用来获取某个时区下当前的瞬时日期时间 用来代替 System.currentTimeMillis()和TimeZone.getDefault()
        //show time
        show(LocalDate.now());
        show(LocalTime.now());
        show(LocalDateTime.now());
        showClock();
        dateTimeFormat();
    }

    private static void show(LocalDate localDate) {
        PrintUtil.log("今天的日期是：" + localDate);
        PrintUtil.log("是不是闰年：" + localDate.isLeapYear());
        //日期的加减 ChronoUnit单位  那个参数可以用负数哦
        PrintUtil.log("一周后的日期是：" + localDate.plus(1, ChronoUnit.WEEKS));
        PrintUtil.log("一周后的日期是：" + localDate.plusWeeks(1));
        PrintUtil.log("一年前的日期是：" + localDate.minus(1, ChronoUnit.YEARS));
        PrintUtil.log("一年前的日期是：" + localDate.minusYears(1));

        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        PrintUtil.log("今天是" + year + '年' + month + '月' + day + '日');
        //获取特定日期类型的LocalDate
        //year 是 0到9999 10000或以上会多个+号，month是1到12，day是1到31(但会根据前面的month判定是否合法) 输入错误数字会报错
        LocalDate localDate2 = LocalDate.of(1995, 10, 3);
        PrintUtil.log("你输入的日期是：" + localDate2);
        //判断日期是否相等 或者用isEqual方法
        PrintUtil.log(localDate.equals(localDate2));
        PrintUtil.log(LocalDate.of(1995, 10, 03).equals(localDate2));
        //用isBefore和isAfter判断前后关系

        //两个日期相差时间  这个感觉有点问题。。它单纯只计算同类之差
        //Period period = Period.between(localDate2,localDate);
        PrintUtil.log("日期{}和日期{}相差{}天", localDate2, localDate, ChronoUnit.DAYS.between(localDate2, localDate));

        //用MonthDay判断生日
        MonthDay birthDay = MonthDay.of(10, 03);
        MonthDay theDay = MonthDay.from(localDate2);
        if (theDay.equals(birthDay)) {
            PrintUtil.log("是你的生日哦！");
        } else {
            PrintUtil.log("不是你的哦！");
        }
    }

    private static void show(LocalTime localTime) {
        PrintUtil.log("当前的时间是：" + localTime);
        PrintUtil.log("两小时后的时间是：" + localTime.plusHours(2));
    }

    private static void show(LocalDateTime localDateTime) {
        PrintUtil.log("当前的日期时间是：" + localDateTime);
        //这个有点问题。。时钟不对。。忘了+8了吗。。。
        //好像又没问题了。。因为算的是从(计算机元年)1970 01 01 00 00 00 到现在的时间戳。。
        PrintUtil.log("当前的时间戳是：" + Instant.now());
        PrintUtil.log("当前的时间戳是：" + Instant.now(Clock.systemUTC()));
        PrintUtil.log("当前的时间戳是：" + Clock.systemDefaultZone().instant());
        PrintUtil.log("当前的时间戳是：" + Clock.system(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).instant());
        //将本地时间转成另一个时区的时间
        ZoneId zoneId = ZoneId.of(ZoneId.SHORT_IDS.get("CTT"));
        ZonedDateTime dateTimeInNewYork = ZonedDateTime.of(localDateTime, zoneId);
        PrintUtil.log("现在是上海时间：" + dateTimeInNewYork);
    }

    private static void showClock() {
        //根据系统时钟和UTC返回当前时间
        Clock clock = Clock.systemUTC();
        PrintUtil.log("clock: " + clock);
        clock = Clock.systemDefaultZone();
        PrintUtil.log("clock: " + clock);
    }

    private static void dateTimeFormat() {
        String dateStr = "19951003";
        //其它几个类也可parse LocalDateTime,ZonedDateTime等
        LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
        PrintUtil.log("parse后的时间为：" + localDate);
        //format
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        LocalDateTime localDateTime = LocalDateTime.now();
        PrintUtil.log("格式化后的日期时间为：" + dtf.format(localDateTime));
    }
}
