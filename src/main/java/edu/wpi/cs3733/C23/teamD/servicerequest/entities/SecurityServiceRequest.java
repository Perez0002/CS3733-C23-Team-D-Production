package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
public class SecurityServiceRequest extends ServiceRequest {
  @Getter private String typeOfSecurityRequest;

  public SecurityServiceRequest(
      String typeOfSecurityRequest,
      Employee staff,
      String reason,
      LocationName location,
      String urgency) {
    super(staff, reason, "Security", location, urgency);
    this.typeOfSecurityRequest = typeOfSecurityRequest;
  }

  public SecurityServiceRequest() {
    super();
    this.typeOfSecurityRequest = "";
  }
  public SecurityServiceRequest(int serviceRequestId,
                                Status stat,
                                Employee associatedStaff,
                                String reason,
                                String serviceRequestType,
                                LocationName location,
                                String urgency,
                                String typeOfSecurityRequest) {
    super(serviceRequestId, stat, associatedStaff, reason, serviceRequestType, location, urgency);
    this.typeOfSecurityRequest = typeOfSecurityRequest;
  }
}
