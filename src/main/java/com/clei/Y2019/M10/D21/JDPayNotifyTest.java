package com.clei.Y2019.M10.D21;

import com.clei.utils.OkHttpUtil;
import com.clei.utils.PrintUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JDPayNotifyTest {
    private final static String PAY_NOTIFY_URL = "http://localhost:4018/jdPayResult";
    public static void main(String[] args) {

        PrintUtil.dateLine(System.currentTimeMillis());
        PrintUtil.dateLine(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        String notifyStr = "{\"cipherJson\":\"ffae08a1ea0090cca0da13d925d49a76bd842f8cd5d74f3a902ed563db6edb55494d57274aa14bfbea46df6f314707d8bba6aef2f928adb1395e31abdfb4092c58de5047f2ddadd953b7366a3c7bac070a94b4a4790a70412c32c3977b432d960e1043fe22e8c473184898312e70d983a6a375ccc6f2fa023163044f54839c52f597f15f0109c1ca0e2eeca3a7349d492034844090f93f66b1d61e0e012cf249a3a213726b913fd6741adc146e4c4dc087cfe896c433e2d768ac152fb632e7d1ca821a5cde3de3dc0f3bf80fec8e9bb68fe1a69f98403e554e0e16b779a52101c0e763af6f6503ca94055b8a3d38150af4106c0f89b6c3bc12e843a7c1977e0bad297825dfc8bb6743e5797235bdfb19c2fef6b05af56470eb677cb9b60c5804e749f887b8f2121c6e933fea23676c26c35f4c7ea88bf2ef049f1fba04b6122f8fe1a69f98403e55533546c34e933ce39ec3e978258f30d91b0a5b59b5613d4dabc0059ebbe19fa55a71a1dc888fd15fbce240fbce4a18f4d16e748f37b6beef8dc1052d8154818b193e2ed1f5235261d464aa2b7aa5f853c336e9f654c3eb11315d99cf00ca30c6b1f50c02514ee07fd32eea1de25f7bcb6d52611b507a9147eaa6306d2a20cae52e6b442ee17f2238a5a35024aa3fc21637685a13f247410df0675d4d7fb2d01ac2fef6b05af564700be940efb0d7d1593d46d8028f072bb4e1ccb3b538ef212e20540c9d7319c6d72b6ed15448c553c7a3c6eecd04643c3fb0e0f50f04997e7c9b2eb1e13e4c1aed3d5f5265df7ecdd2203fd4f96799a302959bbb0fc78c69198381d03d31455b167b073c612bc099f4\",\"errCode\":\"000000\",\"errCodeDes\":\"成功\",\"merchantNo\":\"110826750\",\"sign\":\"d20d983abc22888918678196cec92ec4\",\"success\":true,\"systemId\":\"JD_MUYING\"}";
        OkHttpUtil.doPost(PAY_NOTIFY_URL, notifyStr);
    }
}
