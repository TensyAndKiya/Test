package com.clei.netty.websocket;

import com.clei.consts.NettyConstants;
import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;
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
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        PrintUtil.log("handlerRemoved");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.log("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.log("channelInactive");
        super.channelInactive(ctx);

        // 清理用户信息
        SessionManager.clear(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        PrintUtil.log("channelRead0");

        Channel client = ctx.channel();
        String msg = frame.text();

        // 绑定用户关联关系
        int bindIndex = msg.indexOf(NettyConstants.BIND_USER);
        if (-1 != bindIndex) {
            SessionManager.bindUser(ctx, msg, bindIndex);
            return;
        }

        // 检查访问权限
        String userName = SessionManager.checkAuth(ctx);
        if (StringUtil.isBlank(userName)) {
            return;
        }

        String socketAddress = client.remoteAddress().toString();
        PrintUtil.log("[{}] - [{}] - msg - [{}]", socketAddress, userName, msg);
        // 给所有人发消息
        for (Channel channel : channelGroup) {
            /*if (client != channel) {
                channel.writeAndFlush("[" + socketAddress + "] " + msg);
            } else {
                channel.writeAndFlush("[俺] " + msg);
            }*/
            // channel.writeAndFlush(socketAddress + "]|[" + msg);
            channel.writeAndFlush(new TextWebSocketFrame("[" + userName + "]" + msg));
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
