package edu.wpi.teamname;

public class Edge {
  private String startNode;
  private String endNode;

  private double cost;

  public Edge(String startNode, String endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public Edge() {
    this.startNode = "";
    this.endNode = "";
  }

  public String getStartNode() {
    return startNode;
  }

  public void setStartNode(String startNode) {
    this.startNode = startNode;
  }

  public String getEndNode() {
    return endNode;
  }

  public void setEndNode(String endNode) {
    this.endNode = endNode;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }
}
