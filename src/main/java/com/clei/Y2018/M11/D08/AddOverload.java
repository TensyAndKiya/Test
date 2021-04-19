package com.clei.Y2018.M11.D08;

import com.clei.utils.PrintUtil;

/**
 * 加法重载
 *
 * @param <T>
 * @author KIyA
 */
public class AddOverload<T extends Number> {

    public static void main(String[] args) {
        AddOverload<Integer> aaa = new AddOverload<>();
        int result = (int) aaa.add(3, 4);
        PrintUtil.log(result);

        MultiplyAndAdd tt = Integer::sum;
        PrintUtil.log(tt.f2(3, 4));
    }

    public double add(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }
}

@FunctionalInterface
interface MultiplyAndAdd {

    default int f1(int x, int y) {
        return x * y;
    }

    int f2(int x, int y);
}
