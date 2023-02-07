package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Move {
  @Id @ManyToOne private Node node;
  @Id @ManyToOne private LocationName location;
  @Id @CreationTimestamp Date moveDate;

  @Override
  public boolean equals(Object obj) {
    if (this.getMoveDate().equals(((Move) obj).getMoveDate())) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(moveDate, location, node);
  }

  public Move(Node node, LocationName locationName, Date moveDate) {
    this.location = locationName;
    this.node = node;
    this.moveDate = moveDate;
  }

  public Move(Node node, LocationName locationName) {
    this.location = locationName;
    this.node = node;
  }

  public Move() {
    this.location = new LocationName();
    this.node = new Node();
    this.moveDate = new Date(2023, 1, 1);
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public LocationName getLocation() {
    return location;
  }

  public void setLocation(LocationName location) {
    this.location = location;
  }

  public Date getMoveDate() {
    return moveDate;
  }

  public void setMoveDate(Date moveDate) {
    this.moveDate = moveDate;
  }

  public String getLongName() {
    return this.location.getLongName();
  }

  public String getNodeID() {
    return this.node.getNodeID();
  }
}
