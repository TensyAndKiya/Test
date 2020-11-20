package com.clei.Y2019.M04.D02;

import com.clei.utils.PrintUtil;

public class ExceptionPositionTest {
    public static void main(String[] args){
        new MyTest().myFunction();
    }
}

class MyTest{
    public void myFunction(){
        int[] arr = {1, 2};
        if(arr.length > 1){
            PrintUtil.dateLine(arr[0]);
        }
    }
}
