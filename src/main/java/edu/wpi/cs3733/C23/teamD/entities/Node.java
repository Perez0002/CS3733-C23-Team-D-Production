package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import lombok.*;

@Entity
public class Node {
  @Getter @Setter @Id private String nodeID;
  @Getter @Setter private int xcoord;
  @Getter @Setter private int ycoord;
  @Getter @Setter private String floor;
  @Getter @Setter private String building;

  @Getter
  @Setter
  @OneToMany(mappedBy = "node")
  private List<Move> moves;

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this.getNodeID().equals(((Node) obj).getNodeID())) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeID, xcoord, ycoord, floor, building, moves);
  }

  public Node(int xcoord, int ycoord, String floor, String building) {
    String xString = String.format("%04d", xcoord);
    String yString = String.format("%04d", ycoord);
    this.nodeID = (floor + "X" + xString + "Y" + yString);
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }

  public Node() {
    this.nodeID = "";
    this.xcoord = 0;
    this.ycoord = 0;
    this.floor = "";
    this.building = "";
  }

  public String getAll() {
    return nodeID + " " + xcoord + " " + ycoord + " " + floor + " " + building;
  }

  public boolean nodeEquals(Node node2) {
    if (this.nodeID.equals(node2.nodeID)) return true;
    else return false;
  }
}
