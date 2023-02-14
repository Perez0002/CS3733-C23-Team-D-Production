package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
public class SanitationRequest extends ServiceRequest {
  // attributes
  @Getter @Setter String location;
  @Getter @Setter int bioLevel;

  public SanitationRequest(
      String location, String reason, int bioLevel, String staff, Status stat) {
    super(staff, stat, reason, "SanitationRequestData");
    this.location = location;
    this.bioLevel = bioLevel;
  }

  public SanitationRequest(
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

  public SanitationRequest() {
    super();
    this.location = "";
    this.bioLevel = 0;
  }

  // for debugging
  public void printSanititationInfo() {
    System.out.println("location: " + this.location + "\nBio Hazard Level: " + this.bioLevel);
  }
}
