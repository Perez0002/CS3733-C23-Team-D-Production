package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import jakarta.persistence.Entity;
import java.util.Date;

@Entity
public class ComputerServiceRequest extends ServiceRequest {
  private String urgency;
  private String deviceType;
  private String location;

  public ComputerServiceRequest() {
    super();
  }

  public ComputerServiceRequest(
      String reason, String staff, String urgency, String deviceType, String location) {
    super(staff, reason, "ComputerService");
    this.urgency = urgency;
    this.deviceType = deviceType;
    this.location = location;
  }

  public ComputerServiceRequest(int serviceID, String reason, String staff, Date date) {
    super(serviceID, staff, reason, "ComputerService", date);
  }
}
