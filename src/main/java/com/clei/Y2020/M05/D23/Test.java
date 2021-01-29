package com.clei.Y2020.M05.D23;

import com.clei.utils.PrintUtil;

import java.util.Arrays;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String str = input.nextLine();

        int indexA = str.indexOf("}");

        int indexB = str.lastIndexOf("}");

        int indexR = str.lastIndexOf("=");

        String strA = str.substring(3,indexA);

        String strB = str.substring(indexA + 5,indexB);

        String strR = str.substring(indexR + 1);

        int[] arrA = Arrays.stream(strA.split(",")).mapToInt(s -> Integer.parseInt(s)).toArray();

        int[] arrB = Arrays.stream(strB.split(",")).mapToInt(s -> Integer.parseInt(s)).toArray();

        int R = Integer.parseInt(strR);

        for (int i = 0; i < arrA.length; i++) {

            for (int j = 0; j < arrB.length; j++) {

                if(arrA[i] > arrB[j]){

                    continue;

                }

                if(arrB[j] - arrA[i] <= R){

                    PrintUtil.log(arrA[i] + " " + arrB[j]);

                }

            }

        }
    }
}
