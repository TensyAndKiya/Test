package com.clei.Y2019.M08.D01;

import com.clei.utils.Base64Util;
import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import okhttp3.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 上海航信开票测试
 *
 * @author KIyA
 */
public class XmlXstreamTest {

    private final static String AppId = "ZZS_PT_DZFP";
    private final static String Version = "2.0";
    private final static String kp_InterfaceCode = "ECXML.FPKJ.BC.E_INV";
    private final static String XZ_InterfaceCode = "ECXML.FPXZ.CX.E_INV";
    private final static String TerminalCode = "0";
    private final static String TaxpayerId = "310101000000090";
    private final static String UserName = "111MFWIK";
    private final static String RequestCode = UserName;
    private final static String AuthorizationCode = "3100000090";
    private final static String DESKey = "9oyKs7cVo1yYzkuisP9bhA==";
    private final static String url = "http://fw1test.shdzfp.com:9000/sajt-shdzfp-sl-http/SvrServlet";
    private final static String companyName = "上海爱信诺航天信息有限公司90";
    private final static float SL = 0.13f;
    private final static String UTF_8 = StandardCharsets.UTF_8.name();

    public static void main(String[] args) throws Exception {

        String fpqqlsh = UserName + DateUtil.currentDateTime("yyyyMMddHHmmss") + getRandom(20);
        String orderId = DateUtil.currentDateTime("yyyyMMddHHmmss") + getRandom(6);
        // 开发票
        String invoiceResult = populateXML(kp_InterfaceCode, invoiceXML(fpqqlsh, orderId));
        String code = getElementContent(invoiceResult, "returnCode");
        String message = getElementContent(invoiceResult, "returnMessage");
        if (null != message && !"".equals(message)) {
            message = new String(Base64Util.decode(message), UTF_8);
        }
        if (code.equals("0000")) {
            PrintUtil.log("开具发票成功,message: " + message);
            new Thread(() -> {
                try {
                    Thread.sleep(8000);
                    // 下载
                    String result = populateXML(XZ_InterfaceCode, downloadInvoiceXML(fpqqlsh, orderId));
                    PrintUtil.log("下载发票结果：：：" + result);
                    String zipCode = getElementContent(result, "zipCode");
                    String encryptCode = getElementContent(result, "encryptCode");
                    String content = getElementContent(result, "content");
                    content = decrypt3DES(Base64Util.decode(content));

                    PrintUtil.log("really really result : " + content);

                } catch (Exception e) {
                    PrintUtil.log("发票信息处理出错", e);
                }
            }).start();
        } else {
            PrintUtil.log("开具发票失败,message: " + message);
        }
    }

    // 装备并发送xml
    private static String populateXML(String interfaceCode, String contentXML) throws Exception {
        contentXML = trim(contentXML);
        PrintUtil.log("contentXML:");
        PrintUtil.log(contentXML);
        String content = Base64Util.encode(encrypt3DES(contentXML));
        String outerXML = outerXML(interfaceCode, content);
        outerXML = trimOuter(outerXML);
        PrintUtil.log("outerXML:");
        PrintUtil.log(outerXML);
        String result = sendXML(url, outerXML);
        PrintUtil.log(result);
        return result;
    }

