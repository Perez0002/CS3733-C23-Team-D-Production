package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
public class SanitationRequest extends ServiceRequest {
  // attributes
  @Getter @Setter int bioLevel;

  public SanitationRequest(
      String reason, int bioLevel, Employee staff, LocationName location, String urgency) {
    super(staff, reason, "SanitationRequestData", location, urgency);
    this.bioLevel = bioLevel;
  }

  public SanitationRequest() {
    super();
    this.bioLevel = 0;
  }

  public SanitationRequest(
      int serviceRequestId,
      Status stat,
      Employee associatedStaff,
      String reason,
      String serviceRequestType,
      LocationName location,
      String urgency,
      Date date,
      int bioLevel) {
    super(
        serviceRequestId,
        stat,
        associatedStaff,
        reason,
        serviceRequestType,
        location,
        urgency,
        date);
    this.bioLevel = bioLevel;
  }
}
