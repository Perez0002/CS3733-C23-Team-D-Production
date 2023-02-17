package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
public class AVRequest extends ServiceRequest {

  @Getter @Setter private String systemFailureTextField;
  @Getter @Setter private LocalDate dateFirstSeen;

  public AVRequest(
      String associatedStaff,
      String reason,
      String serviceRequestType,
      String systemFailureTextField,
      LocalDate dateFirstSeen) {
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
      LocalDate dateFirstSeen) {
    super(serviceId, associatedStaff, reason, serviceRequestType, date);
    this.systemFailureTextField = systemFailureTextField;
    this.dateFirstSeen = dateFirstSeen;
  }

  public AVRequest(String systemFailureTextField, LocalDate dateFirstSeen) {
    this.systemFailureTextField = systemFailureTextField;
    this.dateFirstSeen = dateFirstSeen;
  }

  public AVRequest() {}
}
