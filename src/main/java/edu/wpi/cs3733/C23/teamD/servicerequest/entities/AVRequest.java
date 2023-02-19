package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
public class AVRequest extends ServiceRequest {

  @Getter @Setter private String systemFailureTextField;
  @Getter @Setter private LocalDate dateFirstSeen;

  public AVRequest(
      Employee associatedStaff,
      String reason,
      String serviceRequestType,
      String systemFailureTextField,
      LocalDate dateFirstSeen,
      LocationName location,
      String urgency) {
    super(associatedStaff, reason, serviceRequestType, location, urgency);
    this.systemFailureTextField = systemFailureTextField;
    this.dateFirstSeen = dateFirstSeen;
  }

  public AVRequest() {}
}
