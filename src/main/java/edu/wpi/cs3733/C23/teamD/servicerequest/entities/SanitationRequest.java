package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.*;
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
}
