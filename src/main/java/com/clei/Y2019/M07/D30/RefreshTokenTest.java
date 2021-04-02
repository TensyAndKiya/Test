package com.clei.Y2019.M07.D30;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;
import com.clei.utils.ThreadUtil;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 重构下获取token的代码
 * 之前的代码可能有多个用户同时刷新token的可能，这样前面获取的token会失效
 * 开发票就会失败
 *
 * @author KIyA
 */
public class RefreshTokenTest {

    private final ConcurrentHashMap<String, AccessToken> TokenDB = new ConcurrentHashMap<>(2);
    private final static long EXPIRE_LIMIT_MILLI = 5L * 60 * 1000;
    private final static CopyOnWriteArraySet<String> TOKEN_SET = new CopyOnWriteArraySet<>();

    public static void main(String[] args) throws Exception {
        RefreshTokenTest rtt = new RefreshTokenTest();
        rtt.initDB();
        String parkId = "park";
        ThreadPoolExecutor pool = ThreadUtil.pool();

        int times = 1000;
        CountDownLatch latch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            pool.execute(() -> {
                rtt.newInvoice(parkId, "张三");
                latch.countDown();
            });
        }

        latch.await();
        pool.shutdown();
        TOKEN_SET.forEach(PrintUtil::log);
    }

    private boolean newInvoice(String parkId, String userName) {
        if (StringUtil.isBlank(parkId) || StringUtil.isBlank(userName)) {
            return false;
        }
        AccessToken token = getToken(parkId);
        if (null == token) {
            return false;
        }

        // 收集token看看利用率
        TOKEN_SET.add(token.getToken());
        // 开票操作
        return true;
    }

    private AccessToken getToken(String parkId) {
        AccessToken token = selectTokenFromDB(parkId);
        // 未配置开票相关信息
        if (null == token) {
            return null;
        }
        // 首次获取
        if (StringUtil.isBlank(token.getToken())) {
            return refreshToken(token);
        }
        // 距离过期时间小于N毫秒的话
        if (System.currentTimeMillis() - token.getRefreshTime() > EXPIRE_LIMIT_MILLI) {
            // 假装token已经过期了
            token.setToken(null);
            return refreshToken(token);
        }
        return token;
    }

    /**
     * @param token
     * @return
     */
    private AccessToken refreshToken(AccessToken token) {
        String parkId = token.getParkId();
        // 只有几百个停车场 intern没风险
        synchronized (parkId.intern()) {
            if (StringUtil.isBlank(token.getToken())) {
                return getTokenFromOther(token);
            }
        }
        return token;
    }

    private void initDB() {
        AccessToken token = new AccessToken(1, "park", "aaa", "bb", "cc", 3600, System.currentTimeMillis() - EXPIRE_LIMIT_MILLI - 1);
        TokenDB.put("park", token);
    }

    private AccessToken selectTokenFromDB(String parkId) {
        return TokenDB.get(parkId);
    }

    /**
     * 从第三方获取token
     *
     * @param token
     * @return
     */
    private AccessToken getTokenFromOther(AccessToken token) {
        Random random = new Random();
        int i = random.ints(1, 100, 1000).toArray()[0];
        String accessToken = String.valueOf(i);

        token.setRefreshTime(3600);
        token.setToken(accessToken);
        token.setRefreshTime(System.currentTimeMillis());

        TokenDB.put(token.getParkId(), token);
        return token;
    }
}

class AccessToken {

    private Integer id;
    private String parkId;
    private String token;
    private String openId;
    private String appSecret;
    private int expireSeconds;
    private long refreshTime;

    public AccessToken() {
    }

    public AccessToken(Integer id, String parkId, String token, String openId, String appSecret, int expireSeconds, long refreshTime) {
        this.id = id;
        this.parkId = parkId;
        this.token = token;
        this.openId = openId;
        this.appSecret = appSecret;
        this.expireSeconds = expireSeconds;
        this.refreshTime = refreshTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }
}