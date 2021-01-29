package com.clei.Y2020.M07.D14;

import com.clei.utils.PrintUtil;
import com.clei.utils.StringUtil;

public class IPTest {

    public static void main(String[] args) {

        String ip = "192.168.1.22";

        PrintUtil.log(isLimitIp(ip));

    }

    private static boolean isLimitIp(String ip) {

        try{

            String limitIp = "192.168.1.2;192.168.1.1/18;192.168.1.22";

            int dotIndex = ip.lastIndexOf(".");
            // ip前缀
            String prefix = ip.substring(0,dotIndex + 1);

            if(StringUtil.isEmpty(limitIp)){
                return false;
            }

            while (true){

                int index = limitIp.indexOf(prefix);

                // 不包含这个前缀
                if(-1 == index){
                    return false;
                }

                String newIp = limitIp.substring(index);

                String str = newIp.substring(prefix.length());

                index = str.indexOf(";");

                // newIp就是最后一个ip或ip分段
                if(-1 == index){

                }else {
                    // 有分号
                    newIp = prefix + str.substring(0,index);
                }

                int slashIndex = newIp.indexOf("/");

                // 是单个ip
                if(-1 == slashIndex){

                    if(ip.equals(newIp)){
                        return true;
                    }else {
                        // 最后一个ip
                        if(-1 == index){
                            return false;
                        }
                    }

                } else {

                    // 最后的数字
                    int number = Integer.parseInt(ip.substring(ip.lastIndexOf(".") + 1));

                    dotIndex = newIp.lastIndexOf(".");

                    int startNum = Integer.parseInt(newIp.substring(dotIndex + 1,slashIndex));

                    int endNum = Integer.parseInt(newIp.substring(slashIndex + 1));

                    if(number >= startNum && number <= endNum){

                        return true;

                    }

                }

                limitIp = limitIp.replace(newIp + ";","");

            }

        }catch (Exception e){
            return false;
        }

    }

}
