package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.Entity;

@Entity
public class ComputerServiceRequest extends ServiceRequest {
  private String deviceType;

  public ComputerServiceRequest() {
    super();
  }

  public ComputerServiceRequest(
      String reason, Employee staff, String urgency, String deviceType, LocationName location) {
    super(staff, reason, "ComputerService", location, urgency);
    this.deviceType = deviceType;
  }
}
