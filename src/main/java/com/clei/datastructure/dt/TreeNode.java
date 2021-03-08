package com.clei.datastructure.dt;

/**
 * 二叉树节点
 *
 * @author KIyA
 */
public class TreeNode<T> {

    /**
     * 元素值
     */
    private T element;

    /**
     * 左儿子
     */
    private TreeNode<T> left;

    /**
     * 右儿子
     */
    private TreeNode<T> right;

    public TreeNode() {
    }

    public TreeNode(T element) {
        this.element = element;
    }

    public TreeNode(T element, TreeNode<T> left, TreeNode<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }
}
