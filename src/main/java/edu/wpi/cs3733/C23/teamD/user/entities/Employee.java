package edu.wpi.cs3733.C23.teamD.user.entities;

import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Employee implements Comparable<Employee> {
  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int employeeID;

  @Getter @Setter private String email;

  @Getter @Setter private String employeeType;

  @Getter @Setter private String firstName;

  @Getter @Setter private String lastName;

  @Getter @Setter private String password;

  @Getter @Setter @CreationTimestamp private Date accountCreated;

  @Getter @Setter private String phoneNumber;

  @Getter @Setter private Date Birthday;

  @Getter @Setter private String address;

  @Getter
  @Setter
  @OneToMany(mappedBy = "associatedStaff")
  private List<ServiceRequest> serviceRequest;

  @Getter
  @Setter
  @OneToMany(mappedBy = "staffAssigning")
  private List<ServiceRequest> serviceRequests;

  public Employee() {}

  @Override
  public int compareTo(Employee o) {
    // implement comparison here
    return 0;
  }

  public Employee(
      int employeeID,
      String employeeType,
      String firstName,
      String lastName,
      String password,
      String email) {
    this.employeeID = employeeID;
    this.employeeType = employeeType;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
  }

  public Employee(
      int employeeID,
      String employeeType,
      String firstName,
      String lastName,
      String password,
      String email,
      String phoneNumber,
      Date birthday,
      String address) {
    this.employeeID = employeeID;
    this.employeeType = employeeType;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.Birthday = birthday;
    this.address = address;
  }
}
