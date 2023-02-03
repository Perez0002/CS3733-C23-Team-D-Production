package edu.wpi.cs3733.C23.teamD.entities;

public class Edge {
  private String edgeID;
  private Node fromNode;
  private Node toNode;

  private double cost;

  public Edge(Node fromNode, Node toNode) {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.edgeID = fromNode.getNodeID() + "_" + toNode.getNodeID();
    this.cost =
        Math.sqrt(
            Math.pow(fromNode.getXcoord() - toNode.getXcoord(), 2)
                + Math.pow(fromNode.getYcoord() - toNode.getYcoord(), 2));
  }

  public Edge() {
    this.toNode = new Node();
    this.fromNode = new Node();
    edgeID = "";
    cost = 0;
  }

  public String getEdgeID() {
    return edgeID;
  }

  public void setEdgeID(String edgeID) {
    this.edgeID = edgeID;
  }

  public Node getFromNode() {
    return fromNode;
  }

  public void setFromNode(Node fromNode) {
    this.fromNode = fromNode;
  }

  public Node getToNode() {
    return toNode;
  }

  public void setToNode(Node toNode) {
    this.toNode = toNode;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  /** generates and sets the cost of a node to the euclidian distance between its two nodes */
  public void genCost() {
    this.cost =
        Math.sqrt(
            Math.pow(fromNode.getXcoord() - toNode.getXcoord(), 2)
                + Math.pow(fromNode.getYcoord() - toNode.getYcoord(), 2));
  }

  /**
   * generates an EdgeID based on the nodeID's of an Edge's node and sets it to the EdgeID attribute
   */
  public void genEdgeID() {
    this.edgeID = fromNode.getNodeID() + "_" + toNode.getNodeID();
  }
}
