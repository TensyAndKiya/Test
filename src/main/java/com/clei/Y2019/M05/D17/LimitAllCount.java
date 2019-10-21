package com.clei.Y2019.M05.D17;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 有12小时或24小时限价 或 都有
 * 有白日黑夜限价
 *
 */
public class LimitAllCount {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int ONE_HOUR_MILLS = 60 * 60 * 1000;
    private static final int ONE_DAY_MILLS = 24 * ONE_HOUR_MILLS;
    public static void main(String[] args) throws Exception{

        String date1 = "2019-05-01 23:23:23";
        String date2 = "2019-05-15 21:21:21";
        Calendar orderStart = Calendar.getInstance();
        Calendar orderEnd = Calendar.getInstance();
        orderStart.setTime(SDF.parse(date1));
        orderEnd.setTime(SDF.parse(date2));
        boolean countStart = true;
        ChargeParams chargeParams = new ChargeParams();
        chargeParams.setChargeTimeInterval(0);
        chargeParams.setMaxAmountOf12Hour(12F);
        chargeParams.setMaxAmountOf24Hour(24F);
        chargeParams.setMaxAmountOfDay(16F);
        chargeParams.setMaxAmountOfNight(12F);
        chargeParams.setDay_flagFallPrice(3F);
        chargeParams.setDay_flagFallTime(1);
        chargeParams.setDay_unitPrice(2F);
        chargeParams.setDay_unitTime(1);
        chargeParams.setNight_flagfallPrice(5F);
        chargeParams.setNight_flagfallTime(2);
        chargeParams.setNight_unitPrice(3F);
        chargeParams.setNight_unitTime(2);
        //这两暂时不知何意 暂时不用
        chargeParams.setMaxDayStopTime(0);
        chargeParams.setMaxNightStopTime(0);
        //计费
        System.out.println(countFee(orderStart,orderEnd,chargeParams,countStart));
    }

    private static float countFee(Calendar orderStart, Calendar orderEnd, ChargeParams params, boolean countStart){
        Date startDate = orderStart.getTime();
        Date endDate = orderEnd.getTime();
        long diffMills = orderStart.getTimeInMillis() - orderEnd.getTimeInMillis();
        //TODO
        if(diffMills > ONE_DAY_MILLS - 1){
            long days = diffMills / ONE_DAY_MILLS;
        }
        return 0.0f;
    }

    private static class ChargeParams {

        private String sub_type;				//分段计费类型 0统一设置 1分段计费
        private String freetime;				//开始免费时间 如：15分钟以前免费
        private Integer chargeTimeInterval;		//0按小时计费，1按分钟计费
        private Float defaultChargeAmount;		//无入场默认金额
        private String parkingLotId;


        private Integer maxStopTime = 0;
        private Float flagfallPrice;
        private Integer flagfallTime;
        private Float unitPrice;
        private Integer unitTime;

        //分段计费
        private String sub_dayStartTime;		//分段计费白天开始时间
        private Integer maxDayStopTime = 0;
        private Float day_flagFallPrice;
        private Integer day_flagFallTime;
        private Float day_unitPrice;
        private Integer day_unitTime;

        private String sub_nightStartTime;		//分段计费晚上开始时间
        private Integer maxNightStopTime = 0;
        private Float night_flagfallPrice;
        private Integer night_flagfallTime;
        private Float night_unitPrice;
        private Integer night_unitTime;


        private Float maxAmountOf12Hour;
        private Float maxAmountOf24Hour;
        private Float maxAmountOfDay;
        private Float maxAmountOfNight;

        public String getParkingLotId() {
            return parkingLotId;
        }

        public void setParkingLotId(String parkingLotId) {
            this.parkingLotId = parkingLotId;
        }

        public String getSub_type() {
            return sub_type;
        }

        public void setSub_type(String sub_type) {
            this.sub_type = sub_type;
        }

        public String getFreetime() {
            return freetime;
        }

        public void setFreetime(String freetime) {
            this.freetime = freetime;
        }

        public Integer getChargeTimeInterval() {
            return chargeTimeInterval;
        }

        public void setChargeTimeInterval(Integer chargeTimeInterval) {
            this.chargeTimeInterval = chargeTimeInterval;
        }

        public Float getDefaultChargeAmount() {
            return defaultChargeAmount;
        }

