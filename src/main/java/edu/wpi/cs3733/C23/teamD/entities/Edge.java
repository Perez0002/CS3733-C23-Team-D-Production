package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.*;

@Entity
public class Edge {
  @Getter @Setter @Id private String edgeID;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(name = "fromNode", foreignKey = @ForeignKey(name = "fromNode_id_fk"))
  Node fromNode;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(name = "toNode", foreignKey = @ForeignKey(name = "toNode_id_fk"))
  Node toNode;

  public Edge(Node fromNode, Node toNode) {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.edgeID = fromNode.getNodeID() + "_" + toNode.getNodeID();
  }

  public Edge() {
    this.toNode = new Node();
    this.fromNode = new Node();
    edgeID = "";
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
