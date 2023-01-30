package edu.wpi.cs3733.C23.teamD.entities;

import java.util.ArrayList;

public class Node {
  private String nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;
  private locationName location;
  private ArrayList<Edge> nodeEdges;

  public Node(int xcoord, int ycoord, String floor, String building) {
    this.nodeID = floor +"X"+ xcoord + "Y" +ycoord;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.nodeEdges = new ArrayList<Edge>();
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

  public locationName getLocation() {
    return location;
  }

  public void setLocation(locationName location) {
    this.location = location;
  }

  public ArrayList<Edge> getNodeEdges() {
    return nodeEdges;
  }

  public void setNodeEdges(ArrayList<Edge> nodeEdges) {
    this.nodeEdges = nodeEdges;
  }
}
