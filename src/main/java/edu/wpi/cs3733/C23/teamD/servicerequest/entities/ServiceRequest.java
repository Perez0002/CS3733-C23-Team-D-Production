package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ServiceRequest {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int serviceRequestId;

  @Getter @Setter private String serviceRequestType;

  @Getter
  @Setter
  @Enumerated(value = EnumType.STRING)
  private Status stat;

  public ServiceRequest() {}

  @Getter
  public enum Status {
    DONE,
    PROCESSING,
    BLANK;
  }

  @Getter @Setter private String reason;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(
      name = "associatedStaff",
      foreignKey =
          @ForeignKey(
              name = "employee_id_fk",
              foreignKeyDefinition =
                  "FOREIGN KEY (associatedStaff) REFERENCES Employee(employeeID) ON UPDATE CASCADE ON DELETE CASCADE"))
  private Employee associatedStaff;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(
      name = "staffAssigning",
      foreignKey =
          @ForeignKey(
              name = "assigning_employee_fk",
              foreignKeyDefinition =
                  "FOREIGN KEY (staffAssigning) REFERENCES Employee(employeeID) ON UPDATE CASCADE ON DELETE CASCADE"))
  private Employee staffAssigning;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(
      name = "location",
      foreignKey =
          @ForeignKey(
              name = "location_name_fk",
              foreignKeyDefinition =
                  "FOREIGN KEY (location) REFERENCES LocationName(longName) ON UPDATE CASCADE ON DELETE CASCADE"))
  private LocationName location;

  @Getter @Setter @CreationTimestamp private Date dateAndTime;

  @Getter private String urgency;

  public ServiceRequest(
      Employee associatedStaff,
      String reason,
      String serviceRequestType,
      LocationName location,
      String urgency) {
    this.staffAssigning = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    this.associatedStaff = associatedStaff;
    this.stat = Status.PROCESSING;
    this.dateAndTime = new Date();
    this.reason = reason;
    this.serviceRequestType = serviceRequestType;
    this.location = location;
    this.urgency = urgency;
  }

  public ServiceRequest(
      int serviceRequestId,
      Status stat,
      Employee associatedStaff,
      String reason,
      String serviceRequestType,
      LocationName location,
      String urgency) {
    this.serviceRequestId = serviceRequestId;
    this.stat = stat;
    this.staffAssigning = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    this.associatedStaff = associatedStaff;
    this.stat = Status.PROCESSING;
    this.dateAndTime = new Date();
    this.reason = reason;
    this.serviceRequestType = serviceRequestType;
    this.location = location;
    this.urgency = urgency;
  }
}
