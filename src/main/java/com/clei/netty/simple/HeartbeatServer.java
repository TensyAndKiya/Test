package com.clei.netty.simple;

import com.clei.consts.NettyConstants;
import com.clei.utils.PrintUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * heartbeat server
 *
 * @author KIyA
 */
public class HeartbeatServer {

    public static void main(String[] args) {
        PrintUtil.log("Heartbeat Server 启动");
        // 启动
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            PrintUtil.log("initChannel");
                            ChannelPipeline pipeline = ch.pipeline();
                            // 读超时4 写超时5 所有超时7
                            pipeline.addLast(new IdleStateHandler(4, 5, 7));
                            pipeline.addLast(new HeartbeatServerHandler());
                        }
                    });
            // 绑定端口
            ChannelFuture future = bootstrap.bind(NettyConstants.SERVER_PORT).sync();
            // 该例中不会发生的 等待服务器 socket关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            PrintUtil.log(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
