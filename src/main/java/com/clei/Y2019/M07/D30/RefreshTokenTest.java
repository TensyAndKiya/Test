package com.clei.Y2019.M07.D30;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 重构下获取token的代码
 * 之前的代码可能有多个用户同时刷新token的可能，这样前面获取的token会失效
 * 开发票就会失败
 */
public class RefreshTokenTest {
    private ConcurrentHashMap<String,byte[]> lockMap = new ConcurrentHashMap<>(2);
    private ConcurrentHashMap<String,AccessToken> TokenDB = new ConcurrentHashMap<>(2);

    public static void main(String[] args) throws InterruptedException {
        RefreshTokenTest rtt = new RefreshTokenTest();
        rtt.initDB();
        Map<String,Object> map = new HashMap<>();
        map.put("parkId","park");
        for (int i = 0; i < 100; i++) {
            rtt.newInvoice(map,"张三");
            Thread.sleep(4000);
        }
    }

    private boolean newInvoice(Map<String,Object> info,String userName){
        AccessToken token = getToken(info.get("parkId").toString());
        if(null == token){
            return false;
        }
        if(null != userName){
            // TODO
            return true;
        }
        return false;
    }

    private AccessToken getToken(String parkId){
        AccessToken token = selectTokenFromDB(parkId);
        // 首次获取
        if(null == token.getToken() || "".equals(token.getToken())){
            return refreshToken(token);
        }
        // 距离过期时间小于3分钟的话
        // 测试一下 改成 5秒
        if(new Date().getTime() - token.getRefreshTime() < 5000){
            return refreshToken(token);
        }
        return token;
    }

    /**
     *
     * @param token
     * @return
     */
    private AccessToken refreshToken(AccessToken token){
        String parkId = token.getParkId();
        byte[] lock = lockMap.get(parkId);
        if(null == lock){
            lockMap.put(parkId,new byte[0]);
        }
        String t = token.getToken();
        lock = lockMap.get(parkId);
        if(null != t && !"".equals(t)){
            synchronized (lock){
                if(null != t && !"".equals(t)){
                    return getTokenFromOther(token);
                }
            }
        }
        return selectTokenFromDB(parkId);
    }

    private void initDB(){
        AccessToken token = new AccessToken(1,"park","aaa","bb","cc",3600,new Date().getTime());
        TokenDB.put("park",token);
    }

    private AccessToken selectTokenFromDB(String parkId){
        return TokenDB.get(parkId);
    }

    // 从第三方获取token
    private AccessToken getTokenFromOther(AccessToken token){
        Random random = new Random();
        String str = "" + random.nextInt() + random.nextInt() + random.nextInt();
        str = str.substring(0,3);
        AccessToken newToken = new AccessToken(token.getId(),token.getParkId(),str,token.getOpenId(),token.getAppSecret(),3600,new Date().getTime());
        TokenDB.put(newToken.getParkId(),newToken);
        return newToken;
    }
}

class AccessToken{
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