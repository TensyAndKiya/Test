package com.clei.netty.client;

import com.clei.utils.PrintUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * chat client handler
 *
 * @author KIyA
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        PrintUtil.log("channelRead0");
        // 输出消息
        PrintUtil.dateLine(msg);
    }
}