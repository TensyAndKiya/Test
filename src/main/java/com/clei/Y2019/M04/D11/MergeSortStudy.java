package com.clei.Y2019.M04.D11;

import com.clei.utils.PrintUtil;

import java.util.Random;

public class MergeSortStudy {
    public static void main(String[] args){

        Random rand = new Random();
        int[] arr = rand.ints(0,100).limit(20).toArray();

        for (int i : arr) {
            PrintUtil.date(i + "\t");
        }
        mergeSort(arr, 0, arr.length - 1);
        PrintUtil.dateLine();
        for (int i : arr) {
            PrintUtil.date(i + "\t");
        }
    }

    public static void mergeSort(int[] arr, int start, int end){
        if(start < end){
            int mid = (start + end)/2;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
            merge(arr, start, mid, end);
        }
    }

    public static void merge(int[] arr,int start, int mid, int end){
        int[] temp = new int[arr.length];
        int p1 = start, p2 = mid +1, k=start;

        while(p1 <=mid && p2<=end){
            if(arr[p1] < arr[p2]){
                temp[k] = arr[p1];
                p1++;
                k++;
            }else{
                temp[k] = arr[p2];
                p2++;
                k++;
            }
        }

        while(p1 <= mid){
            temp[k] = arr[p1];
            k++;
            p1++;
        }

        while(p2 <= end){
            temp[k] = arr[p2];
            k++;
            p2++;
        }

        for (int i = start; i < end +1 ; i++) {
            arr[i] = temp[i];
        }
    }
}
