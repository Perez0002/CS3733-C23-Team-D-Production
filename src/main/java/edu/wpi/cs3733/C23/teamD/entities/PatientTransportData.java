package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.type.*;

/*
PatientTransportData
creates an entity object containing data for use on frontend PatientTransport UI
*/

@Entity
public class PatientTransportData extends ServiceRequestForm {

  // Attributes of PatientTransportData class

  private String startRoom;
  private String endRoom;
  private String equipment; // equipment necessary based on form contents
  private String patientID;

  /*
  PatientTransportData()
  @param String patientID, String endRoom, ArrayList<String> equipment, String reason, String[] sendTo
  @return PatientTransportData object
  creates a PatientTransportData() object to be manipulated
  -- relationship with PatientTransportController: this function is called when
  submit() is called
  */
  public PatientTransportData(
      String patientID,
      String startRoom,
      String endRoom,
      String equipment,
      String reason,
      String sendTo,
      Status stat) {
    super(sendTo, stat, reason, "PatientTransportData");
    this.patientID = patientID;
    this.endRoom = endRoom;
    this.equipment = equipment;
    this.startRoom = startRoom;
  }

  public PatientTransportData(
      int serviceId,
      String patientID,
      String startRoom,
      String endRoom,
      String equipment,
      String reason,
      String sendTo,
      Status stat,
      Date date) {
    super(serviceId, sendTo, stat, reason, "PatientTransportData", date);
    this.patientID = patientID;
    this.endRoom = endRoom;
    this.equipment = equipment;
    this.startRoom = startRoom;
  }

  public PatientTransportData() { // should endRoom be in the constructor
    super();
    this.endRoom = null;
    this.equipment = null;
    this.startRoom = "";
    this.endRoom = "";
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
    System.out.println("The equipment required is " + this.equipment);
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

  public String getStartRoom() {
    return startRoom;
  }

  public String getEquipment() {
    return equipment;
  }

  public void setStartRoom(String startRoom) {
    this.startRoom = startRoom;
  }

  public void setEndRoom(String endRoom) {
    this.endRoom = endRoom;
  }

  public void setEquipment(String equipment) {
    this.equipment = equipment;
  }

  public String getEndRoom() {
    return endRoom;
  }

  public String getPatientID() {
    return patientID;
  }

  public void setPatientID(String patientID) {
    this.patientID = patientID;
  }
}
