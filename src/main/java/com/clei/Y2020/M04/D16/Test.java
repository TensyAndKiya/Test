package com.clei.Y2020.M04.D16;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 连续字符的开始结束索引
 *
 * @author KIyA
 * @date 2019-04-16
 */
public class Test {

    public static void main(String[] args)throws Exception {

        List<Integer> result = new LinkedList<>();
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] c = s.toCharArray();
        StringBuffer sb = new StringBuffer();


        // 上个重复字符的连续初始index
        int last = 0;

        for(int i = 0; i<c.length; i++){

            if(i > last){

                if(c[last] == c[i]){

                    // 下一个字符还相等 不操作。
                    if(i < c.length - 1 && c[last] == c[i + 1]){

                    }else {

                        result.add(last);
                        result.add(i);
                    }

                }else {
                    last = i;
                }

            }

        }

        System.out.println("result: " + result);

    }

}
