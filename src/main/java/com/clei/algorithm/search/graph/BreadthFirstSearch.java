package com.clei.algorithm.search.graph;

import com.clei.entity.Node3;
import com.clei.utils.ArrayUtil;
import com.clei.utils.NumUtil;
import com.clei.utils.PrintUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * 广度优先查找
 *
 * @author KIyA
 */
public class BreadthFirstSearch {

    public static void main(String[] args) {
        int height = 4;
        int nodeNum = 3;
        int[] arr = ArrayUtil.shuffleArray(NumUtil.getPowSum(nodeNum, height), 1, 50, false);
        LinkedList<Integer> queue = Arrays.stream(arr).boxed().collect(Collectors.toCollection(LinkedList::new));
        Node3 tree = Node3.getTree(height, queue, null);
        PrintUtil.println(tree);
        search(tree);
    }

    public static void search(Node3 tree) {
        if (null != tree) {
            LinkedList<Node3> queue = new LinkedList<>();
            queue.add(tree);
            while (!queue.isEmpty()) {
                Node3 node = queue.poll();
                int val = node.getI();
                PrintUtil.print(val + " ");
                if (null != node.getLeft()) {
                    queue.add(node.getLeft());
                }
                if (null != node.getMiddle()) {
                    queue.add(node.getMiddle());
                }
                if (null != node.getRight()) {
                    queue.add(node.getRight());
                }
            }
        }
    }
}
