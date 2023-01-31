package edu.wpi.cs3733.C23.teamD.entities;

public class SanitationRequestData {

  // attributes
  String location;
  String reason;
  int bioLevel;

  String staff;

  public enum status {
    BLANK,
    PROCESSING,
    DONE;
  }

  private SanitationRequestData.status stat;

  public String getLocation() {
    return location;
  }

  public String getReason() {
    return reason;
  }

  public int getBioLevel() {
    return bioLevel;
  }

  public SanitationRequestData(String location, String reason, int bioLevel, String staff, status stat) {
    this.location = location;
    this.reason = reason;
    this.bioLevel = bioLevel;
    this.staff = staff;
    this.stat = stat;
  }

  public SanitationRequestData() {
    this.location = "";
    this.reason = "";
    this.bioLevel = 0;
    this.staff = "";
    this.stat = status.BLANK;
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
}
