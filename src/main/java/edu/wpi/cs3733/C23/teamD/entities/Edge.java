package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Edge {
  @Id private String edgeID;

  @ManyToOne
  @JoinColumn(name = "fromNode", foreignKey = @ForeignKey(name = "fromNode_id_fk"))
  Node fromNode;

  @ManyToOne
  @JoinColumn(name = "toNode", foreignKey = @ForeignKey(name = "toNode_id_fk"))
  Node toNode;

  @Transient private double cost;

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
    if (getToNode().getLocationType().equals("ELEV")
        && getFromNode().getLocationType().equals("ELEV")) {
      this.cost = 500;
    } else if (getToNode().getLocationType().equals("STAI")
        && getFromNode().getLocationType().equals("STAI")) {
      this.cost = 1500;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this.getEdgeID().equals(((Edge) obj).getEdgeID())) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(edgeID, toNode, fromNode);
  }

  /**
   * generates an EdgeID based on the nodeID's of an Edge's node and sets it to the EdgeID attribute
   */
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
