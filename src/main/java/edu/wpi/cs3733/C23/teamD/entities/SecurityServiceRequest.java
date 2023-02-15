package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
public class SecurityServiceRequest extends ServiceRequest {
  @Getter private String typeOfSecurityRequest;

  @Getter private String urgency;

  public SecurityServiceRequest(
      String typeOfSecurityRequest, String urgency, String staff, String reason) {
    super(staff, reason, "Security");
    this.typeOfSecurityRequest = typeOfSecurityRequest;
    this.urgency = urgency;
  }

  public SecurityServiceRequest() {
    super();
    this.typeOfSecurityRequest = "";
    this.urgency = "";
  }
}
