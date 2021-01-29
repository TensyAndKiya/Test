package com.clei.Y2019.M12.D25;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clei.utils.Base64Util;
import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;
import com.clei.utils.RequestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 百旺开票测试2
 */
public class BaiwangInvoiceService2 {

    public static void main(String[] args) throws Exception {

        String aesKey = "5EE6C2C11DD421F2";

        Map<String,Object> cInfo = new HashMap<>();
        Map<String,Object> iInfo = new HashMap<>();

        cInfo.put("appId","6d29f136114544bcc73edcce960c430231183cc192c433e2b9ebcad56e8ceb08");
        cInfo.put("taxNo","110109500321655");
        cInfo.put("companyName","百旺电子测试2");
        cInfo.put("address","南山区蛇口、");
        cInfo.put("phone", "83484949");
        cInfo.put("bankName", "xx银行、");
        cInfo.put("bankAccount", "88888888888");
        cInfo.put("taxRate", "0.09");
        cInfo.put("drawer", "张三");
        cInfo.put("reviewer", "李四");
        cInfo.put("payee", "王五");

        iInfo.put("taxNo", "");
        iInfo.put("invoiceTitle", "陈某");
        iInfo.put("address", "中国四川");
        iInfo.put("phone", "184XXXXXXXX");
        iInfo.put("bankName", "中国人民银行");
        iInfo.put("bankAccount", "12345678");
        iInfo.put("amount", "1.00");
        iInfo.put("email", "yueyaye@163.com");


        JSONObject content = requestData(cInfo, iInfo);
        // content = content.replaceAll("\r","").replaceAll("\n","");

        String contentStr = content.toJSONString();

        PrintUtil.log("content : " + contentStr);

        contentStr = Base64Util.encode(contentStr.getBytes("UTF-8")).replaceAll("\r", "").replaceAll("\n", "");

        PrintUtil.log("contentStr : " + contentStr);

        String contentMD5 = MD5(contentStr.getBytes("UTF-8"));

        byte[] encryptBytes = encrypt(contentMD5.getBytes("UTF-8"),aesKey);

        String encryptStr = Base64Util.encode(encryptBytes).replaceAll("\r", "").replaceAll("\n", "");

        JSONObject outerInfo = outerInfo(cInfo,contentStr,encryptStr);

        // PrintUtil.log("contentKey : " + encryptStr);

        PrintUtil.log("final : " + outerInfo.toJSONString());

        String result = RequestUtils.getHttpConnectResult(outerInfo.toJSONString(),"https://dev.fapiao.com:18944/fpt-dsqz/invoice");

        PrintUtil.log("result : " + result);


    }

