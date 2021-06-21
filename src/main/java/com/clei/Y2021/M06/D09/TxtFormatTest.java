package com.clei.Y2021.M06.D09;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * txt展示列表效果优化
 *
 * @author KIyA
 * @date 2021-06-04
 */
public class TxtFormatTest {

    /**
     * 读取文件绝对路径
     */
    private final static String INPUT_DIRECTORY = "D:\\Download\\DingDing\\详细设计文档\\mysql";

    /**
     * 读取文件绝对路径
     */
    private final static String OUTPUT_DIRECTORY = INPUT_DIRECTORY + File.separator + "temp";

    /**
     * 表列数
     */
    private final static int COLUMNS = 4;

    /**
     * 将source样子的文本转为target样子的
     * 看起来清楚一点
     * <p>
     * 结果：
     * notepad++上看起来对齐了的
     * idea和notepad上就不是了
     * 字体的问题
     * <p>
     * source
     * 字段名	字段类型	注释	备注
     * id	keyword	主键
     * driverCode	keyword	驾驶人编号
     * enterpriseCode	keyword	企业编码
     * riskTypeCode	keyword	风险类别编码	如果没有风险则该字段误数据
     * riskDate	keyword	数据日期	yyyy-MM-dd
     * riskLevel	keyword	风险等级	如果没有风险则该字段误数据
     * isOnline	keyword	是否在线	1:在线，0:不在线
     * riskCount	long	风险数量	为0时说明该驾驶人是离线状态或者在线但是没有触发风险
     * updateTime	date	数据更新时间	yyyy-MM-dd HH:mm:ss
     * useNature	keyword	车辆使用性质	v1.9.2
     * <p>
     * target
     * 字段名            字段类型    注释            备注
     * id                keyword     主键
     * driverCode        keyword     驾驶人编号
     * enterpriseCode    keyword     企业编码
     * riskTypeCode      keyword     风险类别编码    如果没有风险则该字段误数据
     * riskDate          keyword     数据日期        yyyy-MM-dd
     * riskLevel         keyword     风险等级        如果没有风险则该字段误数据
     * isOnline          keyword     是否在线        1:在线，0:不在线
     * riskCount         long        风险数量        为0时说明该驾驶人是离线状态或者在线但是没有触发风险
     * updateTime        date        数据更新时间    yyyy-MM-ddHH:mm:ss
     * useNature         keyword     车辆使用性质    v1.9.2
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        File inputDirectory = new File(INPUT_DIRECTORY);
        File[] files = inputDirectory.listFiles();

        for (File f : files) {
            if (f.isDirectory()) {
                continue;
            }

            File outputFile = new File(OUTPUT_DIRECTORY + File.separator + f.getName());
            int[] columnMaxLengthArr = new int[COLUMNS];
            List<String[]> lineList = new ArrayList<>();

            // 切割
            try (FileInputStream fis = new FileInputStream(f);
                 InputStreamReader isr = new InputStreamReader(fis, "GBK");
                 BufferedReader br = new BufferedReader(isr);
                 FileOutputStream fos = new FileOutputStream(outputFile);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
                 BufferedWriter bw = new BufferedWriter(osw)) {

                String str;
                while (null != (str = br.readLine())) {
                    String[] arr = str.split("\\s+");
                    lineList.add(arr);

                    for (int i = 0; i < arr.length && i < COLUMNS - 1; i++) {
                        columnMaxLengthArr[i] = Math.max(columnMaxLengthArr[i], strLength(arr[i]));
                    }
                }

                for (String[] strArr : lineList) {
                    for (int i = 0; i < strArr.length; i++) {
                        String s = strArr[i];
                        // 最后一行没必要做补齐
                        if (i < COLUMNS - 1) {
                            // 默认补齐4个空格的长度
                            int lengthDiff = columnMaxLengthArr[i] - strLength(s) + 4;
                            s = StringUtil.complete(s, ' ', s.length() + lengthDiff, false);
                        }
                        bw.write(s);
                    }
                    bw.write("\n");
                }

                bw.flush();
            } catch (Exception e) {
                PrintUtil.log("文件操作出错", e);
            }
        }
    }


    /**
     * 获取字符串的占位长度，这里非ascii字符的都是中文字符，所以显示长度为2
     *
     * @param str
     * @return
     */
    private static int strLength(String str) {
        char[] arr = str.toCharArray();
        int length = 0;
        for (char c : arr) {
            length += (c < 128 ? 1 : 2);
        }
        return length;
    }
}
