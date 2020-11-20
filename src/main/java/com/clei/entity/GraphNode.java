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

    private String name;

    private int weight = Integer.MAX_VALUE;

    private List<GraphLine> lines = new ArrayList<>();

    private String path;

    public GraphNode(String name) {
        this.name = name;
    }

    public void addLines(GraphLine... arr) {
        this.lines.addAll(Arrays.asList(arr));
    }

    public void addLines(List<GraphLine> lines) {
        this.lines.addAll(lines);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
