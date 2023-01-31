package edu.wpi.cs3733.C23.teamD.entities;

import java.util.ArrayList;
import java.util.Arrays;

/*
PatientTransportData
creates an entity object containing data for use on frontend PatientTransport UI
*/
public class PatientTransportData {

  // Attributes of PatientTransportData class
  private int patientTransportID;
  private String startRoom;
  private String endRoom;
  private ArrayList<String> equipment; // equipment necessary based on form contents
  private String reason;
  private String[] sendTo; // individuals to notify based on form contents

  private String patientID;


  public enum status {
    BLANK,
    PROCESSING,
    DONE;
  }

  private status stat;

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
      int patientTransportID,
      String startRoom,
      String endRoom,
      ArrayList<String> equipment,
      String reason,
      String[] sendTo,
      status stat) {
    this.patientID = patientID;
    this.patientTransportID = patientTransportID;
    this.endRoom = endRoom;
    this.equipment = equipment;
    this.reason = reason;
    this.sendTo = sendTo;
    this.startRoom = startRoom;
    this.stat = stat;
  }

  public PatientTransportData() { // should endRoom be in the constructor
    this.patientTransportID = 0;
    this.endRoom = null;
    this.equipment = null;
    this.reason = null;
    this.sendTo = null;
    this.startRoom = "";
    this.stat = status.BLANK;
  }
  /*
  printInformation()
  @param void
  @return void
  prints information from PatientTransportData for debugging purposes
  */
  public void printInformation() { // for debugging purposes
    System.out.println("The patientID is " + this.patientTransportID);
    System.out.println("The endRoom " + this.endRoom);
    System.out.println("The equipment required is " + this.equipment);
    System.out.println("The reason is " + this.reason);
    System.out.println("sendTo contacts: " + Arrays.toString(this.sendTo));
    System.out.println("The startRoom is " + this.startRoom);
  } // end printInformation()

  /*
  getFunctions()
  @param void
  @return String
  each get function returns a String containing information from PatientTransportData object
  */

  public int getPatientTransportID() {
    return patientTransportID;
  } // end getPatientID()

  public String getStartRoom() {
    return startRoom;
  }

  public ArrayList<String> getEquipment() {
    return equipment;
  }

  public String getReason() {
    return reason;
  }

  public String[] getSendTo() {
    return sendTo;
  }

  public void setPatientTransportID(int patientTransportID) {
    this.patientTransportID = patientTransportID;
  }

  public void setStartRoom(String startRoom) {
    this.startRoom = startRoom;
  }

  public void setEndRoom(String endRoom) {
    this.endRoom = endRoom;
  }

  public void setEquipment(ArrayList<String> equipment) {
    this.equipment = equipment;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public void setSendTo(String[] sendTo) {
    this.sendTo = sendTo;
  }

  public status getStat() {
    return stat;
  }

  public void setStat(status stat) {
    this.stat = stat;
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
