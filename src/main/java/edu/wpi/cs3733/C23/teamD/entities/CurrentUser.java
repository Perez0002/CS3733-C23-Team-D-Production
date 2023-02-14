package edu.wpi.cs3733.C23.teamD.entities;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

public class CurrentUser {

  @Getter @Setter private String username;

  @Getter @Setter private int accessLevel;

  @Getter @Setter private String employeeType;

  @Getter @Setter private String firstName;

  @Getter @Setter private String lastName;

  @Getter @Setter private String email;

  @Getter @Setter private String password;

  @Getter @Setter private Date accountCreated;

  @Getter @Setter private String phoneNumber;

  @Getter @Setter private Date Birthday;

  @Getter @Setter private String address;

  public CurrentUser() {}
}
