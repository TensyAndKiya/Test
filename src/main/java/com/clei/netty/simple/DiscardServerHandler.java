package com.clei.netty.simple;

import com.clei.utils.PrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * discard server handler
 *
 * @author KIyA
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 消息处理
     *
     * @param ctx 上下文
     * @param msg 消息
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        PrintUtil.log("channelRead msg : {}", msg);
        // 丢弃消息
        ((ByteBuf) msg).release();
    }

    /**
     * 异常处理
     *
     * @param ctx   上下文
     * @param cause 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        PrintUtil.log("[{}]异常关闭", ctx.channel().remoteAddress(), cause);
        // 遇到异常就关闭连接
        ctx.close();
    }
}
