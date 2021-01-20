package com.clei.netty.server;

import com.clei.utils.PrintUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * chat server handler
 *
 * @author KIyA
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * channel group
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 有新连接加入
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.log("handlerAdded");
        Channel client = ctx.channel();
        // 广播给所有channel
        channelGroup.writeAndFlush("[SERVER] - " + client.remoteAddress() + " 加入\n");
        // 添加到组内
        channelGroup.add(client);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
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
        PrintUtil.dateLine("[" + ctx.channel().remoteAddress() + "] 在线");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.log("channelInactive");
        PrintUtil.dateLine("[" + ctx.channel().remoteAddress() + "] 掉线");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.log("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.log("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        PrintUtil.log("channelRead0");
        Channel client = ctx.channel();
        String socketAddress = client.remoteAddress().toString();
        PrintUtil.log("[{}] - msg - [{}]", socketAddress, msg);
        // 加个换行 delimiter好处理
        msg = msg + '\n';
        // 给所有人发消息
        for (Channel channel : channelGroup) {
            if (client != channel) {
                channel.writeAndFlush("[" + socketAddress + "] " + msg);
            } else {
                channel.writeAndFlush("[俺] " + msg);
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PrintUtil.log("channelRead");
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        PrintUtil.dateLine("[" + ctx.channel().remoteAddress() + "] 异常关闭");
        // 遇到异常就关闭连接
        ctx.close();
    }
}
