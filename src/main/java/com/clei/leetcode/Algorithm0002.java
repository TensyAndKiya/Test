package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 两数相加
 *
 * 给出两个非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author KIyA
 */
public class Algorithm0002 {

    public static void main(String[] args) {

        Node a = new Node(1);
        Node b = new Node(9);
        Node c = new Node(9);
        Node d = new Node(9);
        Node e = new Node(9);
        Node f = new Node(9);
        Node g = new Node(9);
        Node h = new Node(9);
        Node i = new Node(9);
        Node j = new Node(9);


        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
        f.next = g;
        /*g.next = h;
        h.next = i;
        i.next = j;*/

        PrintUtil.log(getNodeVal(a));

        PrintUtil.log(getNodeVal(new Node(9)));

        Node result = getNode(a,new Node(9));

        PrintUtil.log(getNodeVal(result));

    }

    public static Node getNode(Node l1, Node l2) {

        return getNode(l1,l2,false);

    }

    public static Node getNode(Node a,Node b,boolean add) {
        if (null == a) {
            if (add) {
                return getNode(new Node(1), b,false);
            } else {
                return b;
            }
        }

        if (null == b) {
            if (add) {
                return getNode(a, new Node(1),false);
            } else {
                return a;
            }
        }

        int sum = a.val + b.val;

        if (add) {
            sum += 1;
        }

        boolean addd = sum > 9;

        if (addd) {
            sum -= 10;
        }

        Node node = new Node(sum);

        node.next = getNode(a.next, b.next, addd);

        return node;
    }


    /**
     * 求 node 的 数值 (int 可能 范围不够 -(2^32) ~ 2^32 - 1)
     * @param a
     * @return
     */
    public static int getNodeVal(Node a){
        if(null == a.next){
            return a.val;
        }

        return a.val + 10 * getNodeVal(a.next);
    }

    /**
     * 静态内部类
     */
    static class Node{
        int val;

        Node next;

        Node(int val){
            this.val = val;
        }
    }
}


