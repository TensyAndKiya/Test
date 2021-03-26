package com.clei.netty.client;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * chat client handler
 *
 * @author KIyA
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 用户id
     */
    private String userId;

    public ChatClientHandler(String userId) {
        this.userId = userId;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        // PrintUtil.log("channelRead0");
        // 输出消息
        if (!StringUtil.isBlank(msg)) {
            String separator = ChatClient.SEPARATOR;
            int index = msg.indexOf(separator);
            if (index > 0) {
                // 暂时只用了userId，以后可以考虑加上登录验证获取用户名等信息
                String msgUserId = msg.substring(0, index);
                if (userId.equals(msgUserId)) {
                    // 后面的空格是为了与userId长度对应使得后面消息对齐
                    msgUserId = "吾                               ";
                }
                PrintUtil.log("[{}] - {}", msgUserId, msg.substring(index + separator.length()));
            } else {
                PrintUtil.log(msg);
            }
        }
    }
}