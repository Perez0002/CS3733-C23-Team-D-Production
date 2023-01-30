package edu.wpi.cs3733.C23.teamD.entities;

public class SanitationRequestData {

  // attributes
  String location;
  String reason;
  int bioLevel;

  public String getLocation() {
    return location;
  }

  public String getReason() {
    return reason;
  }

  public int getBioLevel() {
    return bioLevel;
  }

  public SanitationRequestData(String location, String reason, int bioLevel) {
    this.location = location;
    this.reason = reason;
    this.bioLevel = bioLevel;
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
}
