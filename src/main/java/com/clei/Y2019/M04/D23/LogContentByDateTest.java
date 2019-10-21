package com.clei.Y2019.M04.D23;

import java.io.*;

public class LogContentByDateTest {
    private final static String LOG_DIRECTORY = "C:\\Users\\liudg\\Desktop\\logs\\2019-04-23 error";
    public static void main(String[] args) throws Exception {
//        Scanner input = new Scanner(System.in,"UTF-8");
//        System.out.println("请输入开始时间和结束时间：");
        //2019-04-22-15:3  从30分开始
        //2019-04-22-16  到 4点结束
        String startDate = "2019-04-11-16:31";
        String endDate = "2019-04-11-16:33";
        File logFile = new File(LOG_DIRECTORY + File.separator + "all_info_0411.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile),"UTF-8"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOG_DIRECTORY + File.separator + "all_info_cut_0411.log"),"UTF-8"));
        boolean start = false;
        String line;
        while( (line = br.readLine()) != null ){
            if(!start){
                if(line.contains(startDate)){
                    start = true;
                    bw.write(line);
                    bw.newLine();
                }
            }else {
                if(line.contains(endDate)){
                    break;
                }
                bw.write(line);
                bw.newLine();
            }
        }
        br.close();
        bw.close();
        System.out.println("筛选完毕");
    }
}
