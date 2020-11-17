package com.clei.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 图节点
 *
 * @author
 */
public class GraphNode {

    private int weight = Integer.MAX_VALUE;

    private List<GraphLine> lines = new ArrayList<>();

    public GraphNode() {
    }

    public void addLines(GraphLine... arr) {
        this.lines.addAll(Arrays.asList(arr));
    }

    public void addLines(List<GraphLine> lines) {
        this.lines.addAll(lines);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<GraphLine> getLines() {
        return lines;
    }


}
