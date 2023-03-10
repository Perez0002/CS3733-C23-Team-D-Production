package edu.wpi.cs3733.C23.teamD.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
public class PastMoves {
  @Id @Getter @Setter private String nodeID;

  @Id @Getter @Setter private String locationName;

  @Id @Getter @Setter private Date moveDate;

  public PastMoves() {}

  public PastMoves(Move m) {
    this.nodeID = m.getNodeID();
    this.locationName = m.getLongName();
    this.moveDate = m.getMoveDate();
  }
}
