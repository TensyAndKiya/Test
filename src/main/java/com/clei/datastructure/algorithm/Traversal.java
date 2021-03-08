package com.clei.datastructure.algorithm;

import com.clei.datastructure.dt.TreeNode;
import com.clei.utils.PrintUtil;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树遍历
 *
 * @author KIyA
 */
public class Traversal {

    public static void main(String[] args) {
        TreeNode<Integer> tree4 = new TreeNode<>(4);
        TreeNode<Integer> tree5 = new TreeNode<>(5);
        TreeNode<Integer> tree6 = new TreeNode<>(6);
        TreeNode<Integer> tree7 = new TreeNode<>(7);
        TreeNode<Integer> tree2 = new TreeNode<>(2, tree4, tree5);
        TreeNode<Integer> tree3 = new TreeNode<>(3, tree6, tree7);
        TreeNode<Integer> tree1 = new TreeNode<>(1, tree2, tree3);

        preorder(tree1);
        PrintUtil.separatorLine(true);
        inorder(tree1);
        PrintUtil.separatorLine(true);
        postorder(tree1);
        PrintUtil.separatorLine(true);
        level(tree1);
    }

    /**
     * 前序遍历
     *
     * @param tree
     */
    private static void preorder(TreeNode<Integer> tree) {
        if (null != tree.getElement()) {
            PrintUtil.print(tree.getElement());
        }
        if (null != tree.getLeft()) {
            preorder(tree.getLeft());
        }
        if (null != tree.getRight()) {
            preorder(tree.getRight());
        }
    }

    /**
     * 中序遍历
     *
     * @param tree
     */
    private static void inorder(TreeNode<Integer> tree) {
        if (null != tree.getLeft()) {
            inorder(tree.getLeft());
        }
        if (null != tree.getElement()) {
            PrintUtil.print(tree.getElement());
        }
        if (null != tree.getRight()) {
            inorder(tree.getRight());
        }
    }

    /**
     * 后序遍历
     *
     * @param tree
     */
    private static void postorder(TreeNode<Integer> tree) {
        if (null != tree.getLeft()) {
            postorder(tree.getLeft());
        }
        if (null != tree.getRight()) {
            postorder(tree.getRight());
        }
        if (null != tree.getElement()) {
            PrintUtil.print(tree.getElement());
        }
    }

    /**
     * 层序遍历
     *
     * @param tree
     */
    private static void level(TreeNode<Integer> tree) {
        if (null == tree) {
            return;
        }
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(tree);
        while (!queue.isEmpty()) {
            TreeNode<Integer> node = queue.poll();
            PrintUtil.print(node.getElement());
            if (null != node.getLeft()) {
                queue.offer(node.getLeft());
            }
            if (null != node.getRight()) {
                queue.offer(node.getRight());
            }
        }
    }
}