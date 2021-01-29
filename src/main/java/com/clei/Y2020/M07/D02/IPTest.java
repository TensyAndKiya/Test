package com.clei.Y2020.M07.D02;

import com.clei.utils.PrintUtil;

public class IPTest {
    public static void main(String[] args) {

        PrintUtil.log(validateIP("255.255.255.256"));

    }

    /**
     * IP校验 ipv4
     * @param ip
     */
    public static boolean validateIP(String ip){
        try {

            String[] arr = ip.split("\\.");

            if(4 != arr.length){
                return false;
            }

            for (String s : arr){
                int i = Integer.parseInt(s);

                if(i < 0 || i > 255){
                    return false;
                }
            }

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
