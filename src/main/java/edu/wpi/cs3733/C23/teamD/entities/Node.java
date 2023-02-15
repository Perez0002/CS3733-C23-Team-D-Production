package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Node {
  @Id private String nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;

  @OneToMany(mappedBy = "node")
  private List<Move> moves;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeID, xcoord, ycoord, floor, building, moves);
  }

  @Transient private LocationName location;

  // @OneToMany(mappedBy = "fromNode toNode")
  @Transient private ArrayList<Edge> nodeEdges;

  public Node(int xcoord, int ycoord, String floor, String building) {
    String xString = String.format("%04d", xcoord);
    String yString = String.format("%04d", ycoord);
    this.nodeID = (floor + "X" + xString + "Y" + yString);
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.nodeEdges = new ArrayList<Edge>();
  }

  public Node(Node n) {
    this.nodeID = n.getNodeID();
    this.xcoord = n.getXcoord();
    this.ycoord = n.getYcoord();
    this.floor = n.getFloor();
    this.building = n.getBuilding();
    this.nodeEdges = new ArrayList<Edge>();
  }

  public List<Move> getMoves() {
    return moves;
  }

  public void setMoves(List<Move> moves) {
    this.moves = moves;
  }

  public Node() {
    this.nodeID = "";
    this.xcoord = 0;
    this.ycoord = 0;
    this.floor = "";
    this.building = "";
    this.nodeEdges = new ArrayList<Edge>();
  }

  public String getAll() {
    return nodeID + " " + xcoord + " " + ycoord + " " + floor + " " + building;
  }

  public boolean nodeEquals(Node node2) {
    if (this.nodeID.equals(node2.nodeID)) return true;
    else return false;
  }

  public int getXcoord() {
    return xcoord;
  }

  public void setXcoord(int xcoord) {
    this.xcoord = xcoord;
  }

  public int getYcoord() {
    return ycoord;
  }

  public void setYcoord(int ycoord) {
    this.ycoord = ycoord;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public void setNodeID() {
    String xString = String.format("%04d", this.xcoord);
    String yString = String.format("%04d", this.ycoord);
    this.nodeID = (this.floor + "X" + xString + "Y" + yString);
  }

  public LocationName getLocation() {
    return location;
  }

  public void setLocation(LocationName location) {
    this.location = location;
  }

  public ArrayList<Edge> getNodeEdges() {
    return nodeEdges;
  }

  public void setNodeEdges(ArrayList<Edge> nodeEdges) {
    this.nodeEdges = nodeEdges;
  }

  public void setLongName(String longName) {
    this.location.setLongName(longName);
  }

  public String getLongName() {
    return this.location.getLongName();
  }

  public void setShortName(String shortName) {
    this.location.setLongName(shortName);
  }

  public String getShortName() {
    return this.location.getShortName();
  }

  public void setLocationType(String locationType) {
    this.location.setLocationType(locationType);
  }

  public String getLocationType() {
    return this.location.getLocationType();
  }
}
