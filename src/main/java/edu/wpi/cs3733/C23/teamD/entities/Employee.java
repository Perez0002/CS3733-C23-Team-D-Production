package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Employee {
  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int employeeID;

  @Getter @Setter private String employeeType;

  @Getter @Setter private String firstName;

  @Getter @Setter private String lastName;

  @Getter @Setter private String username;

  @Getter @Setter private String password;

  @OneToMany(mappedBy = "staffAssigned")
  private List<ServiceRequest> serviceRequest;

  public Employee() {}

  public Employee(
      int employeeID,
      String employeeType,
      String firstName,
      String lastName,
      String username,
      String password) {
    this.employeeID = employeeID;
    this.employeeType = employeeType;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
  }
}
