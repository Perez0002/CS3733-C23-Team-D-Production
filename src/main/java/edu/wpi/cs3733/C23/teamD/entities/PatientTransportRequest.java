package edu.wpi.cs3733.C23.teamD.entities;

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
  @Getter @Setter private String startRoom;
  @Getter @Setter private String endRoom;
  @Getter @Setter private String urgency;
  //  private String patientID;

  /*
  PatientTransportData()
  @param String patientID, String endRoom, ArrayList<String> equipment, String reason, String[] sendTo
  @return PatientTransportData object
  creates a PatientTransportData() object to be manipulated
  -- relationship with PatientTransportController: this function is called when
  submit() is called
  */
  public PatientTransportRequest(
      String startRoom, String endRoom, String reason, String sendTo, String urgency) {
    super(sendTo, reason, "PatientTransportData");
    this.endRoom = endRoom;
    this.startRoom = startRoom;
    this.urgency = urgency;
  }

  public PatientTransportRequest(
      int serviceId,
      String startRoom,
      String endRoom,
      String reason,
      String sendTo,
      String urgency,
      Date date) {
    super(serviceId, sendTo, reason, "PatientTransportData", date);
    this.endRoom = endRoom;
    this.startRoom = startRoom;
    this.urgency = urgency;
  }

  public PatientTransportRequest() { // should endRoom be in the constructor
    super();
    this.startRoom = "";
    this.endRoom = "";
    this.urgency = "";
  }

  /*
  printInformation()
  @param void
  @return void
  prints information from PatientTransportData for debugging purposes
  */
  public void printInformation() { // for debugging purposes
    System.out.println("The patientID is " + this.getServiceRequestId());
    System.out.println("The endRoom " + this.endRoom);
    System.out.println("The reason is " + this.getReason());
    System.out.println("sendTo contacts: " + (this.getAssociatedStaff()));
    System.out.println("The startRoom is " + this.startRoom);
  } // end printInformation()

  /*
  getFunctions()
  @param void
  @return String
  each get function returns a String containing information from PatientTransportData object
  */

}
