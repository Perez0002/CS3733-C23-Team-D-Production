package edu.wpi.cs3733.C23.teamD.entities;

public class SanitationRequestData {

  // attributes
  int sanitationRequestID;
  String location;
  String reason;
  int bioLevel;

  String staff;

  public enum Status {
    BLANK,
    PROCESSING,
    DONE;
  }

  private SanitationRequestData.Status stat;

  public String getLocation() {
    return location;
  }

  public String getReason() {
    return reason;
  }

  public int getBioLevel() {
    return bioLevel;
  }

  public SanitationRequestData(
      int formID, String location, String reason, int bioLevel, String staff, Status stat) {
    sanitationRequestID = formID;
    this.location = location;
    this.reason = reason;
    this.bioLevel = bioLevel;
    this.staff = staff;
    this.stat = stat;
  }

  public SanitationRequestData() {
    this.sanitationRequestID = 0;
    this.location = "";
    this.reason = "";
    this.bioLevel = 0;
    this.staff = "";
    this.stat = Status.BLANK;
  }

  // for debugging
  public void printSanititationInfo() {
    System.out.println(
        "location: "
            + this.location
            + "\nreason: "
            + this.reason
            + "\nBio Hazard Level: "
            + this.bioLevel);
  }

  public int getSanitationRequestID() {
    return sanitationRequestID;
  }

  public void setSanitationRequestID(int sanitationRequestID) {
    this.sanitationRequestID = sanitationRequestID;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public void setBioLevel(int bioLevel) {
    this.bioLevel = bioLevel;
  }

  public String getStaff() {
    return staff;
  }

  public void setStaff(String staff) {
    this.staff = staff;
  }

  public Status getStat() {
    return stat;
  }

  public void setStat(Status stat) {
    this.stat = stat;
  }
}