        public void setDefaultChargeAmount(Float defaultChargeAmount) {
            this.defaultChargeAmount = defaultChargeAmount;
        }

        public Float getFlagfallPrice() {
            return flagfallPrice;
        }

        public void setFlagfallPrice(Float flagfallPrice) {
            this.flagfallPrice = flagfallPrice;
        }

        public Integer getFlagfallTime() {
            return flagfallTime;
        }

        public void setFlagfallTime(Integer flagfallTime) {
            this.flagfallTime = flagfallTime;
        }

        public Float getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Float unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Integer getUnitTime() {
            return unitTime;
        }

        public void setUnitTime(Integer unitTime) {
            this.unitTime = unitTime;
        }

        public String getSub_dayStartTime() {
            return sub_dayStartTime;
        }

        public void setSub_dayStartTime(String sub_dayStartTime) {
            this.sub_dayStartTime = sub_dayStartTime;
        }

        public Float getDay_flagFallPrice() {
            return day_flagFallPrice;
        }

        public void setDay_flagFallPrice(Float day_flagFallPrice) {
            this.day_flagFallPrice = day_flagFallPrice;
        }

        public Integer getDay_flagFallTime() {
            return day_flagFallTime;
        }

        public void setDay_flagFallTime(Integer day_flagFallTime) {
            this.day_flagFallTime = day_flagFallTime;
        }

        public Float getDay_unitPrice() {
            return day_unitPrice;
        }

        public void setDay_unitPrice(Float day_unitPrice) {
            this.day_unitPrice = day_unitPrice;
        }

        public Integer getDay_unitTime() {
            return day_unitTime;
        }

        public void setDay_unitTime(Integer day_unitTime) {
            this.day_unitTime = day_unitTime;
        }

        public String getSub_nightStartTime() {
            return sub_nightStartTime;
        }

        public void setSub_nightStartTime(String sub_nightStartTime) {
            this.sub_nightStartTime = sub_nightStartTime;
        }

        public Float getNight_flagfallPrice() {
            return night_flagfallPrice;
        }

        public void setNight_flagfallPrice(Float night_flagfallPrice) {
            this.night_flagfallPrice = night_flagfallPrice;
        }

        public Integer getNight_flagfallTime() {
            return night_flagfallTime;
        }

        public void setNight_flagfallTime(Integer night_flagfallTime) {
            this.night_flagfallTime = night_flagfallTime;
        }

        public Float getNight_unitPrice() {
            return night_unitPrice;
        }

        public void setNight_unitPrice(Float night_unitPrice) {
            this.night_unitPrice = night_unitPrice;
        }

        public Integer getNight_unitTime() {
            return night_unitTime;
        }

        public void setNight_unitTime(Integer night_unitTime) {
            this.night_unitTime = night_unitTime;
        }

        public Integer getMaxStopTime() {
            return maxStopTime;
        }

        public void setMaxStopTime(Integer maxStopTime) {
            this.maxStopTime = maxStopTime;
        }

        public Integer getMaxDayStopTime() {
            return maxDayStopTime;
        }

        public void setMaxDayStopTime(Integer maxDayStopTime) {
            this.maxDayStopTime = maxDayStopTime;
        }

        public Integer getMaxNightStopTime() {
            return maxNightStopTime;
        }

        public void setMaxNightStopTime(Integer maxNightStopTime) {
            this.maxNightStopTime = maxNightStopTime;
        }

        public Float getMaxAmountOf12Hour() {
            return maxAmountOf12Hour;
        }

        public void setMaxAmountOf12Hour(Float maxAmountOf12Hour) {
            this.maxAmountOf12Hour = maxAmountOf12Hour;
        }

        public Float getMaxAmountOf24Hour() {
            return maxAmountOf24Hour;
        }

        public void setMaxAmountOf24Hour(Float maxAmountOf24Hour) {
            this.maxAmountOf24Hour = maxAmountOf24Hour;
        }

        public Float getMaxAmountOfDay() {
            return maxAmountOfDay;
        }

        public void setMaxAmountOfDay(Float maxAmountOfDay) {
            this.maxAmountOfDay = maxAmountOfDay;
        }

        public Float getMaxAmountOfNight() {
            return maxAmountOfNight;
        }

        public void setMaxAmountOfNight(Float maxAmountOfNight) {
            this.maxAmountOfNight = maxAmountOfNight;
        }
    }
}
