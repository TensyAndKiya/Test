package com.clei.function;

/**
 * 消费三种类型参数的消费者
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @author KIyA
 */
@FunctionalInterface
public interface Consumer3<A, B, C> {

    /**
     * 消费三种类型参数
     * 无返回值
     *
     * @param a
     * @param b
     * @param c
     */
    void accept(A a, B b, C c);
}
