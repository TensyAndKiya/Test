package com.clei.datastructure;

/**
 * AVL 平衡二叉查找树
 *
 * @author KIyA
 */
public class AvlTree<T extends Comparable<? super T>> {

    /**
     * 树根节点
     */
    private AvlTreeNode<T> root;

    /**
     * 树节点数
     */
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return 0 == size;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(AvlTreeNode<T> node) {
        if (null == node) {
            return true;
        }
        int diff = Math.abs(getHeight(node.getLeft()) - getHeight(node.getRight()));
        if (diff > 1) {
            return false;
        }
        return isBalanced(node.getLeft()) && isBalanced(node.getRight());
    }

    private int getHeight(AvlTreeNode<T> node) {
        return null == node ? 0 : node.getHeight();
    }
}
