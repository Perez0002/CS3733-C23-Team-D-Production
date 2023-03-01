package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class LabRequest extends ServiceRequest {

  @Getter @Setter private String LabType;

  public LabRequest(
      Employee associatedStaff,
      String reason,
      String serviceRequestType,
      String LabType,
      LocationName location,
      String urgency) {
    super(associatedStaff, reason, serviceRequestType, location, urgency);
    this.LabType = LabType;
  }

  public LabRequest() {}
}
