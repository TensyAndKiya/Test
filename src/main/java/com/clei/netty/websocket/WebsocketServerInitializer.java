package com.clei.netty.websocket;

import com.clei.utils.PrintUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * websocket server initializer
 *
 * @author KIyA
 */
public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        PrintUtil.log("initChannel");
        ChannelPipeline pipeline = ch.pipeline();

        // IP限制
        IpSubnetFilterRule accept = new IpSubnetFilterRule("128.0.0.1", 24, IpFilterRuleType.ACCEPT);
        IpSubnetFilterRule reject = new IpSubnetFilterRule("127.0.0.1", 32, IpFilterRuleType.REJECT);
        pipeline.addLast(new RuleBasedIpFilter(accept, reject));

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new WebsocketServerHandler());
    }
}
