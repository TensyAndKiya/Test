package com.clei.netty.websocket;

import com.clei.utils.PrintUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * websocket server handler
 *
 * @author KIyA
 */
public class WebsocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 有新连接加入
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        PrintUtil.log("handlerAdded");
        Channel client = ctx.channel();
        // 广播给所有channel
        channelGroup.writeAndFlush("[SERVER] - " + client.remoteAddress() + " 加入\n");
        // 添加到组内
        channelGroup.add(client);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        PrintUtil.log("handlerRemoved");
        Channel client = ctx.channel();
        // 广播给所有channel
        channelGroup.writeAndFlush("[SERVER] - " + client.remoteAddress() + " 离开\n");
        // 关闭的channel会自动从channelGroup remove
        // 无需调用channelGroup.remove(client)
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.log("channelActive");
        PrintUtil.log("[" + ctx.channel().remoteAddress() + "] 上线");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.log("channelInactive");
        PrintUtil.log("[" + ctx.channel().remoteAddress() + "] 掉线");
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        PrintUtil.log("channelRead0");
        Channel client = ctx.channel();
        String socketAddress = client.remoteAddress().toString();
        String msg = frame.text();
        PrintUtil.log("[{}] - msg - [{}]", socketAddress, msg);
        // 给所有人发消息
        for (Channel channel : channelGroup) {
            /*if (client != channel) {
                channel.writeAndFlush("[" + socketAddress + "] " + msg);
            } else {
                channel.writeAndFlush("[俺] " + msg);
            }*/
            // channel.writeAndFlush(socketAddress + "]|[" + msg);
            channel.writeAndFlush(new TextWebSocketFrame(msg));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PrintUtil.log("channelRead");
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        PrintUtil.log("[{}]异常关闭", ctx.channel().remoteAddress(), cause);
        // 遇到异常就关闭连接
        ctx.close();
    }
}
