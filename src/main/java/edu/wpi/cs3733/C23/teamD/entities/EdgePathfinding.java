package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

public class EdgePathfinding {

  @Getter @Setter @Id private String edgeID;
  @Getter @Setter private double cost;

  @Getter @Setter NodePathfinding toNode;
  @Getter @Setter NodePathfinding fromNode;

  public EdgePathfinding(Node fromNode, Node toNode) {
    edgeID = fromNode.getNodeID() + "_" + toNode.getNodeID();
    this.fromNode = new NodePathfinding(fromNode);
    this.toNode = new NodePathfinding(toNode);
    this.cost =
        Math.sqrt(
            Math.pow(fromNode.getXcoord() - toNode.getXcoord(), 2)
                + Math.pow(fromNode.getYcoord() - toNode.getYcoord(), 2));
  }

  public EdgePathfinding(NodePathfinding fromNode, NodePathfinding toNode) {
    edgeID = fromNode.getNodeID() + "_" + toNode.getNodeID();
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.cost =
        Math.sqrt(
            Math.pow(fromNode.getXcoord() - toNode.getXcoord(), 2)
                + Math.pow(fromNode.getYcoord() - toNode.getYcoord(), 2));
  }

  public EdgePathfinding() {
    edgeID = "";
    this.fromNode = new NodePathfinding();
    this.toNode = new NodePathfinding();
    this.cost = 0;
  }

  public EdgePathfinding(Edge edge) {
    this.fromNode = new NodePathfinding(edge.getFromNode());
    this.toNode = new NodePathfinding(edge.getToNode());
    this.cost =
        Math.sqrt(
            Math.pow(fromNode.getXcoord() - toNode.getXcoord(), 2)
                + Math.pow(fromNode.getYcoord() - toNode.getYcoord(), 2));
  }
  /** generates and sets the cost of a node to the euclidian distance between its two nodes */
  public void genCost() {
    this.cost =
        Math.sqrt(
            Math.pow(fromNode.getXcoord() - toNode.getXcoord(), 2)
                + Math.pow(fromNode.getYcoord() - toNode.getYcoord(), 2));
  }

  public void genEdgeID() {
    this.edgeID = fromNode.getNodeID() + "_" + toNode.getNodeID();
  }

  public String getFromNodeID() {
    return this.fromNode.getNodeID();
  }

  public String getToNodeID() {
    return this.fromNode.getNodeID();
  }
}
