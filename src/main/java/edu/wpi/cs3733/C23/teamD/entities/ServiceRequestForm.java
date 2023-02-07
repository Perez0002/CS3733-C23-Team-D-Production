package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ServiceRequestForm {
  private String serviceRequestType;

  @Enumerated(value = EnumType.STRING)
  private Status stat;

  public enum Status {
    DONE,
    PROCESSING,
    BLANK;
  }

  private String reason;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int serviceRequestId;

  private String associatedStaff;

  @CreationTimestamp private Date dateAndTime;

  public ServiceRequestForm(
      String associatedStaff, Status stat, String reason, String serviceRequestType) {
    this.associatedStaff = associatedStaff;
    this.stat = stat;
    this.dateAndTime = new Date();
    this.reason = reason;
    this.serviceRequestType = serviceRequestType;
  }

  public ServiceRequestForm(
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

  public ServiceRequestForm() {
    this.associatedStaff = "";
    this.stat = Status.BLANK;
    this.dateAndTime = new Date();
    this.reason = "";
    this.serviceRequestType = "";
  }

  public String getServiceRequestType() {
    return serviceRequestType;
  }

  public void setServiceRequestType(String serviceRequestType) {
    this.serviceRequestType = serviceRequestType;
  }

  public Status getStat() {
    return stat;
  }

  public void setStat(Status stat) {
    this.stat = stat;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public int getServiceRequestId() {
    return serviceRequestId;
  }

  public String getAssociatedStaff() {
    return associatedStaff;
  }

  public void setAssociatedStaff(String associatedStaff) {
    this.associatedStaff = associatedStaff;
  }

  public Date getDateAndTime() {
    return dateAndTime;
  }

  public void setDateAndTime(Date dateAndTime) {
    this.dateAndTime = dateAndTime;
  }
}
