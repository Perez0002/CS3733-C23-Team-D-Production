package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.Entity;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
public class AVRequest extends ServiceRequest {

  @Getter @Setter private String systemFailureTextField;
  @Getter @Setter private Date dateFirstSeen;

  public AVRequest(
      String associatedStaff,
      String reason,
      String serviceRequestType,
      String systemFailureTextField,
      Date dateFirstSeen) {
    super(associatedStaff, reason, serviceRequestType);
    this.systemFailureTextField = systemFailureTextField;
    this.dateFirstSeen = dateFirstSeen;
  }

  public AVRequest(
      int serviceId,
      String associatedStaff,
      String reason,
      String serviceRequestType,
      Date date,
      String systemFailureTextField,
      Date dateFirstSeen) {
    super(serviceId, associatedStaff, reason, serviceRequestType, date);
    this.systemFailureTextField = systemFailureTextField;
    this.dateFirstSeen = dateFirstSeen;
  }

  public AVRequest(String systemFailureTextField, Date dateFirstSeen) {
    this.systemFailureTextField = systemFailureTextField;
    this.dateFirstSeen = dateFirstSeen;
  }

  public AVRequest() {}
}
