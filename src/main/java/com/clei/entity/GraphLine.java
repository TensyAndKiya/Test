package com.clei.entity;

/**
 * 图线
 *
 * @author KIyA
 */
public class GraphLine {

    private GraphNode a;

    private GraphNode b;

    private int weight;

    public GraphLine(GraphNode a, GraphNode b, int weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public GraphNode getA() {
        return a;
    }

    public void setA(GraphNode a) {
        this.a = a;
    }

    public GraphNode getB() {
        return b;
    }

    public void setB(GraphNode b) {
        this.b = b;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
