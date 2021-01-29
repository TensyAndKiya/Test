package com.clei.Y2019.M08.D06;

import com.clei.utils.PrintUtil;

public class StrEqualsTest {
    public static void main(String[] args) {
        String str1 = "<REQUEST_FPXXXZ_NEW class=\"REQUEST_FPXXXZ_NEW\"><FPQQLSH>111MFWIK201908070927497M0nqA0luCk7G17V9zR4</FPQQLSH><DSPTBM>111MFWIK</DSPTBM><NSRSBH>310101000000090</NSRSBH><DDH>20190807092749M37eZ0</DDH><PDF_XZFS>2</PDF_XZFS></REQUEST_FPXXXZ_NEW>";
        String str2 = "<REQUEST_FPXXXZ_NEW class=\"REQUEST_FPXXXZ_NEW\"><FPQQLSH>111MFWIK20190807100253qdFE6BW9fqC3WUDq54kF</FPQQLSH><DSPTBM>111MFWIK</DSPTBM><NSRSBH>310101000000090</NSRSBH><DDH>20190807100253Mp6LJe</DDH><PDF_XZFS>2</PDF_XZFS></REQUEST_FPXXXZ_NEW>";
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();

        for (int i = 0; i < str2.length(); i++) {
            if(arr1[i] != arr2[i]){
                PrintUtil.log("index: " + i);
                PrintUtil.log(str1.substring(i));
                PrintUtil.log(str2.substring(i));
                break;
            }
        }
    }
}
