package com.clei.Y2019.M12.D25;

import com.alibaba.fastjson.JSONObject;
import com.clei.utils.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 百旺开票测试3
 *
 * @author KIyA
 */
public class BaiwangInvoiceService3 {

    public static void main(String[] args) throws Exception {
        String aesKey = "加密加密加密加密";

        Map<String, Object> cInfo = new HashMap<>();
        Map<String, Object> iInfo = new HashMap<>();

        cInfo.put("appId", "");
        cInfo.put("taxNo", "税号");
        cInfo.put("companyName", "百旺电子测试2");
        cInfo.put("address", "南山区蛇口、");
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

        String content = requestData(cInfo, iInfo);
        // content = content.replaceAll("\r","").replaceAll("\n","");

        PrintUtil.log("content " + content);

        String contentMD5 = MD5(content.getBytes("UTF-8"));

        byte[] encryptBytes = encrypt(contentMD5.getBytes("UTF-8"), aesKey);

        String encryptStr = Base64Util.encode(encryptBytes).replaceAll("\r", "").replaceAll("\n", "");

        String outerInfo = outerInfo(cInfo, content, encryptStr);

        // PrintUtil.log("contentKey : " + encryptStr);

        PrintUtil.log("final : " + outerInfo);

        String result = RequestUtils.getHttpConnectResult(outerInfo, "https://dev.fapiao.com:19444/fpt-dsqz/invoice");

        PrintUtil.log("result : " + result);

        JSONObject obj = JSONObject.parseObject(result);

        JSONObject interfaceInfo = JSONObject.parseObject(obj.getString("interface"));

        JSONObject returnStateInfo = JSONObject.parseObject(interfaceInfo.getString("returnStateInfo"));

        String returnCode = returnStateInfo.getString("returnCode");

        // 成功
        JSONObject data = JSONObject.parseObject(interfaceInfo.getString("Data"));
        // 业务数据内容
        String returnContent = data.getString("content");

        PrintUtil.log("content : " + returnContent);
        byte[] base64 = Base64Util.decode(returnContent);

        PrintUtil.log(new String(base64, "utf-8"));

        PrintUtil.log("length1 : " + base64.length);
        byte[] aesBytes = EncryptUtil.decryptAES(base64, aesKey);
        String finalResult = new String(EncryptUtil.decryptAES(aesBytes, aesKey));

        PrintUtil.log(finalResult);
    }

    // 外层信息
    private static String outerInfo(Map<String, Object> companyInfo, String content, String contentKey) throws UnsupportedEncodingException {

        String now = DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
        String today = now.substring(0, 10);

        String requestCode = "DZFPQZ";
        String interfaceCode = "DFXJ1001";

        String dataExchangeId = requestCode + interfaceCode + today
                + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9);

        StringBuffer sb = new StringBuffer("");

        sb.append("{");
        sb.append("\"interface\": {");
        sb.append("\"globalInfo\": {");
        sb.append("\"appId\": \"").append(companyInfo.get("appId")).append("\",");
        sb.append("\"interfaceId\": \"\",");
        sb.append("\"interfaceCode\": \"").append(interfaceCode).append("\",");
        sb.append("\"requestCode\": \"DZFPQZ\",");
        sb.append("\"requestTime\": \"").append(new Date()).append("\",");
        sb.append("\"responseCode\": \"Ds\",");
        sb.append("\"dataExchangeId\": \"").append(dataExchangeId).append("\"");
        sb.append("},");
        sb.append("\"returnStateInfo\": {");
        sb.append("\"returnCode\": \"\",");
        sb.append("\"returnMessage\": \"\"");
        sb.append("},");
        sb.append("\"Data\": {");
        sb.append("\"dataDescription\": {");
        sb.append("\"zipCode\": \"0\"");
        sb.append("},");
        sb.append("\"content\": \"");
        sb.append(content).append("\",");
        sb.append("\"contentKey\":\"");
        sb.append(contentKey).append("\"");
        sb.append("}");
        sb.append("}");
        sb.append("}");

