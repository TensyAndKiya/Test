package com.clei.Y2019.M05.D08;

import com.clei.utils.BigDecimalUtil;
import com.clei.utils.PrintUtil;

public class FloatTest {
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            float f  = (float) (100 * Math.random());
            // float f = i;
            float s = BigDecimalUtil.getScaleFloat(f,3);
            PrintUtil.println("f: {}, str: {}",f,s);
        }

        if(Math.random() > 0){
            return;
        }

        float a = 1987.01f;
        float b = 1987.01f;
        while(a > 200){
            a -= 200;
            PrintUtil.dateLine(200);
        }
        if(a>0){
            PrintUtil.dateLine(a);
        }
        String xxx = "asdfjalsdkfjalksdjflksad";
        if(xxx.length() > 8){
            PrintUtil.dateLine(xxx.substring(xxx.length() - 8));
        }
    }
}
