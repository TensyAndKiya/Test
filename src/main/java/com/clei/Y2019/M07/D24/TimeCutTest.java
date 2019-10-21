package com.clei.Y2019.M07.D24;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 测试下这个函数是不是有问题
 * 结果证明 是 业务逻辑有漏洞。。
 */
public class TimeCutTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse("2019-07-24 09:46:37");
        Date endDate = sdf.parse("2019-07-24 10:53:33");
        endDate = updateEndTime(Calendar.MINUTE,true,10,10,startDate,endDate);
        System.out.println(sdf.format(endDate));
    }

    private static Date updateEndTime(int chargeInterval, boolean countStart, int firstSection, int section, Date orderStartTime, Date orderEndTime){
        if(section > 0){
            final int MINUTE_MILLS = 60 * 1000;
            long diffMills = orderEndTime.getTime() - orderStartTime.getTime();
            int sectionMills;
            int firstSectionMills;
            if(chargeInterval == Calendar.HOUR){
                sectionMills = 60 * MINUTE_MILLS * section;
                firstSectionMills = 60 * MINUTE_MILLS * firstSection;
            }else{
                sectionMills = MINUTE_MILLS * section;
                firstSectionMills = MINUTE_MILLS * firstSection;
            }
            //看看是不是计算起步价的
            if(countStart){
                if(diffMills > firstSectionMills - 1){
                    //先减去起步时长 再根据计费单位来算
                    diffMills = diffMills - firstSectionMills;
                    long lastMills = diffMills%sectionMills;
                    if(lastMills > 0){
                        orderEndTime = new Date(orderEndTime.getTime() - lastMills);
                    }
                }
                //如果连起步时间都达不到的话，还是要计起步费用，直接返回orderEndTime
            }else{
                long lastMills = diffMills%sectionMills;
                if(lastMills > 0){
                    orderEndTime = new Date(orderEndTime.getTime() - lastMills);
                }
            }
        }else{
            System.out.println("分段时长单位必须大于0！");
        }
        return orderEndTime;
    }
}
