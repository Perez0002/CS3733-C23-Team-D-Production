package edu.wpi.cs3733.C23.teamD.entities;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class NodePathfinding {
  @Getter @Setter private String nodeID;
  @Getter @Setter private int xcoord;
  @Getter @Setter private int ycoord;
  @Getter @Setter private String floor;
  @Getter @Setter private String building;
  @Getter @Setter private LocationName location;
  @Getter @Setter private ArrayList<EdgePathfinding> nodeEdges;

  public NodePathfinding(int xcoord, int ycoord, String floor, String building) {
    String xString = String.format("%04d", xcoord);
    String yString = String.format("%04d", ycoord);
    this.nodeID = (floor + "X" + xString + "Y" + yString);
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.nodeEdges = new ArrayList<EdgePathfinding>();
  }

  public NodePathfinding() {
    this.nodeID = "";
    this.xcoord = 0;
    this.ycoord = 0;
    this.floor = "";
    this.building = "";
    this.nodeEdges = new ArrayList<EdgePathfinding>();
  }

  public NodePathfinding(Node node) {
    this.nodeID = node.getNodeID();
    this.xcoord = node.getXcoord();
    this.ycoord = node.getYcoord();
    this.floor = node.getFloor();
    this.building = node.getBuilding();
    this.nodeEdges = new ArrayList<EdgePathfinding>();
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

  public Node pathToDB() {
    return new Node(this.xcoord, this.ycoord, this.floor, this.building);
  }
}
