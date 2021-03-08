package com.clei.datastructure.dt;

/**
 * 二叉树节点高度
 *
 * @author KIyA
 */
public class AvlTreeNode<T> {

    /**
     * 元素值
     */
    private T element;

    /**
     * 左儿子
     */
    private AvlTreeNode<T> left;

    /**
     * 右儿子
     */
    private AvlTreeNode<T> right;

    /**
     * 节点高度
     */
    private int height;

    public AvlTreeNode() {
    }

    public AvlTreeNode(T element) {
        this.element = element;
        this.height = 1;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public AvlTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(AvlTreeNode<T> left) {
        this.left = left;
    }

    public AvlTreeNode<T> getRight() {
        return right;
    }

    public void setRight(AvlTreeNode<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
