package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class ComputerServiceRequest extends ServiceRequest {
  @Getter @Setter String deviceType;

  public ComputerServiceRequest() {
    super();
  }

  public ComputerServiceRequest(
      String reason, Employee staff, String urgency, String deviceType, LocationName location) {
    super(staff, reason, "ComputerService", location, urgency);
    this.deviceType = deviceType;
  }
  public ComputerServiceRequest(int serviceRequestId,
                                Status stat,
                                Employee associatedStaff,
                                String reason,
                                String serviceRequestType,
                                LocationName location,
                                String urgency,
                                String deviceType) {
    super(serviceRequestId, stat, associatedStaff, reason, serviceRequestType, location, urgency);
    this.deviceType = deviceType;
  }
}
