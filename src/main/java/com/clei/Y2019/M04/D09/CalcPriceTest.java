package com.clei.Y2019.M04.D09;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalcPriceTest {
    private final static int MINUTE_MILLS = 60000;
    private final static int HOUR_MILLS = 3600000;
    private final static int DAY_MILLS = 86400000;

    private final static int FREE_MINUTES = 15;
    //人民币
    private final static float PRICE_PER_DAY = 31f;

    public static void main(String[] args) throws Exception{

        /*Scanner input = new Scanner(System.in,"UTF-8");
        System.out.println("时间格式 yyyy MM dd HH mm ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH mm ss");
        while(true){
            System.out.println("请输入开始时间： xxxx xx xx xx xx xx(输入xx结束程序)");
            String str = input.nextLine();
            if(str.equals("xx")){
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
        }*/


        Date sDate = new Date();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(sDate.getTime()));
        for (int i = 0; i < 24; i++) {
            cal2.add(Calendar.HOUR_OF_DAY,2);
            System.out.println(myCalc(sDate,cal2.getTime()));
        }
    }

    //详细计费规则请看 resources/images/ChargingRule图。。
    //目前只实现了 15分钟内免费，和黑白两阶段的计费
    private static Result myCalc(Date startDate, Date endDate){
        float totalPrice = 0f;
        if( null == startDate || null == endDate ){
            return new Result(startDate,endDate,0,0,0,0,0);
        }
        if( (endDate.getTime() - startDate.getTime()) <= FREE_MINUTES * MINUTE_MILLS ){
            return new Result(startDate,endDate,0,0,0,0,0);
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
        //计算24小时内的 白日与黑夜停车费
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(tempStartDate);
        cal2.setTime(endDate);

        int startHour = cal1.get(Calendar.HOUR_OF_DAY);
        int endHour = cal2.get(Calendar.HOUR_OF_DAY);

        int dayHours = 0;
        int nightHours = 0;
        //开始时间在白日阶段
        if( startHour >= 8 && startHour < 20 ){
            //if从白到白 else从白到黑
            if( endHour >= 8 && endHour < 20 ){
                if( (endDate.getTime() - tempStartDate.getTime()) < 12 * HOUR_MILLS ){
                    //同一天的白
                    dayHours = getDiffHours(tempStartDate,endDate);
                }else{
                    //中间过了一个完整夜阶段
                    dayHours = getDiffHours(tempStartDate,endDate) - 12;
                    nightHours = 12;
                }
            }else{
                //开始时间那一天的20点分割时刻
                Calendar tempCal = getCalendarWith(tempStartDate,20);
                dayHours = getDiffHours(tempStartDate,tempCal.getTime());
                nightHours = getDiffHours(tempCal.getTime(),endDate);
            }
        }else {
            //if从黑到黑，else从黑到白
            if( endHour < 8 || endHour >= 20){
                if( (endDate.getTime() - tempStartDate.getTime()) < 12 * HOUR_MILLS ){
                    //同一天的黑
                    nightHours = getDiffHours(tempStartDate,endDate);
                }else{
                    nightHours = getDiffHours(tempStartDate,endDate) - 12;
                    dayHours = 12;
                }
            }else{
                //结束时间那一天的8点分割时刻
                Calendar tempCal = getCalendarWith(endDate,8);
                nightHours = getDiffHours(tempStartDate,tempCal.getTime());
                dayHours = getDiffHours(tempCal.getTime(),endDate);
            }
        }

        float nightPrice = (float) ( Math.ceil( nightHours / 4.0 ) * 2 );

        float dayPrice;
        if( dayHours < 2 ){
            dayPrice = 2 * dayHours;
        }else if( dayHours < 5 ){
            dayPrice = 2 + 1 * ( dayHours - 1 );
        }else if( dayHours < 9 ){
            dayPrice = 2 + 3 + 2 * ( dayHours - 4 );
        }else {
            dayPrice = 2 + 3 + 8 + 3 * ( dayHours - 8 );
        }
        totalPrice = totalPrice + dayPrice + nightPrice;

        Result result = new Result(startDate,endDate,dayHours,nightHours,dayPrice,nightPrice,totalPrice);

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
    private int dayHours;
    private int nightHours;
    private float dayPrice;
    private float nightPrice;
    private float totalPrice;

    public Result(Date startDate, Date endDate, int dayHours, int nightHours, float dayPrice, float nightPrice, float totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayHours = dayHours;
        this.nightHours = nightHours;
        this.dayPrice = dayPrice;
        this.nightPrice = nightPrice;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "Result{" +
                "startDate=" + sdf.format(startDate) +
                ", endDate=" + sdf.format(endDate) +
                ", dayHours=" + dayHours +
                ", nightHours=" + nightHours +
                ", dayPrice=" + dayPrice +
                ", nightPrice=" + nightPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