    private static String sendXML(String url, String xml) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml;charset=utf-8"), xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();
        // response.body().
        if (response.isSuccessful()) {
            String result = response.body().string();
            PrintUtil.log("result");
            return result;
        } else {
            return "fail";
        }
    }

    private static String outerXML(String interfaceCode, String content) {
        // 通用外层报文
        XStream stream = new XStream(new DomDriver(UTF_8, new XmlFriendlyNameCoder("-_", "_")));
        // 处理注解
        stream.processAnnotations(Interface.class);
        stream.processAnnotations(GlobalInfo.class);
        GlobalInfo info = getGlobalInfo(interfaceCode);
        ReturnStateInfo returnStateInfo = new ReturnStateInfo("", "");
        DataDescription dataDescription = new DataDescription("0", "1", "3DES");
        Data data = new Data(dataDescription, content);
        Interface iFace = new Interface(info, returnStateInfo, data);
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        return xml + stream.toXML(iFace);
    }

    private static String invoiceXML(String fpqqlsh, String orderId) {
        // 开具发票报文
        XStream stream = new XStream(new DomDriver(UTF_8, new XmlFriendlyNameCoder("-_", "_")));
        // 处理注解
        stream.processAnnotations(CreateInvoiceRequest.class);
        stream.processAnnotations(InvoiceHeader.class);
        stream.processAnnotations(InvoiceItems.class);
        stream.processAnnotations(InvoiceItem.class);
        stream.processAnnotations(InvoiceOrder.class);
        // 填充数据
        InvoiceItems invoiceItems = getInvoiceItems();
        InvoiceHeader invoiceHeader = getInvoiceHeader(fpqqlsh, invoiceItems);
        InvoiceOrder invoiceOrder = new InvoiceOrder(orderId);

        CreateInvoiceRequest invoiceRequest = new CreateInvoiceRequest(invoiceHeader, invoiceItems, invoiceOrder);
        return stream.toXML(invoiceRequest);
    }

    private static String downloadInvoiceXML(String fpqqlsh, String orderId) {
        // 下载发票报文
        XStream stream = new XStream(new DomDriver(UTF_8, new XmlFriendlyNameCoder("-_", "_")));
        // 处理注解
        stream.processAnnotations(DownloadInvoiceRequest.class);
        // 填充数据
        DownloadInvoiceRequest invoiceRequest = new DownloadInvoiceRequest();
        invoiceRequest.setFpqqlsh(fpqqlsh);
        invoiceRequest.setDsptbm(UserName);
        invoiceRequest.setNsrsbh(TaxpayerId);
        invoiceRequest.setDdh(orderId);
        return stream.toXML(invoiceRequest);
    }

    private static GlobalInfo getGlobalInfo(String interfaceCode) {
        GlobalInfo info = new GlobalInfo();
        info.setTerminalCode(TerminalCode);
        info.setAppId(AppId);
        info.setVersion(Version);
        info.setInterfaceCode(interfaceCode);
        info.setUserName(UserName);
        info.setRequestCode(RequestCode);
        info.setRequestTime(DateUtil.currentDateTime("yyyy-MM-dd HH:mm:ss ss"));
        info.setTaxpayerId(TaxpayerId);
        info.setAuthorizationCode(AuthorizationCode);
        info.setDataExchangeId(getRandom(50));
        return info;
    }

    private static InvoiceHeader getInvoiceHeader(String fpqqlsh, InvoiceItems invoiceItems) {
        InvoiceHeader invoiceHeader = new InvoiceHeader();
        invoiceHeader.setFpqqlsh(fpqqlsh);
        invoiceHeader.setDsptbm(UserName);
        invoiceHeader.setNsrsbh(TaxpayerId);
        invoiceHeader.setNsrmc(companyName);
        invoiceHeader.setDkbz("0");
        invoiceHeader.setKpxm("车辆停放服务");
        invoiceHeader.setBmbbbh("33.0");
        invoiceHeader.setXhfnsrsbh(TaxpayerId);
        invoiceHeader.setXhfmc(companyName);
        invoiceHeader.setXhfdz("天津市-武清区");
        invoiceHeader.setXhfdh("13810189561");
        invoiceHeader.setXhfyhzh("saleAccountNo");
        invoiceHeader.setGhfmc("个人");
        invoiceHeader.setGhfqylx("03");
        invoiceHeader.setGhfsj("184XXXXXXXX");
        invoiceHeader.setGhfemail("yueyaye@163.com");
        invoiceHeader.setKpy("张三");
        // invoiceHeader.setSky("收款人");
        // invoiceHeader.setFhr("复核人");
        invoiceHeader.setKplx("1");
        invoiceHeader.setTschbz("0");
        invoiceHeader.setCzdm("10");
        invoiceHeader.setQdbz("0");
        // 价税合计 税额和不含税金额计算
        BigDecimal hjje = new BigDecimal(invoiceItems.getInvoiceItem().getXmje());
        float hjse = getScaleFloat((hjje.floatValue() * SL) / (1 + SL));
        invoiceHeader.setKphjje("" + hjje.floatValue());
        invoiceHeader.setHjbhsje("" + getScaleFloat(hjje.floatValue() - hjse));
        invoiceHeader.setHjse("" + hjse);
        invoiceHeader.setBz("备注");
        return invoiceHeader;
    }


    private static InvoiceItems getInvoiceItems() {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setXmmc("车辆停放服务");
        // invoiceItem.setXmdw("");
        // invoiceItem.setGgxh("");
        invoiceItem.setXmsl("1");
        invoiceItem.setHsbz("1");
        invoiceItem.setFphxz("0");
        invoiceItem.setSpbm("3040502020200000000");
        invoiceItem.setYhzcbs("0");
        invoiceItem.setXmje("100");
        invoiceItem.setSl("" + SL);
        return new InvoiceItems(invoiceItem);
    }

    private static String getRandom(int num) {
//      48-57 65-90 97-122
        StringBuffer id = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            char s = 0;
            int j = random.nextInt(3) % 4;
            switch (j) {
                case 0:
                    //随机生成数字
                    s = (char) (random.nextInt(57) % (57 - 48 + 1) + 48);
                    break;
                case 1:
                    //随机生成大写字母
                    s = (char) (random.nextInt(90) % (90 - 65 + 1) + 65);
                    break;
                case 2:
                    //随机生成小写字母
                    s = (char) (random.nextInt(122) % (122 - 97 + 1) + 97);
                    break;
            }
            id.append(s);
        }
        return id.toString();
    }

    private static String trim(String str) {
        return str.replaceAll("  ", "")
                .replaceAll("\n", "");
    }

    private static String trimOuter(String str) {
        // 使用xstrem会多出来 &#xd;
        return trim(str).replaceAll("&#xd;", "\n");
    }

    private static float getScaleFloat(float f) {
        return new BigDecimal(f).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    private static String getElementContent(String xml, String element) {
        String startElement = '<' + element + '>';
        int start = xml.indexOf(startElement);
        if (start == -1) {
            PrintUtil.log("错误，返回结果不包含" + element + "信息");
            return null;
        }
        String endElement = "</" + element + '>';
        int end = xml.indexOf(endElement);
        String value = xml.substring(start + startElement.length(), end);
        return value;
    }

    private static String zip(String str) {
        return str;
    }

    private static String unZip(String str) {
        return str;
    }

    private static byte[] encrypt3DES(String str) throws Exception {
        String algorithm = "DESede";
        // key
        SecretKey secretKey = new SecretKeySpec(DESKey.getBytes(UTF_8), algorithm);
        // 加密
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(str.getBytes(UTF_8));
    }

    private static String decrypt3DES(byte[] bytes) throws Exception {
        // key
        String algorithm = "DESede";
        SecretKey secretKey = new SecretKeySpec(DESKey.getBytes(UTF_8), algorithm);
        // 解密
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] result = cipher.doFinal(bytes);
        return new String(result, UTF_8);
    }
}

