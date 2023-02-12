package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ServiceRequest {
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

  @ManyToOne
  @JoinColumn(
      name = "staffAssigned",
      foreignKey =
          @ForeignKey(
              name = "employee_id_fk",
              foreignKeyDefinition =
                  "FOREIGN KEY (staffAssigned) REFERENCES Employee(employeeID) ON UPDATE CASCADE ON DELETE CASCADE"))
  private Employee employees;

  private String associatedStaff;

  @CreationTimestamp private Date dateAndTime;

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

  public void setServiceRequestId(int serviceRequestId) {
    this.serviceRequestId = serviceRequestId;
  }
}
