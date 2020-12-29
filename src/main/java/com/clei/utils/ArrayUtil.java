package com.clei.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 数组相关工具类
 *
 * @author KIyA
 */
public class ArrayUtil {
    // 想法1
    // 1 从1到1000里随机选取100个

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();

        int[] array = intArray(0);
        int[] randomArray = randomArray(array, 0);

        long end = System.currentTimeMillis();
        PrintUtil.println("耗时 ： {}ms", end - begin);

        PrintUtil.dateLine("origin : " + Arrays.toString(array));
        PrintUtil.dateLine("result : " + Arrays.toString(randomArray));
        shuffle(randomArray);
        PrintUtil.dateLine("shuffle result : " + Arrays.toString(randomArray));

        PrintUtil.dateLine("size : " + randomArray.length);
    }

    // 从 0 到 n 里 选 num 个
    public static int[] randomArray(int[] array, int num) {
        int length = array.length;

        if (length == num) {
            return array;
        }

        if (length < num) {
            throw new RuntimeException("数组太短");
        }

        // 1000里选 900个 == 1000里选 100个
        boolean other = false;
        int[] originArray = array;
        if (num > length / 2) {
            other = true;
            num = length - num;
        }
        // 装选入的数字
        Set<Integer> set = new HashSet<>(num);

        // 筛选出的 set
        outer:
        while (set.size() < num) {
            // 还需要多少个数字
            int tempNum = num - set.size();
            // 长度为n倍tempNum 的 数组(n > 1)
            // 下面两个都是应对一次生成没有满足的状况
            // 使用tempNum而不是num保证 需要的数字越来越少的时候 生成的数组越来越小 浪费少
            // 使用array.length 而不是 length 保证 不会出现IndexOutOfBoundsException
            int[] randomIntArray = randomIntArray(tempNum * 3 / 2, 0, array.length);

            for (int i : randomIntArray) {
                set.add(array[i]);

                if (num == set.size()) {
                    break outer;
                }
            }
            array = getLastArray(array, set);
        }

        //
        if (other) {
            return getLastArray(originArray, set);
        }

        return set.stream().mapToInt(i -> i).toArray();
    }

    /**
     * 打乱数组顺序
     *
     * @param arr
     * @return
     */
    public static <T> void shuffle(T[] arr) {
        int length = arr.length;

        if (length < 2) {
            throw new RuntimeException("数组太短");
        }

        Random rand = new Random();

        int index = length;

        while (index > 0) {
            int r = rand.nextInt(index);
            swap(arr, r, --index);
        }
    }

    /**
     * 打乱数组顺序
     *
     * @param arr
     * @return
     */
    public static void shuffle(int[] arr) {
        int length = arr.length;

        if (length < 2) {
            throw new RuntimeException("数组太短");
        }

        Random rand = new Random();

        int index = length;

        while (index > 0) {
            int r = rand.nextInt(index);
            swap(arr, r, --index);
        }
    }

    /**
     * 获得一个长度为num 大小在origin 到 bound[左闭右开]之间的乱序int数组
     *
     * @param num
     * @param origin
     * @param bound
     * @param repeatable
     * @return
     */
    public static int[] shuffleArray(int num, int origin, int bound, boolean repeatable) {
        int[] arr = randomIntArray(num, origin, bound, repeatable);
        shuffle(arr);
        return arr;
    }

    /**
     * 获得一个长度为num 大小在origin 到 bound[左闭右开]之间的int数组
     *
     * @param num
     * @param origin
     * @param bound
     * @param repeatable
     * @return
     */
    public static int[] randomIntArray(int num, int origin, int bound, boolean repeatable) {
        if (repeatable) {
            return randomIntArray(num, origin, bound);
        } else {
            if (num < 0 || bound - origin <= num) {
                throw new RuntimeException("参数错误");
            }

            int[] arr = intArray(bound - origin, origin, bound);
            int[] result = randomArray(arr, num);
            return result;
        }
    }

    /**
     * 获得一个长度为i 的 从0到Integer.MAX_VALUE[左闭右开] 的 int数组
     *
     * @param i
     * @return
     */
    public static int[] intArray(int i) {
        return intArray(i, 0);
    }

    /**
     * 获得一个长度为i 的 from到Integer.MAX_VALUE[左闭右开] 的 int数组
     *
     * @param i
     * @return
     */
    public static int[] intArray(int i, int from) {
        return intArray(i, from, Integer.MAX_VALUE);
    }

    /**
     * 获得一个长度为i 的 from到to[左闭右开] 的 int数组
     *
     * @param i
     * @return
     */
    public static int[] intArray(int i, int from, int to) {
        if (i < 0 || to - from < i) {
            throw new RuntimeException("参数错误");
        }
        int[] array = new int[i];

        for (int j = 0; j < i; j++) {
            array[j] = j + from;
        }
        return array;
    }

    /**
     * 获得一个长度为i 的 从0到i[左闭右开] 的 Integer 列表
     *
     * @param i
     * @return
     */
    public static List<Integer> integerList(int i) {
        if (i < 1) {
            return null;
        }

        List<Integer> list = new ArrayList<>(i);

        for (int j = 0; j < i; j++) {
            list.add(j);
        }
        return list;
    }

    /**
     * 交换T数组两个数位置
     *
     * @param arr
     * @param i
     * @param j
     */
    public static <T> void swap(T[] arr, int i, int j) {
        T t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    /**
     * 交换int数组两个数位置
     *
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    /**
     * 交换char数组两个数位置
     *
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(char[] arr, int i, int j) {
        char t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    /**
     * 将数字num插入到数组的i位置
     *
     * @param arr
     * @param num
     * @param i
     * @param endIndex
     */
    public static void insert(int[] arr, int num, int i, int endIndex) {
        if (i < 0 || i > arr.length - 1 || endIndex <= i) {
            throw new RuntimeException("参数错误");
        }
        for (int j = endIndex; j > i + 1; j--) {
            arr[j - 1] = arr[j - 2];
        }
        arr[i] = num;
    }

    /**
     * 翻转
     *
     * @param arr
     * @return
     */
    public static void reverse(int[] arr) {
        int length = arr.length;
        int times = length / 2;
        for (int i = 0; i < times; i++) {
            swap(arr, i, length - i - 1);
        }
    }

    /**
     * char数组反转
     *
     * @param arr
     * @return
     */
    public static void reverse(char[] arr) {
        int length = arr.length;
        int times = length / 2;
        for (int i = 0; i < times; i++) {
            swap(arr, i, length - i - 1);
        }
    }

    /**
     * 多个int数组合并数组合并
     *
     * @param arr
     * @return
     */
    public static int[] merge(int[]... arr) {
        int length = 0;
        for (int[] a : arr) {
            length += a.length;
        }
        int[] result = new int[length];
        int from = 0;
        for (int[] a : arr) {
            int arrLength = a.length;
            System.arraycopy(a, 0, result, from, arrLength);
            from += arrLength;
        }
        return result;
    }

    /**
     * array 里 的值 不在 collection 里 的 凑一个array
     *
     * @param array
     * @param set
     * @return
     */
    private static int[] getLastArray(int[] array, Set<Integer> set) {
        int[] newArray = new int[array.length - set.size()];
        int j = 0;
        for (int i : array) {
            if (!set.contains(i)) {
                newArray[j] = i;
                j++;
            }
        }
        return newArray;
    }

    /**
     * 获得一个长度为num 大小在origin 到 bound[左闭右开]之间的int数组
     * 允许重复的
     *
     * @param num
     * @param origin
     * @param bound
     * @return
     */
    private static int[] randomIntArray(int num, int origin, int bound) {
        if (num < 0 || bound - origin <= num) {
            throw new RuntimeException("参数错误");
        }
        Random random = new Random();
        int[] array = random.ints(num, origin, bound).toArray();
        return array;
    }
}
