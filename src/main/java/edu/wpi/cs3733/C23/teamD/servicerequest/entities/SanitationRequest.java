package edu.wpi.cs3733.C23.teamD.servicerequest.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class SanitationRequest extends ServiceRequest {
  // attributes
  String location;
  int bioLevel;

  public String getLocation() {
    return location;
  }

  public int getBioLevel() {
    return bioLevel;
  }

  public SanitationRequest(String location, String reason, int bioLevel, String staff) {
    super(staff, reason, "SanitationRequestData");
    this.location = location;
    this.bioLevel = bioLevel;
  }

  public SanitationRequest(
      int serviceId, String location, String reason, int bioLevel, String staff, Date date) {
    super(serviceId, staff, reason, "SanitationRequestData", date);
    this.location = location;
    this.bioLevel = bioLevel;
  }

  public SanitationRequest() {
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
