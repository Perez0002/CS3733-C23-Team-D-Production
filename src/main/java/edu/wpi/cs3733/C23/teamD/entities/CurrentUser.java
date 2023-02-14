package edu.wpi.cs3733.C23.teamD.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class CurrentUser {

  private String username;

  private int accessLevel;

  private String employeeType;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private Date accountCreated;

  @Getter @Setter private String phoneNumber;

  @Getter @Setter private Date Birthday;

  @Getter @Setter private String address;


  public CurrentUser() {}

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setAccessLevel(int accessLevel) {
    this.accessLevel = accessLevel;
  }

  public int getAccessLevel() {
    return accessLevel;
  }
}
