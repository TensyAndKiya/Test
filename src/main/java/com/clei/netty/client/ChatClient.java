package com.clei.netty.client;

import com.clei.utils.PrintUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * chat server
 *
 * @author KIyA
 */
public class ChatClient {

    public static void main(String[] args) throws InterruptedException {
        PrintUtil.log("Chat Client 启动");
        // 启动
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());
            // 连接
            Channel channel = bootstrap.connect("127.0.0.1", 8888).sync().channel();
            // 输入并发送
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(in.readLine() + "\n");
            }
        } catch (Exception e) {
            PrintUtil.log(e);
        } finally {
            bossGroup.shutdownGracefully();
        }
    }
}
