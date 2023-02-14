package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.*;

@Entity
public class LocationName {
  @Getter @Setter @Id private String longName;
  @Getter @Setter private String shortName;
  @Getter @Setter private String locationType;

  @Getter
  @Setter
  @OneToMany(mappedBy = "location")
  private List<Move> moves;

  public LocationName(String longName, String shortName, String nodeType) {
    this.longName = longName;
    this.shortName = shortName;
    this.locationType = nodeType;
  }

  public boolean equals(LocationName locationName) {
    return this.getLongName().equals(locationName.getLongName());
  }

  public LocationName() {
    this.longName = "";
    this.shortName = "";
    this.locationType = "";
  }
}
