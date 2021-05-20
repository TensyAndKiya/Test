package com.clei.netty.websocket;

import com.clei.consts.NettyConstants;
import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session manager
 *
 * @author KIyA
 */
public class SessionManager {

    /**
     * websocket server account
     */
    private final static Map<String, String> ACCOUNTS = new HashMap(2) {
        {
            put("0001", "张三");
            put("0002", "李四");
        }
    };

    /**
     * Channel - User
     */
    private final static ConcurrentHashMap<Integer, String> CHANNEL_USER = new ConcurrentHashMap<>(4);

    /**
     * User - Channel
     */
    private final static ConcurrentHashMap<String, Channel> USER_CHANNEL = new ConcurrentHashMap<>(4);


    /**
     * 绑定用户关联关系
     *
     * @param ctx       ChannelHandlerContext
     * @param msg       消息
     * @param bindIndex
     */
    public static void bindUser(ChannelHandlerContext ctx, String msg, int bindIndex) {
        Channel client = ctx.channel();
        Integer hashCode = client.hashCode();
        String userId = msg.substring(bindIndex + NettyConstants.BIND_USER.length());
        if (CHANNEL_USER.containsKey(hashCode)) {
            client.writeAndFlush(new TextWebSocketFrame("用户已登录"));
            PrintUtil.log("用户已登录 userId : {}", userId);
            return;
        }

        if (USER_CHANNEL.containsKey(userId)) {
            client.writeAndFlush(new TextWebSocketFrame("当前账户已在使用中"));
            PrintUtil.log("当前账户已在使用中 userId : {}", userId);
            ctx.close();
            return;
        }

        if (!ACCOUNTS.containsKey(userId)) {
            client.writeAndFlush(new TextWebSocketFrame("账户不存在"));
            PrintUtil.log("账户不存在 userId : {}", userId);
            ctx.close();
            return;
        }

        CHANNEL_USER.put(hashCode, userId);
        USER_CHANNEL.put(userId, client);
        client.writeAndFlush(new TextWebSocketFrame(NettyConstants.BIND_USER + "success"));
        PrintUtil.log("用户登录成功 userId : {}", userId);
    }


    /**
     * 检查访问权限
     *
     * @param ctx ctx
     * @return
     */
    public static String checkAuth(ChannelHandlerContext ctx) {
        Channel client = ctx.channel();
        Integer hashCode = client.hashCode();
        String userId = CHANNEL_USER.get(hashCode);
        if (StringUtil.isBlank(userId)) {
            PrintUtil.log("用户未登录");
            client.writeAndFlush(new TextWebSocketFrame("用户未登录"));
            ctx.close();
            return null;
        }
        return ACCOUNTS.get(userId);
    }

    /**
     * 清理用户信息
     *
     * @param ctx
     */
    public static void clear(ChannelHandlerContext ctx) {
        Channel client = ctx.channel();
        Integer hashCode = client.hashCode();
        String userId = CHANNEL_USER.remove(hashCode);
        if (StringUtil.isNotBlank(userId)) {
            USER_CHANNEL.remove(userId);
            PrintUtil.log("清理用户信息完毕 userId : {}", userId);
        }
    }
}