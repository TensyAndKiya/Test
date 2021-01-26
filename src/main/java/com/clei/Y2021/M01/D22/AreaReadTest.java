package com.clei.Y2021.M01.D22;

import com.clei.utils.MybatisUtil;
import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 从网站上读取中国省市区区划代码
 *
 * @author KIyA
 */
public class AreaReadTest {

    public static void main(String[] args) throws Exception {
        // 站点url
        String siteUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/";
        String provinceUrl = siteUrl + "index.html";
        String result = OkHttpUtil.doGet(provinceUrl, Charset.forName("GBK"));
        String[] lines = result.split("\n");
        String provinceMark = "<tr class='provincetr'>";
        int provinceMarkLength = provinceMark.length();
        String tdMark = "<td>";
        String brMark = "<br/>";
        String aMark = "</a>";
        int tdMarkLength = tdMark.length();
        for (String line : lines) {
            // 找到省那一栏的html
            if (line.startsWith(provinceMark)) {
                PrintUtil.log(line);
                String[] trLines = line.split("</tr>");
                // 返回结果
                List<Area> list = new ArrayList<>();
                for (String trLine : trLines) {
                    // 根据tr拆分
                    if (trLine.length() > provinceMarkLength) {
                        trLine = trLine.substring(provinceMark.length());
                        PrintUtil.log(trLine);
                        String[] tdLines = trLine.split("</td>");
                        // 找到每个省
                        for (String tdLine : tdLines) {
                            if (tdLine.endsWith(aMark)) {
                                tdLine = tdLine.substring(tdMarkLength);
                                PrintUtil.log(tdLine);
                                // 省名称及对应html地址
                                int nameBegin = tdLine.indexOf(">") + 1;
                                int nameEnd = tdLine.indexOf(brMark);
                                String provinceName = tdLine.substring(nameBegin, nameEnd);
                                int hrefBegin = tdLine.indexOf("'") + 1;
                                int hrefEnd = tdLine.indexOf("'>");
                                String href = tdLine.substring(hrefBegin, hrefEnd);
                                PrintUtil.log("province : {}, href : {}", provinceName, href);
                                String provinceCode = href.substring(0, href.indexOf("."));
                                Area area = new Area(provinceName, provinceCode, 0, "");
                                list.add(area);
                                // 设置市级区域
                                setCityArea(list, provinceCode, siteUrl, href);
                            }
                        }
                    }
                }
                PrintUtil.log(list.size());
                // 插入到数据库
                MybatisUtil.insertArea(list);
            }
        }
    }

    /**
     * 设置市级区域编码
     *
     * @param list         区域list
     * @param provinceCode 省级区域编码
     * @param siteUrl      站点url
     * @param href         html链接
     */
    private static void setCityArea(List<Area> list, String provinceCode, String siteUrl, String href) throws InterruptedException {
        String url = siteUrl + href;
        String result = OkHttpUtil.doGet(url, Charset.forName("GBK"));
        PrintUtil.log(result);
        String[] lines = result.split("\n");
        String cityMark = "<tr class='citytr'>";
        int cityMarkLength = cityMark.length();
        String tdMark = "<td>";
        String aMark = "</a>";
        int tdMarkLength = tdMark.length();
        for (String line : lines) {
            // 找到市那一栏的html
            if (line.startsWith(cityMark)) {
                PrintUtil.log(line);
                String[] trLines = line.split("</tr>");
                for (String trLine : trLines) {
                    // 根据tr拆分
                    if (trLine.length() > cityMarkLength) {
                        trLine = trLine.substring(cityMarkLength);
                        PrintUtil.log(trLine);
                        String[] tdLines = trLine.split("</td>");
                        // 找到每个市 取第二个元素即可
                        String tdLine = tdLines[1];
                        PrintUtil.log(tdLine);
                        tdLine = tdLine.substring(tdMarkLength);
                        // 省名称及对应html地址
                        int nameBegin = tdLine.indexOf(">") + 1;
                        int nameEnd = tdLine.indexOf(aMark);
                        String cityName = tdLine.substring(nameBegin, nameEnd);
                        int hrefBegin = tdLine.indexOf("'") + 1;
                        int hrefEnd = tdLine.indexOf("'>");
                        String tempHref = tdLine.substring(hrefBegin, hrefEnd);
                        PrintUtil.log("city : {}, href : {}", cityName, tempHref);
                        String cityCode = tempHref.substring(tempHref.indexOf("/") + 1, tempHref.indexOf("."));
                        Area area = new Area(cityName, cityCode, 1, provinceCode);
                        list.add(area);
                        // 设置县级区域
                        setCountyArea(list, cityCode, siteUrl, tempHref);
                    }
                }
            }
        }
    }

    /**
     * 设置县级区域编码
     *
     * @param list     区域list
     * @param cityCode 市级区域编码
     * @param siteUrl  站点url
     * @param href     html链接
     */
    private static void setCountyArea(List<Area> list, String cityCode, String siteUrl, String href) throws InterruptedException {
        // 睡眠是因为多次访问后，网站出现不响应的情况
        Thread.sleep(1200L);
        String url = siteUrl + href;
        String result = OkHttpUtil.doGet(url, Charset.forName("GBK"));
        PrintUtil.log(result);
        String[] lines = result.split("\n");
        String countyMark = "<tr class='countytr'>";
        int countyMarkLength = countyMark.length();
        String tdMark = "<td>";
        String aMark = "</a>";
        int tdMarkLength = tdMark.length();
        for (String line : lines) {
            // 找到市那一栏的html
            if (line.startsWith(countyMark)) {
                PrintUtil.log(line);
                String[] trLines = line.split("</tr>");
                for (String trLine : trLines) {
                    // 根据tr拆分
                    if (trLine.length() > countyMarkLength) {
                        trLine = trLine.substring(countyMarkLength);
                        PrintUtil.log(trLine);
                        String[] tdLines = trLine.split("</td>");
                        String countyName, countyCode;
                        // 有超链接
                        if (trLine.contains(aMark)) {
                            // 取第二个元素即可
                            String tdLine = tdLines[1];
                            PrintUtil.log(tdLine);
                            tdLine = tdLine.substring(tdMarkLength);
                            // 省名称及对应html地址
                            int nameBegin = tdLine.indexOf(">") + 1;
                            int nameEnd = tdLine.indexOf(aMark);
                            countyName = tdLine.substring(nameBegin, nameEnd);
                            int hrefBegin = tdLine.indexOf("'") + 1;
                            int hrefEnd = tdLine.indexOf("'>");
                            String tempHref = tdLine.substring(hrefBegin, hrefEnd);
                            PrintUtil.log("county : {}, href : {}", countyName, tempHref);
                            countyCode = tempHref.substring(tempHref.indexOf("/") + 1, tempHref.indexOf("."));
                        } else {
                            // 无超链接
                            countyName = tdLines[1].replace(tdMark, "");
                            countyCode = tdLines[0].replace(tdMark, "").substring(0, 6);
                        }
                        Area area = new Area(countyName, countyCode, 2, cityCode);
                        list.add(area);
                    }
                }
            }
        }
    }
}
