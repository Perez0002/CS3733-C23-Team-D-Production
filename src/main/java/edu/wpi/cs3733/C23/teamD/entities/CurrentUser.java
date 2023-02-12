package edu.wpi.cs3733.C23.teamD.entities;

public class CurrentUser {

  private String username;

  private int accessLevel;

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