    // 外层信息
    private static JSONObject outerInfo(Map<String,Object> companyInfo,String content,String contentKey) throws UnsupportedEncodingException {

        String now = DateUtil.format(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss");
        String today = now.substring(0,10);

        String requestCode = "DZFPQZ";
        String interfaceCode = "DFXJ1001";

        // 最外层信息
        JSONObject obj = new JSONObject();
        JSONObject interfaceInfo = new JSONObject();
        // 全局信息
        JSONObject global = new JSONObject();
        global.put("appId",companyInfo.get("appId"));
        // 订单上传接口
        global.put("interfaceId","");
        global.put("interfaceCode",interfaceCode);
        global.put("requestCode",requestCode);
        // gouride 文档上是 "yyyy-MM-dd HH:mm:ss"
        global.put("requestTime", new Date().toString());
        global.put("responseCode","Ds");
        global.put("dataExchangeId",requestCode + interfaceCode + today
                 + UUID.randomUUID().toString().replaceAll("-","").substring(0,9));
                // + "1234567");
        // 返回信息
        JSONObject returnInfo = new JSONObject();
        returnInfo.put("returnCode","");
        returnInfo.put("returnMessage","");
        // 数据
        JSONObject data = new JSONObject();
        JSONObject dataDescription = new JSONObject();
        data.put("dataDescription",dataDescription);
        dataDescription.put("zipCode","0");

        data.put("content", Base64Util.encode(content.getBytes("UTF-8")).replaceAll("\r", "").replaceAll("\n", ""));
        data.put("contentKey", contentKey);

        interfaceInfo.put("globalInfo",global);
        interfaceInfo.put("returnStateInfo",returnInfo);
        interfaceInfo.put("Data",data);

        obj.put("interface",interfaceInfo);

        PrintUtil.log(obj.toJSONString());

        return obj;

    }

    // 请求数据
    private static JSONObject requestData(Map<String,Object> companyInfo,Map<String,Object> requestInfo){
        // 停哪儿成都信息科技有限公司
        String company = "TEST";
        String fpqqlsh = company + DateUtil.format(LocalDateTime.now(),"yyyyMMddHHmmssSSS").substring(2,15)
                + new Random().nextInt(10);

        JSONObject data = new JSONObject();
        // 发票请求流水号
        data.put("FPQQLSH",fpqqlsh);
        // 开票类型
        data.put("KPLX","0");
        // 征税方式
        data.put("ZSFS","0");
        // 销售方纳税人识别号
        data.put("XSF_NSRSBH",companyInfo.get("taxNo"));
        // 销售方名称
        data.put("XSF_MC",companyInfo.get("companyName"));
        // 销售方地址电话
        Object address = companyInfo.get("address");
        Object phone = companyInfo.get("phone");
        if(notEmpty(address) && notEmpty(phone)){
            data.put("XSF_DZDH",address.toString() + phone.toString());
        }else {
            data.put("XSF_DZDH","");
        }
        // 销售方银行账号
        Object bankName = companyInfo.get("bankName");
        Object bankAccount = companyInfo.get("bankAccount");
        if(notEmpty(bankName) && notEmpty(bankAccount)){
            data.put("XSF_YHZH",bankName.toString() + bankAccount.toString());
        }else {
            data.put("XSF_YHZH","");
        }
        // 购买方纳税人识别号
        Object taxNo = requestInfo.get("taxNo");
        if(notEmpty(taxNo)){
            data.put("GMF_NSRSBH",taxNo.toString());
        }else {
            data.put("GMF_NSRSBH","");
        }
        // 购买方名称
        data.put("GMF_MC",requestInfo.get("invoiceTitle"));
        // 购买方地址电话
        Object address2 = requestInfo.get("address");
        Object phone2 = requestInfo.get("phone");
        if(notEmpty(address2) && notEmpty(phone2)){
            data.put("GMF_DZDH",address2.toString() + phone2.toString());
        }else {
            data.put("GMF_DZDH","");
        }
        // 购买方银行账号
        Object bankName2 = requestInfo.get("bankName");
        Object bankAccount2 = requestInfo.get("bankAccount");
        if(notEmpty(bankName2) && notEmpty(bankAccount2)){
            data.put("GMF_YHZH",bankName2.toString() + bankAccount2.toString());
        }else {
            data.put("GMF_YHZH","");
        }
        // 购买方电子邮箱 目前这个必须得有
        data.put("GMF_SJH","");
        data.put("GMF_DZYX",requestInfo.get("email").toString());

        data.put("FPT_ZH","");
        data.put("WX_OPENID","");
        // 微信openId
        // data.put("WX_OPENID",openId);
        // 开票人 收款人 复核人
        data.put("KPR",companyInfo.get("drawer"));
        data.put("FHR",companyInfo.get("reviewer"));
        data.put("SKR",companyInfo.get("payee"));

        data.put("YFP_DM","");
        data.put("YFP_HM","");
        // 金额
        BigDecimal amount = new BigDecimal(requestInfo.get("amount").toString());
        BigDecimal taxRate = new BigDecimal(companyInfo.get("taxRate").toString());

        // 合计税额
        BigDecimal hjse = amount.multiply(taxRate).divide(BigDecimal.ONE.add(taxRate),4, RoundingMode.HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP);
        // 合计金额
        BigDecimal hjje = amount.subtract(hjse);
        data.put("JSHJ",amount.toString());
        data.put("HJJE",hjje.toString());
        data.put("HJSE",hjse.toString());

        data.put("KCE","");
        // 备注
        data.put("BZ","开票备注");
        data.put("HYLX","");
        data.put("BY1","");
        data.put("BY2","");
        data.put("BY3","");
        data.put("BY4","");
        data.put("BY5","");
        data.put("BY6","");
        data.put("BY7","");
        data.put("BY8","");
        data.put("BY9","");
        data.put("BY10","");
        data.put("WX_ORDER_ID","");
        data.put("WX_APP_ID","");
        data.put("ZFB_UID","");

        // 特殊票种标识
        // data.put("TSPZ","00");
        // 商品项目明细
        JSONObject detail = new JSONObject();
        // 发票行性质
        detail.put("FPHXZ","0");
        // 商品编码
        detail.put("SPBM","3040502020200000000");
        // 项目名称
        detail.put("XMMC","车辆停放服务");
        // 项目金额
        detail.put("XMJE",amount.toString());
        detail.put("XMDJ",amount.toString());
        detail.put("XMSL","1");
        // 税率
        detail.put("SL",taxRate.toString());
        // 税额
        detail.put("SE",hjse.toString());
        // 备注 1 2 3 4 5

        detail.put("ZXBM","");
        detail.put("YHZCBS","");
        detail.put("LSLBS","");
        detail.put("ZZSTSGL","");
        detail.put("GGXH","");
        detail.put("DW","");
        detail.put("BY1","");
        detail.put("BY2","");
        detail.put("BY3","");
        detail.put("BY4","");
        detail.put("BY5","");

        // 所有商品
        JSONObject allGoods = new JSONObject();
        JSONArray goodsArray = new JSONArray();
        goodsArray.add(detail);
        allGoods.put("COMMON_FPKJ_XMXX",goodsArray);

        // 请求数据体
        JSONObject requestData = new JSONObject();

        data.put("COMMON_FPKJ_XMXXS",allGoods);
        requestData.put("REQUEST_COMMON_FPKJ",data);

        return requestData;
    }

    private static boolean notEmpty(Object obj){
        return null != obj && 0 != obj.toString().length();
    }

    public static String MD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        byte[] b = md5.digest();
        StringBuffer buf = new StringBuffer("");

        for(int offset = 0; offset < b.length; ++offset) {
            int i = b[offset];
            if (i < 0) {
                i += 256;
            }

            if (i < 16) {
                buf.append("0");
            }

            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

    public static byte[] encrypt(byte[] content, String password) {
        try {
            byte[] enCodeFormat = password.getBytes();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

}
