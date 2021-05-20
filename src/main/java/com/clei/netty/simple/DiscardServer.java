package com.clei.netty.simple;

import com.clei.consts.NettyConstants;
import com.clei.utils.PrintUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * discard server
 *
 * @author KIyA
 */
public class DiscardServer {

    public static void main(String[] args) {
        PrintUtil.log("Discard Server 启动");
        // 启动
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            PrintUtil.log("initChannel");
                            // handler处理
                            ch.pipeline().addLast("handler", new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
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