        return sb.toString();

    }

    // 请求数据
    private static String requestData(Map<String, Object> companyInfo, Map<String, Object> requestInfo) throws UnsupportedEncodingException {

        // 停哪儿成都信息科技有限公司
        String company = "TEST";
        String fpqqlsh = company + DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmssSSS").substring(2, 15)
                + new Random().nextInt(10);

        StringBuffer content = new StringBuffer("{");
        content.append("\"REQUEST_COMMON_FPKJ\": {");
        content.append("\"FPQQLSH\": \"" + fpqqlsh + "\",");
        content.append("\"ZSFS\": \"0\",");
        content.append("\"KPLX\": \"0\",");
        content.append("\"XSF_NSRSBH\": \"" + companyInfo.get("taxNo") + "\",");
        content.append("\"XSF_MC\": \"" + companyInfo.get("companyName") + "\",");
        content.append("\"XSF_DZDH\": \"" + companyInfo.get("address") + companyInfo.get("phone") + "\",");
        content.append("\"XSF_YHZH\": \"" + companyInfo.get("bankName") + companyInfo.get("bankAccount") + "\",");
        // 购买方纳税人识别号
        Object taxNo = requestInfo.get("taxNo");
        if (notEmpty(taxNo)) {
            content.append("\"GMF_NSRSBH\": \"" + taxNo + "\",");
        }
        content.append("\"GMF_MC\": \"" + requestInfo.get("invoiceTitle") + "\",");

        // 购买方地址电话
        Object address2 = requestInfo.get("address");
        Object phone2 = requestInfo.get("phone");
        if (notEmpty(address2) && notEmpty(phone2)) {
            content.append("\"GMF_DZDH\": \"" + address2 + phone2 + "\",");
        }
        // 购买方银行账号
        Object bankName2 = requestInfo.get("bankName");
        Object bankAccount2 = requestInfo.get("bankAccount");
        if (notEmpty(bankName2) && notEmpty(bankAccount2)) {
            content.append("\"GMF_YHZH\": \"" + bankName2 + bankAccount2 + "\",");
        }

        content.append("\"GMF_SJH\": \"\",");
        content.append("\"GMF_DZYX\": \"" + requestInfo.get("email") + "\",");

        content.append("\"FPT_ZH\": \"\",");
        content.append("\"WX_OPENID\": \"\",");

        content.append("\"KPR\": \"" + companyInfo.get("drawer") + "\",");
        content.append("\"SKR\": \"" + companyInfo.get("payee") + "\",");
        content.append("\"FHR\": \"" + companyInfo.get("reviewer") + "\",");

        // 金额
        BigDecimal amount = new BigDecimal(requestInfo.get("amount").toString());
        BigDecimal taxRate = new BigDecimal(companyInfo.get("taxRate").toString());

        // 合计税额
        BigDecimal hjse = amount.multiply(taxRate).divide(BigDecimal.ONE.add(taxRate), 4, RoundingMode.HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 合计金额
        BigDecimal hjje = amount.subtract(hjse);

        content.append("\"YFP_DM\": \"\",");
        content.append("\"YFP_HM\": \"\",");
        content.append("\"JSHJ\": \"" + amount + "\",");
        content.append("\"HJJE\": \"" + hjje + "\",");
        content.append("\"HJSE\": \"" + hjse + "\",");
        content.append("\"KCE\": \"\",");
        content.append("\"BZ\": \"\",");
        content.append("\"HYLX\": \"\",");
        content.append("\"BY1\": \"\",");
        content.append("\"BY2\": \"\",");
        content.append("\"BY3\": \"\",");
        content.append("\"BY4\": \"\",");
        content.append("\"BY5\": \"\",");
        content.append("\"BY6\": \"\",");
        content.append("\"BY7\": \"\",");
        content.append("\"BY8\": \"\",");
        content.append("\"BY9\": \"\",");
        content.append("\"BY10\": \"\",");
        content.append("\"WX_ORDER_ID\": \"\",");
        content.append("\"WX_APP_ID\": \"\",");
        content.append("\"ZFB_UID\": \"\",");

        content.append("\"COMMON_FPKJ_XMXXS\": {");
        content.append("\"COMMON_FPKJ_XMXX\": [{");
        content.append("\"FPHXZ\": \"0\",");
        content.append("\"SPBM\": \"3040502020200000000\",");
        content.append("\"ZXBM\": \"\",");
        content.append("\"YHZCBS\": \"\",");
        content.append("\"LSLBS\": \"\",");
        content.append("\"ZZSTSGL\": \"\",");
        content.append("\"XMMC\": \"车辆停放服务\",");
        content.append("\"GGXH\": \"\",");
        content.append("\"DW\": \"\",");
        content.append("\"XMSL\": \"1\",");
        content.append("\"XMDJ\": \"" + hjje + "\",");
        content.append("\"XMJE\": \"" + hjje + "\",");
        content.append("\"SL\": \"" + taxRate + "\",");
        content.append("\"SE\": \"" + hjse + "\",");
        content.append("\"BY1\": \"\",");
        content.append("\"BY2\": \"\",");
        content.append("\"BY3\": \"\",");
        content.append("\"BY4\": \"\",");
        content.append("\"BY5\": \"\"}]");
        content.append("}");
        content.append("}");
        content.append("}");

        PrintUtil.log("content " + content.toString());

        String str = Base64Util.encode(content.toString().getBytes("UTF-8"))
                .replaceAll("\r", "").replaceAll("\n", "");

        return str;
    }

    private static boolean notEmpty(Object obj) {
        return null != obj && 0 != obj.toString().length();
    }

    public static String MD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        byte[] b = md5.digest();
        StringBuffer buf = new StringBuffer("");

        for (int offset = 0; offset < b.length; ++offset) {
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
            return cipher.doFinal(content);
        } catch (Exception e) {
            PrintUtil.log("信息加密出错", e);
            return null;
        }
    }

}
