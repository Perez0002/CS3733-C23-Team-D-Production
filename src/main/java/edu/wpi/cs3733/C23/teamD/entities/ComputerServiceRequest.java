package edu.wpi.cs3733.C23.teamD.entities;

import java.util.Date;

public class ComputerServiceRequest extends ServiceRequest {
  private String urgency;
  private String deviceType;
  private String location;

  public ComputerServiceRequest() {
    super();
  }

  public ComputerServiceRequest(String reason, String staff, Status stat,String urgency,String deviceType, String location) {
    super(staff, stat, reason, "ComputerService");
    this.urgency=urgency;
    this.deviceType=deviceType;
    this.location=location;

  }

  public ComputerServiceRequest(
      int serviceID, String reason, String staff, Status stat, Date date) {
    super(serviceID, staff, stat, reason, "ComputerService", date);
  }
}
