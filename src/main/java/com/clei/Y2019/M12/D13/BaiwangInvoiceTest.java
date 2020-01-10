package com.clei.Y2019.M12.D13;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clei.utils.BigDecimalUtil;
import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaiwangInvoiceTest {

    private final static String API_TOKEN_URL="http://open.hydzfp.com/open_access/access/token.open";
    private final static String API_BUSS_URL="http://open.hydzfp.com/open_access/buss/exec.open";

    private final static float SL = 0.09f;

    private static int i = 0;

    private static ConcurrentHashMap<String,Object> tokenRequestMap = new ConcurrentHashMap<>(2);

    public static void main(String[] args){

        // token参数
        tokenRequestMap.put("access_token","");
        tokenRequestMap.put("refresh_date",new Date());
        tokenRequestMap.put("expire_time",7200);
        tokenRequestMap.put("open_id","");
        tokenRequestMap.put("app_secret","");
        tokenRequestMap.put("drawer","何花");
        tokenRequestMap.put("tax_number","91510107MA6CED5530");
        tokenRequestMap.put("name","成都首中首泊停车场管理有限公司");
        tokenRequestMap.put("address","成都市武侯区火车南站西路15号7楼02室");
        tokenRequestMap.put("phone","028-85133533");
        tokenRequestMap.put("opening_bank","招商银行股份有限公司成都府城大道支行");
        tokenRequestMap.put("account","128909396510901");
        tokenRequestMap.put("fhr","黄莺");
        tokenRequestMap.put("skr","何花");
        // 开票业务参数
        Map<String,Object> param = new HashMap<>();
        param.put("invoice_price",0.01f);
        param.put("e_mail","yueyaye@163.com");
        param.put("tax_number","");
        param.put("invoice_title","陈某");
        param.put("address","");
        param.put("phone","");
        param.put("bank","");
        param.put("account","");

        newInvoice(param,null);
    }


    public static void getAccessToken() {

        Object accessToken = tokenRequestMap.get("access_token");
        //如果距离过期时间小于5分钟 则刷新ACCESSTOKEN
        if (null == accessToken || "".equals(accessToken)) {
            PrintUtil.println("初始化token");
            refreshAccessToken();
            return ;
        }
        Date refreshDate = (Date) tokenRequestMap.get("refresh_date");
        Integer expireTime = (Integer) tokenRequestMap.get("expire_time");
        long timeDiff = System.currentTimeMillis() - refreshDate.getTime();
        if(expireTime * 1000 - timeDiff < 300000){
            PrintUtil.println("快要过期 刷新token");
            refreshAccessToken();
            return ;
        }
    }

    public static boolean newInvoice(Map<String,Object> invoicingRecord,String userName){

        getAccessToken();

        PrintUtil.println("tokenMap : " + tokenRequestMap);

        //开票人传空则从token记录获取，且长度限制为8
        if(null == userName || "".equals(userName)){
            userName = tokenRequestMap.get("drawer").toString();
        }

        BigDecimal totalPrice = new BigDecimal(invoicingRecord.get("invoice_price").toString());
        //可能会有大于单张限额的，切割开多次
        BigDecimal singleQuota = new BigDecimal("0.01");
        // 西部智谷的要求将车场名设置到备注里
        invoicingRecord.put("other_message","备注");
        while(totalPrice.compareTo(singleQuota) >= 0){
            float jshj = BigDecimalUtil.getScaleFloat(singleQuota,2);
            totalPrice = totalPrice.subtract(singleQuota);
            newInvoice(invoicingRecord,tokenRequestMap,jshj,userName);

            try{
                Thread.sleep(876);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(totalPrice.compareTo(singleQuota) >= 0){
            return newInvoice(invoicingRecord,tokenRequestMap,BigDecimalUtil.getScaleFloat(totalPrice,2),userName);
        }
        return false;
    }

    private static boolean newInvoice(Map<String,Object> invoicingRecord,Map<String,Object> tokenMap,float jshj,String userName){

        System.out.println(i);
        i ++;

        JSONObject params = new JSONObject();
        JSONObject dataParams = new JSONObject();
        JSONObject goods = new JSONObject();
        JSONArray goodsDetail = new JSONArray();

        String orderNo = StringUtil.createOrderNo();
        String email = invoicingRecord.get("e_mail").toString();

        //调整长度。。float的长度+-*/后总是变化莫测
        float hjse = new BigDecimal((jshj * SL)/(1 + SL)).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
        Object taxNoObj = invoicingRecord.get("tax_number");
        String taxNo = null == taxNoObj ? "" : taxNoObj.toString();
        goods.put("fphxz",0);//发票行性质 0正常行 1折扣行 2被折扣行
        goods.put("spbm","3040502020200000000");//商品编码
        goods.put("yhzcbs",0);//优惠政策标识 0不使用 1使用
        goods.put("xmmc","车辆停放服务");//项目名称
        goods.put("xmje",jshj + "");//项目金额 两位小数
        goods.put("sl",SL + "");//税率 两位小数
        goods.put("se",hjse + "");//税额 两位小数

        goodsDetail.add(goods);//商品详情
        dataParams.put("data_resources","API");//固定参数API
        dataParams.put("nsrsbh",tokenMap.get("tax_number"));//销售方纳税人识别号
        dataParams.put("order_num", orderNo);//业务单据号，必须是唯一的
        dataParams.put("bmb_bbh","29.0");//税收编码版本号，参数"29.0",具体指请询问提供商
        dataParams.put("zsfs",0);//征税方式 0普通征税 1减按计增 2差额征税
        dataParams.put("xsf_nsrsbh",tokenMap.get("tax_number"));//销售方纳税人识别号
        dataParams.put("xsf_mc",tokenMap.get("name"));//销售方名称
        dataParams.put("xsf_dzdh",tokenMap.get("address").toString() + tokenMap.get("phone"));//销售方地址电话
        dataParams.put("xsf_yhzh",tokenMap.get("opening_bank").toString() + tokenMap.get("account"));//销售方开户行名称与银行账号
        dataParams.put("gmf_nsrsbh",taxNo);//购买方纳税人识别号
        dataParams.put("gmf_mc",invoicingRecord.get("invoice_title"));
        putIfNotEmpty(dataParams,"gmf_dzdh",invoicingRecord.get("address"),invoicingRecord.get("phone"));
        putIfNotEmpty(dataParams,"gmf_yhzh",invoicingRecord.get("bank"),invoicingRecord.get("account"));
        dataParams.put("kpr",userName);
        dataParams.put("fhr",tokenMap.get("fhr"));
        dataParams.put("skr",tokenMap.get("skr"));
        dataParams.put("jshj",jshj + "");//价税合计 合计金额+合计税额
        dataParams.put("hjje",new BigDecimal(jshj - hjse).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue() + "");//合计金额
        dataParams.put("hjse",hjse + "");//合计税额
        dataParams.put("jff_email",email);//电子邮件
        dataParams.put("common_fpkj_xmxx",goodsDetail);//商品明细
        dataParams.put("bz",invoicingRecord.get("other_message")); //备注
        params.put("access_token",tokenMap.get("access_token"));//accessToken
        params.put("serviceKey","ebi_InvoiceHandle_newBlueInvoice");//service method
        params.put("data",dataParams);//数据

        PrintUtil.println("开始开发票 params: " + params);
        String response = OkHttpUtil.doPost(API_BUSS_URL, params.toJSONString());
        PrintUtil.println("开具蓝字发票 response:" + response);

        if(StringUtil.isNotEmpty(response)){
            JSONObject map = JSONObject.parseObject(response);
            String result = map.get("result").toString();
            if(result.equals("SUCCESS")){
                PrintUtil.println("开具蓝字发票成功");

                onlineDeliver(tokenMap,orderNo,email);

                return true;
            }
        }
        PrintUtil.println("开具蓝字发票失败");
        return false;
    }

    public static boolean onlineDeliver(Map<String,Object> tokenMap,String orderNo,String email){
        JSONObject params = new JSONObject();
        JSONObject dataParams = new JSONObject();

        dataParams.put("nsrsbh",tokenMap.get("tax_number"));
        dataParams.put("order_num", orderNo);
        dataParams.put("jff_phone","");
        dataParams.put("jff_email",email);
        params.put("access_token",tokenMap.get("access_token"));
        params.put("serviceKey","ebi_InvoiceHandle_newInvoiceDelay");
        params.put("data",dataParams);

        String response = OkHttpUtil.doPost(API_BUSS_URL, params.toJSONString());

        PrintUtil.println("在线交付:" + response);

        if(StringUtil.isNotEmpty(response)){
            JSONObject map = JSONObject.parseObject(response);
            String result = map.getString("result");
            if(result.equals("SUCCESS")){
                PrintUtil.println("在线交付成功");
                return true;
            }
        }
        PrintUtil.println("在线交付失败");
        return false;
    }

    private static void refreshAccessToken() {
        // 避免短时间内重复刷新token
        String openId = tokenRequestMap.get("open_id").toString();
        String lock = ("refreshToken" + openId).intern();
        synchronized (lock){
            Long curTime = System.currentTimeMillis();

            JSONObject params = new JSONObject();
            params.put("openid", openId);
            params.put("app_secret", tokenRequestMap.get("app_secret").toString());

            String response = OkHttpUtil.doPost(API_TOKEN_URL, params.toString());
            PrintUtil.println("刷新AccessToken response:" + response);

            JSONObject map = JSONObject.parseObject(response);
            String result = map.get("result").toString();

            if(result.equals("SUCCESS")){
                PrintUtil.println("刷新发票AccessToken成功");
                JSONObject newTokenMap = JSONObject.parseObject(map.getString("rows"));
                PrintUtil.println("tokenMap : " + newTokenMap);
                //refresh
                String accessToken = newTokenMap.get("access_token").toString();

                //修改并返回
                tokenRequestMap.put("access_token",accessToken);
                tokenRequestMap.put("refresh_date",new Date());
                tokenRequestMap.put("expire_time",7200);
                // 成功刷新token才保存
                tokenRequestMap.put(openId,curTime);
                return ;
            }else {
                PrintUtil.println("刷新发票AccessToken失败: " + map);
            }
        }
    }

    private static void putIfNotEmpty(JSONObject map,String key,Object... args){
        String value = "";
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            if(null != obj && !"".equals(obj)){
                value += obj.toString() + ' ';
            }
        }
        map.put(key,value);
    }

}
