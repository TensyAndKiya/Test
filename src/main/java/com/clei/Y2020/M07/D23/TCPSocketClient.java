package com.clei.Y2020.M07.D23;

import com.clei.utils.PrintUtil;
import com.clei.utils.SystemUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 好久没写socket程序了，试一哈
 *
 * @author KIyA
 */
public class TCPSocketClient {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 8001);

        // 写入
        OutputStream os = socket.getOutputStream();

        os.write("十步杀一人".getBytes(StandardCharsets.UTF_8));

        // 读取
        byte[] buffer = new byte[1024];

        InputStream is = socket.getInputStream();

        int length = is.read(buffer);

        PrintUtil.log(length);

        String content = new String(buffer, 0, length, StandardCharsets.UTF_8);

        PrintUtil.log(content);

        // 再写入
        os = socket.getOutputStream();
        os.write("千里不留行".getBytes(StandardCharsets.UTF_8));

        // 暂停
        SystemUtil.pause();

        is.close();

        os.close();

        socket.close();
    }
}
