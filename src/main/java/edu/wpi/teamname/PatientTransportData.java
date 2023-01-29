package edu.wpi.teamname;

import java.util.ArrayList;
import java.util.Arrays;

/*
PatientTransportData
creates an entity object containing data for use on frontend PatientTransport UI
*/
public class PatientTransportData {

    // Attributes of PatientTransportData class
    String patientID;
    String startRoom;
    String endRoom;
    ArrayList<String> equipment; // equipment necessary based on form contents
    String reason;
    String[] sendTo; // individuals to notify based on form contents

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
            String endRoom,
            ArrayList<String> equipment,
            String reason,
            String[] sendTo) { // should endRoom be in the constructor
        this.patientID = patientID;
        this.endRoom = endRoom; // how are we getting the startRoom
        this.equipment = equipment;
        this.reason = reason;
        this.sendTo = sendTo;
        this.startRoom = "test"; // for debugging purposes
    } // end PatientTransportData()
    public PatientTransportData() { // should endRoom be in the constructor
        this.patientID = null;
        this.endRoom = null; // how are we getting the startRoom
        this.equipment = null;
        this.reason = null;
        this.sendTo = null;
        this.startRoom = ""; // for debugging purposes
    }
    /*
    printInformation()
    @param void
    @return void
    prints information from PatientTransportData for debugging purposes
    */
    public void printInformation() { // for debugging purposes
        System.out.println("The patientID is " + this.patientID);
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

    public String getPatientID() {
        return patientID;
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

    public void setPatientID(String patientID) {
        this.patientID = patientID;
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
}