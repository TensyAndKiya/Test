package com.clei.algorithm.other;

import com.clei.entity.GraphLine;
import com.clei.entity.GraphNode;
import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 迪杰斯特拉算法
 * 图中求解最短路径
 * 不能计算有环路径和负权重路径的
 *
 * @author KIyA
 */
public class Dijkstra {

    public static void main(String[] args) {
        List<GraphNode> nodeList = initGraphNode();
        firstToLast(nodeList);
    }

    /**
     * 计算第一个点到最后一个点的权重
     *
     * @param nodeList
     */
    private static void firstToLast(List<GraphNode> nodeList) {
        // 第一个点到其它点的花费
        Map<GraphNode, Integer> costMap = new HashMap<>(nodeList.size() - 1);
        GraphNode firstNode = nodeList.get(0);
        costMap.put(firstNode, 0);
        // 结果路径父节点关系 如 a->b->c c的父节点是b b的父节点是a
        Map<GraphNode, GraphNode> parentMap = new HashMap<>(nodeList.size() - 1);
        Set<GraphNode> processed = new HashSet<>(nodeList.size());

        GraphNode node = firstNode;
        while (null != node) {
            int cost = costMap.get(node);
            List<GraphLine> lineList = node.getLines();
            for (GraphLine line : lineList) {
                int newCost = cost + line.getWeight();
                // 到邻居节点的花费
                Integer neighborCost = costMap.get(line.getB());
                // 原花费大于从当前节点出发的花费
                if (null == neighborCost || neighborCost > newCost) {
                    costMap.put(line.getB(), newCost);
                    parentMap.put(line.getB(), node);
                }
            }
            processed.add(node);
            node = getLowestNode(costMap, processed);
        }

        costMap.forEach((k, v) -> PrintUtil.log("k : {}, v : {}", k.getName(), v));
        parentMap.forEach((k, v) -> PrintUtil.log("k : {}, v : {}", k.getName(), v.getName()));

        String path = "";
        node = nodeList.get(nodeList.size() - 1);
        while (node != null) {
            path = path + node.getName() + "->";
            node = parentMap.get(node);
        }
        PrintUtil.log("path : {}", path);
    }

    /**
     * 找出下一个未处理的花费最低的Node
     *
     * @param costMap
     * @param processed
     */
    private static GraphNode getLowestNode(Map<GraphNode, Integer> costMap, Set<GraphNode> processed) {
        int minCost = Integer.MAX_VALUE;
        GraphNode result = null;
        Set<Map.Entry<GraphNode, Integer>> entrySet = costMap.entrySet();
        Iterator<Map.Entry<GraphNode, Integer>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<GraphNode, Integer> next = iterator.next();
            GraphNode node = next.getKey();
            int cost = next.getValue();
            if (!processed.contains(node) && cost < minCost) {
                result = node;
                minCost = next.getValue();
            }
        }
        return result;
    }

    /**
     * 初始化一个有向图
     *
     * @return
     */
    private static List<GraphNode> initGraphNode() {
        GraphNode a = new GraphNode("A");
        GraphNode b = new GraphNode("B");
        GraphNode c = new GraphNode("C");
        GraphNode d = new GraphNode("D");
        GraphNode e = new GraphNode("E");
        GraphNode f = new GraphNode("F");

        List<GraphNode> nodeList = new ArrayList<>(6);
        nodeList.add(a);
        nodeList.add(b);
        nodeList.add(c);
        nodeList.add(d);
        nodeList.add(e);
        nodeList.add(f);

        // 起点设置为a
        a.setWeight(0);

        // 每个点有的线都是以改点为出发点的
        GraphLine ab = new GraphLine(a, b, 5);
        GraphLine ad = new GraphLine(a, d, 2);
        a.addLines(ab, ad);

        GraphLine bc = new GraphLine(b, c, 4);
        GraphLine be = new GraphLine(b, e, 2);
        b.addLines(bc, be);

        GraphLine ce = new GraphLine(c, e, 6);
        GraphLine cf = new GraphLine(c, f, 3);
        c.addLines(ce, cf);

        GraphLine db = new GraphLine(d, b, 8);
        GraphLine de = new GraphLine(d, e, 7);
        d.addLines(db, de);

        GraphLine ef = new GraphLine(e, f, 1);
        e.addLines(ef);

        return nodeList;
    }
}
