package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class SanitationRequestData extends ServiceRequestForm {
  // attributes
  String location;
  int bioLevel;

  public String getLocation() {
    return location;
  }

  public int getBioLevel() {
    return bioLevel;
  }

  public SanitationRequestData(
      String location, String reason, int bioLevel, String staff, Status stat) {
    super(staff, stat, reason, "SanitationRequestData");
    this.location = location;
    this.bioLevel = bioLevel;
  }

  public SanitationRequestData(
      int serviceId,
      String location,
      String reason,
      int bioLevel,
      String staff,
      Status stat,
      Date date) {
    super(serviceId, staff, stat, reason, "SanitationRequestData", date);
    this.location = location;
    this.bioLevel = bioLevel;
  }

  public SanitationRequestData() {
    super();
    this.location = "";
    this.bioLevel = 0;
  }

  // for debugging
  public void printSanititationInfo() {
    System.out.println("location: " + this.location + "\nBio Hazard Level: " + this.bioLevel);
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setBioLevel(int bioLevel) {
    this.bioLevel = bioLevel;
  }
}
