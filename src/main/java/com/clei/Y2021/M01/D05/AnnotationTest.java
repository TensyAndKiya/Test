package com.clei.Y2021.M01.D05;

import com.clei.annotation.Test1;
import com.clei.annotation.Test2;
import com.clei.annotation.Test3;
import com.clei.function.Consumer2;
import com.clei.utils.PrintUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * 测试注解测试类
 *
 * @author KIyA
 */
public class AnnotationTest {

    public static void main(String[] args) throws Exception {
        Consumer<Method> noExConsumer1 = m -> PrintUtil.log(m.getName() + "通过");
        Consumer<Method> noExConsumer2 = m -> PrintUtil.log(m.getName() + "未通过");
        Consumer2<Method, Throwable> exNoPassConsumer = (m, t) -> PrintUtil.log(m.getName() + "未通过", t);
        // 抛出异常是指定异常
        Consumer2<Method, Throwable> specialExConsumer1 = (m, t) -> {
            // 抛出指定异常才通过
            Class<? extends Throwable> exClass = m.getAnnotation(Test2.class).value();
            if (exClass.isInstance(t)) {
                noExConsumer1.accept(m);
            } else {
                exNoPassConsumer.accept(m, t);
            }
        };
        // 抛出异常在限定范围内
        Consumer2<Method, Throwable> specialExConsumer2 = (m, t) -> {
            // 抛出指定异常才通过
            Class<? extends Throwable>[] exClass = m.getAnnotation(Test3.class).value();
            boolean pass = false;
            for (Class<? extends Throwable> clazz : exClass) {
                if (clazz.isInstance(t)) {
                    pass = true;
                    break;
                }
            }
            if (pass) {
                noExConsumer1.accept(m);
            } else {
                exNoPassConsumer.accept(m, t);
            }
        };
        // 之所以写上述这些不同的consumer是因为之前写了三个一样格式的doTest123方法
        // 重复代码较多，所以用consumer减少代码量，这样也保证了相同操作的一致性
        // 三个不同的测试注解
        doTest(Test1.class, noExConsumer1, exNoPassConsumer);
        PrintUtil.separatorLine();
        doTest(Test2.class, noExConsumer2, specialExConsumer1);
        PrintUtil.separatorLine();
        doTest(Test3.class, noExConsumer2, specialExConsumer2);
    }

    /**
     * 正常执行则通过
     *
     * @throws Exception 异常
     */
    private static void doTest(Class<? extends Annotation> clazz, Consumer<Method> noExConsumer, Consumer2<Method, Throwable> exConsumer) throws Exception {
        // 被测试的类
        Class<?> testClass = Class.forName("com.clei.Y2021.M01.D05.AnnotationTest");
        // 其所有公共方法
        Method[] methods = testClass.getDeclaredMethods();
        // 有注解的方法数
        int count = 0;
        // 迭代
        for (Method m : methods) {
            // 若方法是指定注解注解的方法
            if (m.isAnnotationPresent(clazz)) {
                count++;
                try {
                    m.invoke(null);
                    noExConsumer.accept(m);
                } catch (InvocationTargetException ite) {
                    Throwable t = ite.getCause();
                    exConsumer.accept(m, t);
                } catch (Exception e) {
                    PrintUtil.log(m.getName() + "无法使用注解" + clazz.getName());
                }
            }
        }
        PrintUtil.log("测试方法数：{}", count);
    }

    @Test1
    @Test2(IndexOutOfBoundsException.class)
    @Test3({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void m1() {

    }

    @Test1
    @Test2(IndexOutOfBoundsException.class)
    @Test3({IndexOutOfBoundsException.class, NullPointerException.class})
    public void m2() {

    }

    @Test1
    @Test2(IndexOutOfBoundsException.class)
    @Test3({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void m3() {
        throw new RuntimeException("未知错误");
    }

    @Test1
    @Test2(IndexOutOfBoundsException.class)
    @Test3({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void m4() {
        int[] arr = new int[]{0};
        int i = Integer.parseInt("1");
        PrintUtil.log(arr[i]);
    }

    @Test1
    @Test2(IndexOutOfBoundsException.class)
    @Test3({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void m5() {
        // 为了绕过always null的提示，用了一个方法生产null
        String str1 = m6();
        PrintUtil.log(str1);
    }

    public static String m6() {
        return null;
    }
}
