package com.clei.netty.simple;

import com.clei.utils.PrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * http server handler
 *
 * @author KIyA
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
        // headers
        HttpHeaders headers = msg.headers();
        List<Map.Entry<String, String>> entries = headers.entries();
        String headersStr = entries.stream()
                .map(e -> e.getKey() + '=' + e.getValue())
                .collect(Collectors.joining(","));
        PrintUtil.log("headers : [{}]", headersStr);
        // request
        PrintUtil.log("method : {}, uri : {}, version : {}", msg.method(), msg.uri(), msg.protocolVersion());
        // body
        PrintUtil.log("request body : {}", msg.content().toString(Charset.defaultCharset()));

        // response
        String uri = msg.uri();
        String res;
        switch (uri) {
            case "/":
                res = "<h1>index</h1>";
                break;
            case "/hello":
                res = "<h1>hello world</h1>";
                break;
            case "/test":
                res = "<h1>success</h1>";
                break;
            default:
                res = "<h1>404</h1>";
        }
        PrintUtil.log("response body : {}", res);
        ByteBuf data = Unpooled.copiedBuffer(res, Charset.defaultCharset());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, data);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, res.length());
        ctx.writeAndFlush(response);
    }
}