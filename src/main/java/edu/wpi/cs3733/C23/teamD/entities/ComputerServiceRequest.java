package edu.wpi.cs3733.C23.teamD.entities;

import java.util.Date;

public class ComputerServiceRequest extends ServiceRequest {
  public ComputerServiceRequest() {
    super();
  }

  public ComputerServiceRequest(String reason, String staff, Status stat) {
    super(staff, stat, reason, "ComputerService");
  }

  public ComputerServiceRequest(
      int serviceID, String reason, String staff, Status stat, Date date) {
    super(serviceID, staff, stat, reason, "ComputerService", date);
  }
}
