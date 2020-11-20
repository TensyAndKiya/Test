package com.clei.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 下载工具类
 *
 * @author KIyA
 */
public class DownloadUtil {

    private final static int BUFFER = 1024;

    /**
     * 将远程文件下载到本地
     *
     * @param fileUrl
     * @param toFile
     */
    public static void downloadToLocal(String fileUrl, String toFile) throws Exception {
        FileOutputStream fos = new FileOutputStream(toFile);
        download(fileUrl, fos);
        fos.close();
    }

    /**
     * java指定文件url下载
     *
     * @param fileUrl
     * @param outputStream
     * @throws Exception
     */
    public static void download(String fileUrl, OutputStream outputStream) throws Exception {
        URL url = new URL(fileUrl);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
        transfer(readableByteChannel, writableByteChannel);
    }

    /**
     * 传输
     *
     * @param readChannel
     * @param writeChannel
     * @throws Exception
     */
    private static void transfer(ReadableByteChannel readChannel, WritableByteChannel writeChannel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER);
        while (readChannel.read(buffer) != -1) {
            //切换为读状态
            buffer.flip();
            //保证全部写入
            while (buffer.hasRemaining()) {
                writeChannel.write(buffer);
            }
            //清空缓冲区
            buffer.clear();
        }
        readChannel.close();
        writeChannel.close();
    }

}
