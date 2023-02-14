package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ServiceRequest {
  @Getter @Setter private String serviceRequestType;

  @Getter
  @Setter
  @Enumerated(value = EnumType.STRING)
  private Status stat;

  public enum Status {
    DONE,
    PROCESSING,
    BLANK;
  }

  @Getter @Setter private String reason;

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int serviceRequestId;

  @Getter @Setter private String associatedStaff;
  @Getter @Setter @CreationTimestamp private Date dateAndTime;

  public ServiceRequest(
      String associatedStaff, Status stat, String reason, String serviceRequestType) {
    this.associatedStaff = associatedStaff;
    this.stat = stat;
    this.dateAndTime = new Date();
    this.reason = reason;
    this.serviceRequestType = serviceRequestType;
  }

  public ServiceRequest(
      int serviceId,
      String associatedStaff,
      Status stat,
      String reason,
      String serviceRequestType,
      Date date) {
    this.associatedStaff = associatedStaff;
    this.serviceRequestId = serviceId;
    this.stat = stat;
    this.dateAndTime = date;
    this.reason = reason;
    this.serviceRequestType = serviceRequestType;
  }

  public ServiceRequest() {
    this.associatedStaff = "";
    this.stat = Status.BLANK;
    this.dateAndTime = new Date();
    this.reason = "";
    this.serviceRequestType = "";
  }
}
