package com.clei.consts;

/**
 * netty用到的一些常量
 *
 * @author KIyA
 */
public interface NettyConstants {

    /**
     * 服务端端口
     */
    int SERVER_PORT = 8888;

    /**
     * 字符串分隔符
     */
    String STR_SEPARATOR = "]|[";

    /**
     * websocket server uri
     */
    String WS_URI = "/ws";

    /**
     * websocket 绑定用户关系记号
     */
    String BIND_USER = "bindUser->";
}
