package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/*
PatientTransportData
creates an entity object containing data for use on frontend PatientTransport UI
*/

@Entity
public class PatientTransportRequest extends ServiceRequest {

  // Attributes of PatientTransportData class
  @Getter @Setter private String endRoom;

  /*
  PatientTransportData()
  @param String patientID, String endRoom, ArrayList<String> equipment, String reason, String[] sendTo
  @return PatientTransportData object
  creates a PatientTransportData() object to be manipulated
  -- relationship with PatientTransportController: this function is called when
  submit() is called
  */
  public PatientTransportRequest(
      String endRoom, String reason, Employee sendTo, String urgency, LocationName location) {
    super(sendTo, reason, "PatientTransportData", location, urgency);
    this.endRoom = endRoom;
  }

  public PatientTransportRequest() {}

  public PatientTransportRequest(
      int serviceRequestId,
      Status stat,
      Employee associatedStaff,
      String reason,
      String serviceRequestType,
      LocationName location,
      String urgency,
      Date date,
      String endRoom) {
    super(
        serviceRequestId,
        stat,
        associatedStaff,
        reason,
        serviceRequestType,
        location,
        urgency,
        date);
    this.endRoom = endRoom;
  }
}
