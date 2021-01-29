package com.clei.Y2020.M07.D23;

import com.clei.utils.PrintUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @backStory 好久没写socket程序了，试一哈
 * @author KIyA
 */
public class TCPSocketServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8001);

        while (true){

            PrintUtil.log("begin");

            Socket socket = serverSocket.accept();

            byte[] buffer = new byte[1024];

            while (true){

                if(socket.isClosed() || !socket.isConnected() || socket.isInputShutdown() || socket.isOutputShutdown()){
                    break;
                }

                InputStream is = socket.getInputStream();

                OutputStream os = socket.getOutputStream();
                // 读取
                int length = is.read(buffer);

                PrintUtil.log(length);

                if(-1 == length){

                    os.close();

                    is.close();

                    break;
                }

                String content = new String(buffer,0,length,"UTF-8");

                PrintUtil.log(content);

                // 写入
                os.write("侠客行".getBytes("UTF-8"));

                if("结束".equals(content)){

                    os.close();

                    is.close();

                    break;
                }

            }

            socket.close();

        }

    }

}
