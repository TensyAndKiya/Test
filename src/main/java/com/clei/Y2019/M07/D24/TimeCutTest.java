package com.clei.Y2019.M07.D24;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 测试下这个函数是不是有问题
 * 结果证明 是 业务逻辑有漏洞。。
 *
 * @author KIyA
 */
public class TimeCutTest {

    public static void main(String[] args) throws ParseException {
        LocalDateTime startDate = DateUtil.parse("2019-07-24 09:46:37");
        LocalDateTime endDate = DateUtil.parse("2019-07-24 10:53:33");
        endDate = updateEndTime(true, 10, 10, startDate, endDate);
        PrintUtil.log(DateUtil.format(endDate));
    }

    private static LocalDateTime updateEndTime(boolean countStart, int firstSection, int section, LocalDateTime orderStartTime, LocalDateTime orderEndTime) {
        if (section > 0) {
            final int minuteMills = 60 * 1000;
            long diffMills = ChronoUnit.MILLIS.between(orderStartTime, orderEndTime);
            int sectionMills;
            int firstSectionMills;
            sectionMills = minuteMills * section;
            firstSectionMills = minuteMills * firstSection;
            //看看是不是计算起步价的
            if (countStart) {
                if (diffMills > firstSectionMills - 1) {
                    //先减去起步时长 再根据计费单位来算
                    diffMills = diffMills - firstSectionMills;
                    long lastMills = diffMills % sectionMills;
                    if (lastMills > 0) {
                        orderEndTime = DateUtil.fromMillis(DateUtil.toEpochMilli(orderEndTime) - lastMills);
                    }
                }
                //如果连起步时间都达不到的话，还是要计起步费用，直接返回orderEndTime
            } else {
                long lastMills = diffMills % sectionMills;
                if (lastMills > 0) {
                    orderEndTime = DateUtil.fromMillis(DateUtil.toEpochMilli(orderEndTime) - lastMills);
                }
            }
        } else {
            PrintUtil.log("分段时长单位必须大于0！");
        }
        return orderEndTime;
    }
}
