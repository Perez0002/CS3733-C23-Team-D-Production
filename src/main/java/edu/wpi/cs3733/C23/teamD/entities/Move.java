package edu.wpi.cs3733.C23.teamD.entities;

import java.util.Date;

public class Move {
  private String nodeID;
  private String longName;
  private Date moveDate;

  public Move(String nodeID, String longName, Date moveDate) {
    this.longName = longName;
    this.nodeID = nodeID;
    this.moveDate = moveDate;
  }

  public Move() {
    this.longName = "";
    this.nodeID = "";
    this.moveDate = new Date(2023, 1, 1);
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public Date getMoveDate() {
    return moveDate;
  }

  public void setMoveDate(Date moveDate) {
    this.moveDate = moveDate;
  }
}
