package com.clei.netty.simple;

import com.clei.utils.DateUtil;
import com.clei.utils.PrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Time server handler
 *
 * @author KIyA
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        PrintUtil.log("channelActive {}", ctx.channel().remoteAddress());
        String curDateTime = DateUtil.currentDateTime();
        byte[] data = curDateTime.getBytes();
        ByteBuf msg = ctx.alloc().buffer(data.length);
        msg.writeBytes(data);
        ChannelFuture future = ctx.writeAndFlush(msg);
        future.addListener(a -> {
            PrintUtil.log("消息发送完毕，关闭连接");
            ctx.close();
        });
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