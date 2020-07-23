package com.clei.Y2020.M07.D23;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @backStory 好久没写socket程序了，试一哈
 * @author KIyA
 */
public class TCPSocketClient {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1",8001);

        // 写入
        OutputStream os = socket.getOutputStream();

        os.write("十步杀一人".getBytes("UTF-8"));

        // 读取
        byte[] buffer = new byte[1024];

        InputStream is = socket.getInputStream();

        int length = is.read(buffer);

        System.out.println(length);

        String content = new String(buffer,0,length,"UTF-8");

        System.out.println(content);

        // 再写入
        os = socket.getOutputStream();
        os.write("千里不留行".getBytes("UTF-8"));

        is.close();

        os.close();

        socket.close();

    }

}
