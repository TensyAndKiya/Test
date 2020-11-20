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
public class DepthFirstSearch {

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
            int val = tree.getI();
            PrintUtil.print(val + " ");
            search(tree.getLeft());
            search(tree.getMiddle());
            search(tree.getRight());
        }
    }
}
