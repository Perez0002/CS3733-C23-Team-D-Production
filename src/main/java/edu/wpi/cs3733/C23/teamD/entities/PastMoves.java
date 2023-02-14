package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
public class PastMoves {
    @Id
    @Getter @Setter
    private String nodeID;

    @Id
    @Getter @Setter
    private String locationName;

    @Id
    @Getter @Setter
    private Date moveDate;

    public PastMoves() {

    }
}
