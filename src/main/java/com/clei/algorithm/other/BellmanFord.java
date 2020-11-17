package com.clei.algorithm.other;

import com.clei.entity.GraphLine;
import com.clei.entity.GraphNode;
import com.clei.utils.PrintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 贝尔曼福特算法
 * 图中求解最短路径
 *
 * @author KIyA
 */
public class BellmanFord {

    public static void main(String[] args) {

        List<GraphNode> nodeList = initGraphNode();
        updateWeight(nodeList);
        // 起点a到g的最小权重值
        int weight = nodeList.get(nodeList.size() - 1).getWeight();
        PrintUtil.println("A -> G : {}", weight);
        // 起点a到e的最小权重值
        weight = nodeList.get(nodeList.size() - 3).getWeight();
        PrintUtil.println("A -> E : {}", weight);
        // 起点a到f的最小权重值
        weight = nodeList.get(nodeList.size() - 2).getWeight();
        PrintUtil.println("A -> F : {}", weight);
    }

    private static void updateWeight(List<GraphNode> nodeList) {
        for (GraphNode n : nodeList) {
            boolean changed = false;
            int weight = n.getWeight();
            List<GraphLine> lineList = n.getLines();
            for (GraphLine l : lineList) {
                int nodeWeight = weight + l.getWeight();
                if (nodeWeight < l.getA().getWeight()) {
                    l.getA().setWeight(nodeWeight);
                    changed = true;
                }
                if (nodeWeight < l.getB().getWeight()) {
                    l.getB().setWeight(nodeWeight);
                    changed = true;
                }
            }
            // 快速结束循环
            if (!changed) {
                break;
            }
        }
    }

    /**
     * 初始化一个图
     *
     * @return
     */
    private static List<GraphNode> initGraphNode() {
        GraphNode a = new GraphNode();
        GraphNode b = new GraphNode();
        GraphNode c = new GraphNode();
        GraphNode d = new GraphNode();
        GraphNode e = new GraphNode();
        GraphNode f = new GraphNode();
        GraphNode g = new GraphNode();

        List<GraphNode> nodeList = new ArrayList<>(7);
        nodeList.add(a);
        nodeList.add(b);
        nodeList.add(c);
        nodeList.add(d);
        nodeList.add(e);
        nodeList.add(f);
        nodeList.add(g);

        // 起点设置为a
        a.setWeight(0);

        // 每条线只添加到一个节点上，避免遍历节点时线的重复遍历
        // 或者可以改为每次遍历看下线这一轮是否已经遍历过
        GraphLine ab = new GraphLine(a, b, 9);
        GraphLine ac = new GraphLine(a, c, 2);
        a.addLines(ab, ac);

        GraphLine bc = new GraphLine(b, d, 6);
        GraphLine bd = new GraphLine(b, d, 3);
        GraphLine be = new GraphLine(b, e, 1);
        b.addLines(bc, bd, be);

        GraphLine cd = new GraphLine(c, d, 2);
        GraphLine cf = new GraphLine(c, f, 9);
        c.addLines(cd, cf);

        GraphLine de = new GraphLine(d, e, 5);
        GraphLine df = new GraphLine(d, f, 6);
        d.addLines(de, df);

        GraphLine ef = new GraphLine(e, f, 3);
        GraphLine eg = new GraphLine(e, g, 7);
        e.addLines(ef, eg);

        GraphLine fg = new GraphLine(f, g, 4);
        f.addLines(fg);

        return nodeList;
    }

}
