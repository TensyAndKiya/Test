package com.clei.Y2021.M05;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * git不准上传超过100M的文件
 * 懒得试其它命令了，切成小文件就行了
 *
 * @author KIyA
 */
public class CutBigFileTest {

    private final static String INPUT_FILE = "E:\\IdeaWorkspace\\south-wind\\temp\\download.zip";

    private final static String OUTPUT_FILE_PATH = "E:\\IdeaWorkspace\\south-wind\\output";

    private final static String OUTPUT_FILE_NAME = "out";

    private final static int READ_TIMES_LIMIT = 88;

    private final static int FILE_SIZE_LIMIT = READ_TIMES_LIMIT * 1024 * 1024;

    public static void main(String[] args) throws Exception {
        // cut();
        merge();
    }

    private static void cut() throws Exception {
        byte[] buffer = new byte[1024 * 1024];
        File inputFile = new File(INPUT_FILE);
        try (FileInputStream fis = new FileInputStream(inputFile);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int times = 0;
            int fileIndex = 0;
            int length;


            File outputFile = new File(OUTPUT_FILE_PATH + File.separator + OUTPUT_FILE_NAME + fileIndex);
            outputFile.getParentFile().mkdirs();
            outputFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(outputFile, true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while (-1 != (length = bis.read(buffer))) {
                times++;

                if (times == READ_TIMES_LIMIT) {
                    times = 1;
                    fileIndex++;

                    bos.flush();
                    bos.close();
                    fos.close();

                    outputFile = new File(OUTPUT_FILE_PATH + File.separator + OUTPUT_FILE_NAME + fileIndex);
                    outputFile.createNewFile();
                    fos = new FileOutputStream(outputFile);
                    bos = new BufferedOutputStream(fos);
                }

                bos.write(buffer, 0, length);
            }

            bos.flush();
            bos.close();
            fos.close();
        }
    }

    private static void merge() throws Exception {
        File inputFile = new File(OUTPUT_FILE_PATH);
        File[] files = inputFile.listFiles();

        File outputFile = new File(OUTPUT_FILE_PATH + File.separator + OUTPUT_FILE_NAME + "_final");
        outputFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(outputFile, true);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] buffer = new byte[1024 * 1024];
        for (File file : files) {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {

                int length;
                while (-1 != (length = bis.read(buffer))) {
                    bos.write(buffer, 0, length);

                }
            }
        }

        bos.flush();
        bos.close();
        fos.close();
    }
}
