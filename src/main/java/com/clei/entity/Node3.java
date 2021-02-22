package com.clei.entity;

import java.util.Queue;
import java.util.Random;

/**
 * 最多三个子节点的节点
 *
 * @author KIyA
 */
public class Node3 {

    private int i;

    private String name;

    private Node3 left;

    private Node3 middle;

    private Node3 right;

    public static Node3 getTree(int height, Queue<Integer> queue, Random random) {
        if (height < 1) {
            return null;
        }
        int value = queue.remove();
        if (height == 1) {
            return new Node3(value, null, null, null);
        }
        if (null == random) {
            random = new Random();
        }
        height--;
        // 001 010 011 100 101 110 111
        int r = random.nextInt(7);
        switch (r) {
            case 0:
                return new Node3(value, null, null, getTree(height, queue, random));
            case 1:
                return new Node3(value, null, getTree(height, queue, random), null);
            case 2:
                return new Node3(value, null, getTree(height, queue, random), getTree(height, queue, random));
            case 3:
                return new Node3(value, getTree(height, queue, random), null, null);
            case 4:
                return new Node3(value, getTree(height, queue, random), null, getTree(height, queue, random));
            case 5:
                return new Node3(value, getTree(height, queue, random), getTree(height, queue, random), null);
            case 6:
                return new Node3(value, getTree(height, queue, random), getTree(height, queue, random), getTree(height, queue, random));
        }
        return null;
    }

    public Node3(int i, Node3 left, Node3 middle, Node3 right) {
        this.i = i;
        this.name = String.valueOf(i);
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(' ');
        if (null != left) {
            sb.append(left.toString());
        }
        if (null != middle) {
            sb.append(middle.toString());
        }
        if (null != right) {
            sb.append(right.toString());
        }
        return sb.toString();
    }

    public int getI() {
        return i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node3 getLeft() {
        return left;
    }

    public Node3 getMiddle() {
        return middle;
    }

    public Node3 getRight() {
        return right;
    }
}
