package com.clei.Y2020.M10.D16;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.PrintUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 测试下转换过程中对isDeleted这种字段的影响
 *
 * @author KIyA
 */
public class SerializeObjTest {

    public static void main(String[] args) {

        String[] arr = {"CC,BB", "DD,EE"};
        PrintUtil.log(StringUtils.join(arr, ";"));

        Vehicle vv = new Vehicle("川A00001", true);

        String json = JSONObject.toJSONString(vv);

        PrintUtil.log(json);

        PrintUtil.log(JSONObject.parseObject(json, Vehicle.class));

        // 结论 通过fastJson来操作没啥影响
        // Boolean / boolean 都没事
    }

    static class Vehicle {

        private String carLicense;

        private boolean isDeleted;

        public Vehicle(String carLicense, boolean isDeleted) {
            this.carLicense = carLicense;
            this.isDeleted = isDeleted;
        }

        public Vehicle() {
        }

        @Override
        public String toString() {
            return "Vehicle{" +
                    "carLicense='" + carLicense + '\'' +
                    ", isDeleted=" + isDeleted +
                    '}';
        }

        public String getCarLicense() {
            return carLicense;
        }

        public void setCarLicense(String carLicense) {
            this.carLicense = carLicense;
        }

        public boolean getDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }
    }

}
