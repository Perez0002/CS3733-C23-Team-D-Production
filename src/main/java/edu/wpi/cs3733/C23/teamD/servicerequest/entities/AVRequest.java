package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
public class AVRequest extends ServiceRequest {

  @Getter @Setter private LocalDate dateFirstSeen;

  public AVRequest(
      Employee associatedStaff,
      String reason,
      String serviceRequestType,
      LocalDate dateFirstSeen,
      LocationName location,
      String urgency) {
    super(associatedStaff, reason, serviceRequestType, location, urgency);
    this.dateFirstSeen = dateFirstSeen;
  }

  public AVRequest() {}

  public AVRequest(
      int serviceRequestId,
      Status stat,
      Employee associatedStaff,
      String reason,
      String serviceRequestType,
      LocationName location,
      String urgency,
      Date date,
      LocalDate dateFirstSeen) {
    super(
        serviceRequestId,
        stat,
        associatedStaff,
        reason,
        serviceRequestType,
        location,
        urgency,
        date);
    this.dateFirstSeen = dateFirstSeen;
  }
}
