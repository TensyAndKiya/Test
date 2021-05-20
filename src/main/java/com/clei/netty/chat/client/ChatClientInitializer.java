package com.clei.netty.chat.client;

import com.clei.utils.PrintUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * chat client initializer
 *
 * @author KIyA
 */
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 用户id
     */
    private String userId;

    public ChatClientInitializer(String userId) {
        this.userId = userId;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        PrintUtil.log("initChannel");
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new ChatClientHandler(userId));
    }
}
