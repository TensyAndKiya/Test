package com.clei.Y2020.M11.D04;

import com.clei.utils.DownloadUtil;

/**
 * java下载文件测试
 *
 * @author KIyA
 */
public class DownloadTest {

    public static void main(String[] args) throws Exception {
        String fileUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604492961961&di=657cafbfc1a5355d6f1688cde50c91c6&imgtype=0&src=http%3A%2F%2Ftc.sinaimg.cn%2Fmaxwidth.2048%2Ftc.service.weibo.com%2Fp3_pstatp_com%2Ff3488df480263bcc05c855d536d211ee.jpg";
        String toFile = "D:\\Temp\\xxx.jpg";
        DownloadUtil.downloadToLocal(fileUrl, toFile);
    }

}
