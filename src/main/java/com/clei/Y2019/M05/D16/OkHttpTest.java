package com.clei.Y2019.M05.D16;

import com.clei.utils.DateUtil;
import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 疯狂的去访问计算器
 * 看看能不能重现错误
 *
 * @author KIyA
 */
public class OkHttpTest {

    private final static String START_DATE = "2019-05-01 00:00:00";
    private final static String END_DATE = "2019-05-16 23:23:23";

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8080/park/sentrybox/calculator/calculate?JSESSIONID=11299d11-4612-4676-adc0-b7a9bf67b995";
        LocalDateTime startDate = DateUtil.parse(START_DATE);
        LocalDateTime endDate = DateUtil.parse(END_DATE);
        while (startDate.isBefore(endDate)) {
            doPost(url, startDate, endDate);
            //加1秒
            startDate = startDate.plusSeconds(1);
            //休息一下，不然遭不住
            Thread.sleep(10);
        }
    }

    /**
     * 某url访问测试
     *
     * @param url
     * @param startDate
     * @param endDate
     * @throws IOException
     */
    private static void doPost(String url, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        Map<String, Object> param = new HashMap<>(4);
        param.put("beginTime", DateUtil.format(startDate));
        param.put("endTime", DateUtil.format(endDate));
        param.put("colorType", "0");
        param.put("sizeType", "10");
        String result = OkHttpUtil.doPostForm(url, param);
        PrintUtil.log("result: {}", result);
    }
}