@XStreamAlias("interface")
class Interface {

    @XStreamAsAttribute
    private String xmlns = "";
    @XStreamAlias("xmlns:xsi")
    @XStreamAsAttribute
    private String xmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";
    @XStreamAlias("xsi:schemaLocation")
    @XStreamAsAttribute
    private String xsiSchemaLocation = "http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd";
    @XStreamAsAttribute
    private String version = "DZFP1.0";
    private GlobalInfo globalInfo;
    private ReturnStateInfo returnStateInfo;
    @XStreamAlias("Data")
    private Data data;

    public Interface(GlobalInfo globalInfo, ReturnStateInfo returnStateInfo, Data data) {
        this.globalInfo = globalInfo;
        this.returnStateInfo = returnStateInfo;
        this.data = data;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getXmlnsXsi() {
        return xmlnsXsi;
    }

    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi;
    }

    public String getXsiSchemaLocation() {
        return xsiSchemaLocation;
    }

    public void setXsiSchemaLocation(String xsiSchemaLocation) {
        this.xsiSchemaLocation = xsiSchemaLocation;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public GlobalInfo getGlobalInfo() {
        return globalInfo;
    }

    public void setGlobalInfo(GlobalInfo globalInfo) {
        this.globalInfo = globalInfo;
    }

    public ReturnStateInfo getReturnStateInfo() {
        return returnStateInfo;
    }

    public void setReturnStateInfo(ReturnStateInfo returnStateInfo) {
        this.returnStateInfo = returnStateInfo;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

class GlobalInfo {

    private String terminalCode = "";
    private String appId = "";
    private String version = "";
    private String interfaceCode = "";
    private String userName = "";
    private String passWord = "";
    private String taxpayerId = "";
    private String authorizationCode = "";
    private String requestCode = "";
    private String requestTime = "";
    private String responseCode = "";
    private String dataExchangeId = "";

    public GlobalInfo() {
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDataExchangeId() {
        return dataExchangeId;
    }

    public void setDataExchangeId(String dataExchangeId) {
        this.dataExchangeId = dataExchangeId;
    }
}

class ReturnStateInfo {

    private String returnCode = "";
    private String returnMessage = "";

    public ReturnStateInfo(String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }

    public ReturnStateInfo() {
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}

class Data {

    private DataDescription dataDescription;
    private String content = "";

    public Data(DataDescription dataDescription, String content) {
        this.dataDescription = dataDescription;
        this.content = content;
    }

    public Data() {
    }

    public DataDescription getDataDescription() {
        return dataDescription;
    }

    public void setDataDescription(DataDescription dataDescription) {
        this.dataDescription = dataDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class DataDescription {

    private String zipCode = "";
    private String encryptCode = "";
    private String codeType = "";

    public DataDescription(String zipCode, String encryptCode, String codeType) {
        this.zipCode = zipCode;
        this.encryptCode = encryptCode;
        this.codeType = codeType;
    }

    public DataDescription() {
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEncryptCode() {
        return encryptCode;
    }

    public void setEncryptCode(String encryptCode) {
        this.encryptCode = encryptCode;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}

@XStreamAlias("REQUEST_FPXXXZ_NEW")
class DownloadInvoiceRequest {

    @XStreamAlias("class")
    @XStreamAsAttribute
    private String type = "REQUEST_FPXXXZ_NEW";
    @XStreamAlias("FPQQLSH")
    private String fpqqlsh;
    @XStreamAlias("DSPTBM")
    private String dsptbm;
    @XStreamAlias("NSRSBH")
    private String nsrsbh;
    @XStreamAlias("DDH")
    private String ddh;
    @XStreamAlias("PDF_XZFS")
    private String pdfxzfs = "2";

    public DownloadInvoiceRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }

    public String getDsptbm() {
        return dsptbm;
    }

    public void setDsptbm(String dsptbm) {
        this.dsptbm = dsptbm;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getDdh() {
        return ddh;
    }

    public void setDdh(String ddh) {
        this.ddh = ddh;
    }

    public String getPdfxzfs() {
        return pdfxzfs;
    }

    public void setPdfxzfs(String pdfxzfs) {
        this.pdfxzfs = pdfxzfs;
    }
}

@XStreamAlias("REQUEST_FPKJXX")
class CreateInvoiceRequest {

    @XStreamAlias("class")
    @XStreamAsAttribute
    private String type = "REQUEST_FPKJXX";
    @XStreamAlias("FPKJXX_FPTXX")
    private InvoiceHeader invoiceHeader;
    @XStreamAlias("FPKJXX_XMXXS")
    private InvoiceItems invoiceItems;
    @XStreamAlias("FPKJXX_DDXX")
    private InvoiceOrder invoiceOrder;

    public CreateInvoiceRequest(InvoiceHeader invoiceHeader, InvoiceItems invoiceItems, InvoiceOrder invoiceOrder) {
        this.invoiceHeader = invoiceHeader;
        this.invoiceItems = invoiceItems;
        this.invoiceOrder = invoiceOrder;
    }

    public String getType() {
        return type;
    }

    public InvoiceHeader getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(InvoiceHeader invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public InvoiceItems getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(InvoiceItems invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public InvoiceOrder getInvoiceOrder() {
        return invoiceOrder;
    }

    public void setInvoiceOrder(InvoiceOrder invoiceOrder) {
        this.invoiceOrder = invoiceOrder;
    }
}

class InvoiceHeader {

    @XStreamAlias("class")
    @XStreamAsAttribute
    private String type = "FPKJXX_FPTXX";
    @XStreamAlias("FPQQLSH")
    private String fpqqlsh = "";
    @XStreamAlias("DSPTBM")
    private String dsptbm = "";
    @XStreamAlias("NSRSBH")
    private String nsrsbh = "";
    @XStreamAlias("NSRMC")
    private String nsrmc = "";
    @XStreamAlias("NSRDZDAH")
    private String nsrdzdah = "";
    @XStreamAlias("SWJG_DM")
    private String swjgdm = "";
    @XStreamAlias("DKBZ")
    private String dkbz = "";
    @XStreamAlias("SGBZ")
    private String sgbz = "";
    @XStreamAlias("PYDM")
    private String pydm = "";
    @XStreamAlias("KPXM")
    private String kpxm = "";
    @XStreamAlias("BMB_BBH")
    private String bmbbbh = "";
    @XStreamAlias("XHF_NSRSBH")
    private String xhfnsrsbh = "";
    @XStreamAlias("XHFMC")
    private String xhfmc = "";
    @XStreamAlias("XHF_DZ")
    private String xhfdz = "";
    @XStreamAlias("XHF_DH")
    private String xhfdh = "";
    @XStreamAlias("XHF_YHZH")
    private String xhfyhzh = "";
    @XStreamAlias("GHFMC")
    private String ghfmc = "";
    @XStreamAlias("GHF_NSRSBH")
    private String ghfnsrsbh = "";
    @XStreamAlias("GHF_SF")
    private String ghfsf = "";
    @XStreamAlias("GHF_DZ")
    private String ghfdz = "";
    @XStreamAlias("GHF_GDDH")
    private String ghfgddh = "";
    @XStreamAlias("GHF_SJ")
    private String ghfsj = "";
    @XStreamAlias("GHF_EMAIL")
    private String ghfemail = "";
    @XStreamAlias("GHFQYLX")
    private String ghfqylx = "";
    @XStreamAlias("GHF_YHZH")
    private String ghfyhzh = "";
    @XStreamAlias("HY_DM")
    private String hydm = "";
    @XStreamAlias("HY_MC")
    private String hymc = "";
    @XStreamAlias("KPY")
    private String kpy = "";
    @XStreamAlias("SKY")
    private String sky = "";
    @XStreamAlias("FHR")
    private String fhr = "";
    @XStreamAlias("KPRQ")
    private String kprq = "";
    @XStreamAlias("KPLX")
    private String kplx = "";
    @XStreamAlias("YFP_DM")
    private String yfpdm = "";
    @XStreamAlias("YFP_HM")
    private String yfphm = "";
    @XStreamAlias("CZDM")
    private String czdm = "";
    @XStreamAlias("QD_BZ")
    private String qdbz = "";
    @XStreamAlias("QDXMMC")
    private String qdxmmc = "";
    @XStreamAlias("CHYY")
    private String chyy = "";
    @XStreamAlias("TSCHBZ")
    private String tschbz = "";
    @XStreamAlias("KPHJJE")
    private String kphjje = "";
    @XStreamAlias("HJBHSJE")
    private String hjbhsje = "";
    @XStreamAlias("HJSE")
    private String hjse = "";
    @XStreamAlias("BZ")
    private String bz = "";
    @XStreamAlias("BYZD1")
    private String byzd1 = "";
    @XStreamAlias("BYZD2")
    private String byzd2 = "";
    @XStreamAlias("BYZD3")
    private String byzd3 = "";
    @XStreamAlias("BYZD4")
    private String byzd4 = "";
    @XStreamAlias("BYZD5")
    private String byzd5 = "";

    public InvoiceHeader() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }

    public String getDsptbm() {
        return dsptbm;
    }

    public void setDsptbm(String dsptbm) {
        this.dsptbm = dsptbm;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getNsrdzdah() {
        return nsrdzdah;
    }

    public void setNsrdzdah(String nsrdzdah) {
        this.nsrdzdah = nsrdzdah;
    }

    public String getSwjgdm() {
        return swjgdm;
    }

    public void setSwjgdm(String swjgdm) {
        this.swjgdm = swjgdm;
    }

    public String getDkbz() {
        return dkbz;
    }

    public void setDkbz(String dkbz) {
        this.dkbz = dkbz;
    }

    public String getSgbz() {
        return sgbz;
    }

    public void setSgbz(String sgbz) {
        this.sgbz = sgbz;
    }

    public String getPydm() {
        return pydm;
    }

    public void setPydm(String pydm) {
        this.pydm = pydm;
    }

    public String getKpxm() {
        return kpxm;
    }

    public void setKpxm(String kpxm) {
        this.kpxm = kpxm;
    }

    public String getBmbbbh() {
        return bmbbbh;
    }

    public void setBmbbbh(String bmbbbh) {
        this.bmbbbh = bmbbbh;
    }

    public String getXhfnsrsbh() {
        return xhfnsrsbh;
    }

    public void setXhfnsrsbh(String xhfnsrsbh) {
        this.xhfnsrsbh = xhfnsrsbh;
    }

    public String getXhfmc() {
        return xhfmc;
    }

    public void setXhfmc(String xhfmc) {
        this.xhfmc = xhfmc;
    }

    public String getXhfdz() {
        return xhfdz;
    }

    public void setXhfdz(String xhfdz) {
        this.xhfdz = xhfdz;
    }

    public String getXhfdh() {
        return xhfdh;
    }

    public void setXhfdh(String xhfdh) {
        this.xhfdh = xhfdh;
    }

    public String getXhfyhzh() {
        return xhfyhzh;
    }

    public void setXhfyhzh(String xhfyhzh) {
        this.xhfyhzh = xhfyhzh;
    }

    public String getGhfmc() {
        return ghfmc;
    }

    public void setGhfmc(String ghfmc) {
        this.ghfmc = ghfmc;
    }

    public String getGhfnsrsbh() {
        return ghfnsrsbh;
    }

    public void setGhfnsrsbh(String ghfnsrsbh) {
        this.ghfnsrsbh = ghfnsrsbh;
    }

    public String getGhfdz() {
        return ghfdz;
    }

    public void setGhfdz(String ghfdz) {
        this.ghfdz = ghfdz;
    }

    public String getGhfsf() {
        return ghfsf;
    }

    public void setGhfsf(String ghfsf) {
        this.ghfsf = ghfsf;
    }

    public String getGhfgddh() {
        return ghfgddh;
    }

    public void setGhfgddh(String ghfgddh) {
        this.ghfgddh = ghfgddh;
    }

    public String getGhfsj() {
        return ghfsj;
    }

    public void setGhfsj(String ghfsj) {
        this.ghfsj = ghfsj;
    }

    public String getGhfemail() {
        return ghfemail;
    }

    public void setGhfemail(String ghfemail) {
        this.ghfemail = ghfemail;
    }

    public String getGhfqylx() {
        return ghfqylx;
    }

    public void setGhfqylx(String ghfqylx) {
        this.ghfqylx = ghfqylx;
    }

    public String getGhfyhzh() {
        return ghfyhzh;
    }

    public void setGhfyhzh(String ghfyhzh) {
        this.ghfyhzh = ghfyhzh;
    }

    public String getHydm() {
        return hydm;
    }

    public void setHydm(String hydm) {
        this.hydm = hydm;
    }

    public String getHymc() {
        return hymc;
    }

    public void setHymc(String hymc) {
        this.hymc = hymc;
    }

    public String getKpy() {
        return kpy;
    }

    public void setKpy(String kpy) {
        this.kpy = kpy;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public String getFhr() {
        return fhr;
    }

    public void setFhr(String fhr) {
        this.fhr = fhr;
    }

    public String getKprq() {
        return kprq;
    }

    public void setKprq(String kprq) {
        this.kprq = kprq;
    }

    public String getKplx() {
        return kplx;
    }

    public void setKplx(String kplx) {
        this.kplx = kplx;
    }

    public String getYfpdm() {
        return yfpdm;
    }

    public void setYfpdm(String yfpdm) {
        this.yfpdm = yfpdm;
    }

    public String getYfphm() {
        return yfphm;
    }

    public void setYfphm(String yfphm) {
        this.yfphm = yfphm;
    }

    public String getTschbz() {
        return tschbz;
    }

    public void setTschbz(String tschbz) {
        this.tschbz = tschbz;
    }

    public String getCzdm() {
        return czdm;
    }

    public void setCzdm(String czdm) {
        this.czdm = czdm;
    }

    public String getQdbz() {
        return qdbz;
    }

    public void setQdbz(String qdbz) {
        this.qdbz = qdbz;
    }

    public String getQdxmmc() {
        return qdxmmc;
    }

    public void setQdxmmc(String qdxmmc) {
        this.qdxmmc = qdxmmc;
    }

    public String getChyy() {
        return chyy;
    }

    public void setChyy(String chyy) {
        this.chyy = chyy;
    }

    public String getKphjje() {
        return kphjje;
    }

    public void setKphjje(String kphjje) {
        this.kphjje = kphjje;
    }

    public String getHjbhsje() {
        return hjbhsje;
    }

    public void setHjbhsje(String hjbhsje) {
        this.hjbhsje = hjbhsje;
    }

    public String getHjse() {
        return hjse;
    }

    public void setHjse(String hjse) {
        this.hjse = hjse;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getByzd1() {
        return byzd1;
    }

    public void setByzd1(String byzd1) {
        this.byzd1 = byzd1;
    }

    public String getByzd2() {
        return byzd2;
    }

    public void setByzd2(String byzd2) {
        this.byzd2 = byzd2;
    }

    public String getByzd3() {
        return byzd3;
    }

    public void setByzd3(String byzd3) {
        this.byzd3 = byzd3;
    }

    public String getByzd4() {
        return byzd4;
    }

    public void setByzd4(String byzd4) {
        this.byzd4 = byzd4;
    }

    public String getByzd5() {
        return byzd5;
    }

    public void setByzd5(String byzd5) {
        this.byzd5 = byzd5;
    }
}

class InvoiceItems {

    // 去他大爷滴，这里少了个分号就爆9219 内层报文格式存在问题！！！
    @XStreamAlias("class")
    @XStreamAsAttribute
    private String type = "FPKJXX_XMXX;";
    @XStreamAsAttribute
    private String size = "1";
    @XStreamAlias("FPKJXX_XMXX")
    private InvoiceItem invoiceItem;

    public InvoiceItems(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }
}

class InvoiceItem {

    @XStreamAlias("XMMC")
    private String xmmc = "";
    @XStreamAlias("XMDW")
    private String xmdw = "";
    @XStreamAlias("GGXH")
    private String ggxh = "";
    @XStreamAlias("XMSL")
    private String xmsl = "";
    @XStreamAlias("HSBZ")
    private String hsbz = "";
    @XStreamAlias("XMDJ")
    private String xmdj = "";
    @XStreamAlias("FPHXZ")
    private String fphxz = "";
    @XStreamAlias("SPBM")
    private String spbm = "";
    @XStreamAlias("ZXBM")
    private String zxbm = "";
    @XStreamAlias("YHZCBS")
    private String yhzcbs = "";
    @XStreamAlias("LSLBS")
    private String lslbs = "";
    @XStreamAlias("ZZSTSGL")
    private String zzstsgl = "";
    @XStreamAlias("KCE")
    private String kce = "";
    @XStreamAlias("XMJE")
    private String xmje = "";
    @XStreamAlias("SL")
    private String sl = "";
    @XStreamAlias("SE")
    private String se = "";
    @XStreamAlias("BYZD1")
    private String byzd1 = "";
    @XStreamAlias("BYZD2")
    private String byzd2 = "";
    @XStreamAlias("BYZD3")
    private String byzd3 = "";
    @XStreamAlias("BYZD4")
    private String byzd4 = "";
    @XStreamAlias("BYZD5")
    private String byzd5 = "";

    public InvoiceItem() {
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getXmdw() {
        return xmdw;
    }

    public void setXmdw(String xmdw) {
        this.xmdw = xmdw;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public String getXmsl() {
        return xmsl;
    }

    public void setXmsl(String xmsl) {
        this.xmsl = xmsl;
    }

    public String getHsbz() {
        return hsbz;
    }

    public void setHsbz(String hsbz) {
        this.hsbz = hsbz;
    }

    public String getFphxz() {
        return fphxz;
    }

    public void setFphxz(String fphxz) {
        this.fphxz = fphxz;
    }

    public String getXmdj() {
        return xmdj;
    }

    public void setXmdj(String xmdj) {
        this.xmdj = xmdj;
    }

    public String getSpbm() {
        return spbm;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getZxbm() {
        return zxbm;
    }

    public void setZxbm(String zxbm) {
        this.zxbm = zxbm;
    }

    public String getYhzcbs() {
        return yhzcbs;
    }

    public void setYhzcbs(String yhzcbs) {
        this.yhzcbs = yhzcbs;
    }

    public String getXmje() {
        return xmje;
    }

    public void setXmje(String xmje) {
        this.xmje = xmje;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public String getByzd1() {
        return byzd1;
    }

    public void setByzd1(String byzd1) {
        this.byzd1 = byzd1;
    }

    public String getByzd2() {
        return byzd2;
    }

    public void setByzd2(String byzd2) {
        this.byzd2 = byzd2;
    }

    public String getByzd3() {
        return byzd3;
    }

    public void setByzd3(String byzd3) {
        this.byzd3 = byzd3;
    }

    public String getByzd4() {
        return byzd4;
    }

    public void setByzd4(String byzd4) {
        this.byzd4 = byzd4;
    }

    public String getByzd5() {
        return byzd5;
    }

    public void setByzd5(String byzd5) {
        this.byzd5 = byzd5;
    }
}

class InvoiceOrder {

    @XStreamAlias("class")
    @XStreamAsAttribute
    private String type = "FPKJXX_DDXX";
    @XStreamAlias("DDH")
    private String ddh = "";
    @XStreamAlias("THDH")
    private String thdh = "";
    @XStreamAlias("DDDATE")
    private String dddate = "";

    public InvoiceOrder(String ddh) {
        this.ddh = ddh;
    }

    public String getType() {
        return type;
    }

    public String getDdh() {
        return ddh;
    }

    public void setDdh(String ddh) {
        this.ddh = ddh;
    }

    public String getThdh() {
        return thdh;
    }

    public void setThdh(String thdh) {
        this.thdh = thdh;
    }

    public String getDddate() {
        return dddate;
    }

    public void setDddate(String dddate) {
        this.dddate = dddate;
    }
}
