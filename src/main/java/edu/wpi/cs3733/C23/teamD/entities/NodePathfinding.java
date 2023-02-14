package edu.wpi.cs3733.C23.teamD.entities;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class NodePathfinding extends Node {
  @Getter @Setter private LocationName location;
  @Getter @Setter private ArrayList<EdgePathfinding> nodeEdges;

  public NodePathfinding(int xcoord, int ycoord, String floor, String building) {
    super(xcoord, ycoord, floor, building);
    this.nodeEdges = new ArrayList<EdgePathfinding>();
  }

  public NodePathfinding() {
    super();
    this.nodeEdges = new ArrayList<EdgePathfinding>();
  }

  public NodePathfinding(Node node) {
    super(node.getXcoord(), node.getYcoord(), node.getFloor(), node.getBuilding());
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
}
