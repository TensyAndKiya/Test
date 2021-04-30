package com.clei.Y2020.M10.D27;

import com.clei.utils.PrintUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据给的数据生成一条批量插入的sql
 *
 * @author KIyA
 * @date 2020-10-28
 */
public class BatchInsertTest {

    public static void main(String[] args) {

        String dataStr = "1.图片及视频证据不足[无效]\n" +
                "2.摄像头安装问题[设备]\n" +
                "3.摄像头黑屏[设备]\n" +
                "4.摄像头被遮挡[驾驶员]\n" +
                "5.信号漂移导致超速[[无效]\n" +
                "6.连续超速3个点（及以上）[驾驶员]\n" +
                "7.超速110码以上[驾驶员]\n" +
                "8.超时4小时驾驶双班中途已换人[无效]\n" +
                "9.超时4小时驾驶用卡错误[无效]\n" +
                "10.非2-5点禁行涉及车辆[无效]\n" +
                "11.非手持手机[无效]\n" +
                "12.正常观察路况[无效]\n" +
                "13.疑似观看手机[驾驶员]\n" +
                "14.疑似疲劳驾驶[驾驶员]\n" +
                "15.已核实驾驶状态正常[无效]\n" +
                "16.非同一驾驶员行为[无效]\n" +
                "17.车辆停运[无效]";

        String[] arr = dataStr.split("\n");

        // 去掉序号.并转为List
        List<String> list = Arrays.stream(arr).map(s -> {
            int dotIndex = s.indexOf('.');
            if (-1 != dotIndex) {
                s = s.substring(dotIndex + 1);
            }
            return s;
        }).collect(Collectors.toList());

        String sqlStart = "INSERT INTO base_risk_process_remark(process_type,title,remark) VALUES";

        StringBuilder sb = new StringBuilder();

        sb.append(sqlStart);
        sb.append('\n');

        // 拼接
        for (int i = 1; i < 4; i++) {
            for (String s : list) {
                sb.append('(');
                sb.append(i);
                sb.append(',');
                sb.append('\'');
                sb.append(s);
                sb.append('\'');
                sb.append(',');
                sb.append('\'');
                sb.append(s);
                sb.append('\'');
                sb.append(')');
                sb.append(',');
                sb.append('\n');
            }
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append(';');

        PrintUtil.log(sb.toString());
    }
}
