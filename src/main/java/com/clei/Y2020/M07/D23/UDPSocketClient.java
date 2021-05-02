package com.clei.Y2020.M07.D23;

import com.clei.utils.PrintUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPSocketClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();

        InetAddress address = InetAddress.getByName("127.0.0.1");

        int port = 8001;

        // 发送
        byte[] content = "天地有正气".getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet = new DatagramPacket(content,content.length,address,port);

        socket.send(packet);

        // 再发送
        content = "杂然赋流形".getBytes(StandardCharsets.UTF_8);

        packet = new DatagramPacket(content,content.length,address,port);

        socket.send(packet);

        // 接受
        byte[] buffer = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(buffer,buffer.length);

        socket.receive(receivePacket);

        byte[] data = receivePacket.getData();

        String response = new String(data, StandardCharsets.UTF_8);

        PrintUtil.log(response);
    }
}
