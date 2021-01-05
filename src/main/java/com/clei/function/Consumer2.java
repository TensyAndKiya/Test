package com.clei.function;

/**
 * 消费两种类型参数的消费者
 *
 * @param <A>
 * @param <B>
 * @author KIyA
 */
@FunctionalInterface
public interface Consumer2<A, B> {

    /**
     * 消费两种类型参数
     * 无返回值
     *
     * @param a
     * @param b
     */
    void accept(A a, B b);
}
