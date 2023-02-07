package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class LocationName {
  @Id private String longName;
  private String shortName;
  private String locationType;

  @OneToMany(mappedBy = "location")
  private List<Move> moves;

  public LocationName(String longName, String shortName, String nodeType) {
    this.longName = longName;
    this.shortName = shortName;
    this.locationType = nodeType;
  }

  public LocationName() {
    this.longName = "";
    this.shortName = "";
    this.locationType = "";
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getLocationType() {
    return locationType;
  }

  public List<Move> getMoves() {
    return moves;
  }

  public void setMoves(List<Move> moves) {
    this.moves = moves;
  }

  public void setLocationType(String locationType) {
    this.locationType = locationType;
  }
}
