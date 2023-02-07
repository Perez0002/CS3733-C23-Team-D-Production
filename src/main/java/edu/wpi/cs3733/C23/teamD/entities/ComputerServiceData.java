package edu.wpi.cs3733.C23.teamD.entities;

import java.util.Date;

public class ComputerServiceData extends ServiceRequestForm {
  public ComputerServiceData() {
    super();
  }

  public ComputerServiceData(String reason, String staff, Status stat) {
    super(staff, stat, reason, "ComputerService");
  }

  public ComputerServiceData(int serviceID, String reason, String staff, Status stat, Date date) {
    super(serviceID, staff, stat, reason, "ComputerService", date);
  }
}
