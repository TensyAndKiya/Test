package com.clei.datastructure;

/**
 * 栈
 *
 * @author KIyA
 * @date 2021-07-22
 */
public class Stack<T> {

    /**
     * 数据
     */
    private final Object[] data;

    /**
     * 当前指针位置
     */
    private int index;

    /**
     * 构造函数
     *
     * @param capacity 栈容量
     */
    public Stack(int capacity) {
        if (capacity < 0) {
            throw new RuntimeException("容量比如大于或等于0");
        }
        this.data = new Object[capacity];
        this.index = -1;
    }

    /**
     * 入栈
     *
     * @param t 元素
     * @return 入栈是否成功
     */
    public boolean push(T t) {
        if (index == data.length - 1) {
            return false;
        }
        data[++index] = t;
        return true;
    }

    /**
     * 出栈
     *
     * @return 出栈元素
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (-1 == index) {
            throw new RuntimeException("栈已空");
        }
        return (T) data[index--];
    }
}
