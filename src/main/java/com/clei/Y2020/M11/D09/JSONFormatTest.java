package com.clei.Y2020.M11.D09;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.clei.utils.PrintUtil;

import java.util.List;
import java.util.Random;

/**
 * JSON格式化
 *
 * @author KIyA
 */
public class JSONFormatTest {

    public static void main(String[] args) {

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            PrintUtil.log(random.nextBoolean());
        }

        TTT t = new TTT();

        PrintUtil.log(JSONObject.toJSONString(t));
        PrintUtil.log(JSONObject.toJSONString(t, SerializerFeature.WriteMapNullValue));
        PrintUtil.log(JSONObject.toJSONString(t, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty));
        PrintUtil.log(JSONObject.toJSONString(t, SerializerFeature.WriteMapNullValue, SerializerFeature.QuoteFieldNames));
    }

    private static class TTT {

        private String aa;
        private String[] bb;
        private List<String> cc;
        private Boolean dd = Boolean.FALSE;
        private Boolean ee;

        public String getAa() {
            return aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }

        public String[] getBb() {
            return bb;
        }

        public void setBb(String[] bb) {
            this.bb = bb;
        }

        public List<String> getCc() {
            return cc;
        }

        public void setCc(List<String> cc) {
            this.cc = cc;
        }

        public Boolean getDd() {
            return dd;
        }

        public void setDd(Boolean dd) {
            this.dd = dd;
        }

        public Boolean getEe() {
            return ee;
        }

        public void setEe(Boolean ee) {
            this.ee = ee;
        }
    }

}
