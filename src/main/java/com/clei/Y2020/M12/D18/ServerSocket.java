package com.clei.Y2020.M12.D18;

import com.clei.utils.PrintUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO ServerSocket
 *
 * @author KIyA
 */
public class ServerSocket {

    /**
     * 缓冲区大小
     */
    private final static int BUF_SIZE = 1024;

    /**
     * 监听端口
     */
    private final static int PORT = 8888;

    /**
     * select等待时间
     */
    private final static int TIMEOUT = 8888;


    public static void main(String[] args) {
        selector();
    }

    /**
     * 处理连接
     *
     * @param key
     */
    public static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        // 非阻塞
        clientChannel.configureBlocking(false);
        // 注册 读
        clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
    }

    /**
     * 处理读
     *
     * @param key
     */
    public static void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        // attachment Buffer
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        int read = clientChannel.read(buffer);
        while (read > 0) {
            buffer.flip();
            /*while (buffer.hasRemaining()){
                PrintUtil.log(buffer.get());
            }*/
            String str = new String(buffer.array(), 0, read);
            PrintUtil.log(str);
            buffer.compact();
            // 再读
            read = clientChannel.read(buffer);
        }
        if (-1 == read) {
            clientChannel.close();
        }
    }

    /**
     * 处理写
     *
     * @param key
     */
    public static void handleWrite(SelectionKey key) throws IOException {
        // attachment Buffer
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        SocketChannel clientChannel = (SocketChannel) key.channel();
        while (buffer.hasRemaining()) {
            clientChannel.write(buffer);
        }
        buffer.compact();
    }

    /**
     * selector
     */
    public static void selector() {
        Selector selector = null;
        ServerSocketChannel serverChannel = null;
        try {
            // Selector 和 ServerSocketChannel开启
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            // server地址端口
            serverChannel.socket().bind(new InetSocketAddress("127.0.0.1", PORT));
            serverChannel.configureBlocking(false);
            // 非阻塞
            serverChannel.configureBlocking(false);
            // 注册到selector
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (0 == selector.select(TIMEOUT)) {
                    PrintUtil.log("==");
                    continue;
                }
                // 哪些就绪了
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }
                    if (key.isWritable()) {
                        handleWrite(key);
                    }
                    if (key.isConnectable()) {
                        PrintUtil.log("connectable");
                    }
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            PrintUtil.log("server出错", e);
        } finally {
            try {
                if (null != selector) {
                    selector.close();
                }
            } catch (Exception e) {
                PrintUtil.log("关闭selector出错", e);
            }
            try {
                if (null != serverChannel) {
                    serverChannel.close();
                }
            } catch (Exception e) {
                PrintUtil.log("关闭serverChannel出错", e);
            }
        }
    }

}
