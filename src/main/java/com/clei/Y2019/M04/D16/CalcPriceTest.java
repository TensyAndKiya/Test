package com.clei.Y2019.M04.D16;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class CalcPriceTest {
    private final static int MINUTE_MILLS = 60000;
    private final static int HOUR_MILLS = 3600000;
    private final static int DAY_MILLS = 86400000;

    private final static int FREE_MINUTES = 20;
    //人民币
    private final static float PRICE_PER_DAY = 18f;

    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in,"UTF-8");
        System.out.println("时间格式 yyyy MM dd HH mm ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH mm ss");
        while(true){
            System.out.println("请输入开始时间： xxxx xx xx xx xx xx(输入xx结束程序)");
            String str = input.nextLine();
            if(null != str && str.equals("xx")){
                break;
            }
            if(null == str || str.length() == 0){
                System.out.println("请输入正确内容：");
                continue;
            }
            Date startDate = sdf.parse(str);
            System.out.println("请输入结束时间： xxxx xx xx xx xx xx(输入xx结束程序)");
            str = input.nextLine();
            if(str.equals("xx")){
                break;
            }
            Date endDate = sdf.parse(str);
            //输出
            System.out.println(myCalc(startDate,endDate));
        }

        /*Date sDate = new Date();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(sDate.getTime()));
        for (int i = 0; i < 24; i++) {
            cal2.add(Calendar.HOUR_OF_DAY,2);
            System.out.println(myCalc(sDate,cal2.getTime()));
        }*/
    }

    //详细计费规则请看 resources/images/ChargingRule2图。。
    private static Result myCalc(Date startDate, Date endDate){
        float totalPrice = 0f;
        if( null == startDate || null == endDate ){
            return new Result(startDate,endDate,0,false,0);
        }
        if( (endDate.getTime() - startDate.getTime()) <= FREE_MINUTES * MINUTE_MILLS ){
            return new Result(startDate,endDate,0,false,0);
        }
        Date tempStartDate = new Date(startDate.getTime());
        //若不是同一天的话可能超过24小时，则计算整天数的钱，并给开始时间重新赋值
        if( !isSameDay(tempStartDate,endDate) ){
            long timeDiffInMills = endDate.getTime() - tempStartDate.getTime();
            if( timeDiffInMills > DAY_MILLS - 1 ){
                long days = timeDiffInMills / DAY_MILLS;
                totalPrice += days * PRICE_PER_DAY;
                tempStartDate.setTime( tempStartDate.getTime() + DAY_MILLS * days);
            }
        }
        //过夜时间0点到6点
        float price = 0f;
        boolean overNight = false;
        if( isSameDay(tempStartDate,endDate) ){
            //同一天的话开始时间小于6点。。且开始不等于结束的话。。跨夜
            long sixTimeInMills = getCalendarWith(tempStartDate,6).getTimeInMillis();
            if(tempStartDate.getTime() < sixTimeInMills && tempStartDate.getTime() < endDate.getTime()){
                overNight = true;
                price += 3;
            }
        }else{
            //非同一天的话结束时间大于0点。。且开始不等于结束的话。。跨夜
            long zeroTimeInMills = getCalendarWith(endDate,0).getTimeInMillis();
            if(endDate.getTime() > zeroTimeInMills && tempStartDate.getTime() < endDate.getTime()){
                overNight = true;
                price += 3;
            }
        }
        int hours = getDiffHours(tempStartDate,endDate);
        //价格计算
        if(hours > 0){
            if( hours < 2 ){
                price += 3;
            }else if( hours < 4 ){
                price += 5;
            }else {
                price += (5 + (hours - 3) );
            }
            //每日限制为18
            price = price > PRICE_PER_DAY ? PRICE_PER_DAY : price;
            totalPrice += price;
        }
        Result result = new Result(startDate,endDate,getDiffHours(startDate,endDate),overNight,totalPrice);
        return result;
    }

    private static boolean isSameDay(Date startDate, Date endDate){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(startDate);
        cal2.setTime(endDate);
        if( (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                && (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) ){
            return true;
        }
        return false;
    }

    private static Calendar getCalendarWith(Date date, int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar;
    }

    private static int getDiffHours(Date startDate, Date endDate){
        //不足1小时按照1小时算,用- 0.0变成double除以long不会自动取整
        return (int)(Math.ceil((endDate.getTime() - startDate.getTime() - 0.0)/HOUR_MILLS));
    }
}

class  Result{
    private Date startDate;
    private Date endDate;
    private int hours;
    private boolean overNight;
    private float totalPrice;

    public Result(Date startDate, Date endDate, int hours, boolean overNight, float totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = hours;
        this.overNight = overNight;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return "Result{" +
                "startDate=" + df.format(startDate) +
                ", endDate=" + df.format(endDate) +
                ", hours=" + hours +
                ", overNight=" + overNight +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
