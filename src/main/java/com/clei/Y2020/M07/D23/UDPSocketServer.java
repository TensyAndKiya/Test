package com.clei.Y2020.M07.D23;

import com.clei.utils.PrintUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPSocketServer {

    public static void main(String[] args) throws Exception {

        DatagramSocket serverSocket = new DatagramSocket(8001);

        while (true){

            PrintUtil.log("begin");

            byte[] buffer = new byte[1024];

            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);

            // 接受
            serverSocket.receive(packet);

            byte[] data = packet.getData();

            String content = new String(data,"UTF-8");

            PrintUtil.log(content);

            // 响应
            byte[] response = "何不语".getBytes("UTF-8");

            PrintUtil.log("address : " + packet.getAddress() + " port : " + packet.getPort());

            DatagramPacket packetToClient = new DatagramPacket(response,response.length,packet.getAddress(),packet.getPort());

            serverSocket.send(packetToClient);

        }

    }

}
