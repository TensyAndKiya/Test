package com.clei.Y2019.M09.D07;

public class FinallyTest {
    public static void main(String[] args) {
        int i = getNum();
        System.out.println(i);
    }

    private static int getNum(){
        int i = 0;
        try{
            i = 3 / 2;
            return i;
        }catch (Exception e){
            i = 2;
            e.printStackTrace();
        }finally {
            return 3;
        }
    }
}
