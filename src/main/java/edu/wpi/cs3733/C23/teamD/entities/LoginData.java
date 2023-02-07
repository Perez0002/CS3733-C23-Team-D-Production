package edu.wpi.cs3733.C23.teamD.entities;

public class LoginData {
  private String username;
  private String password;

  private int accessLevel;



  public LoginData(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public int getAccessLevel() {
    return accessLevel;
  }

  public boolean setAccessLevel() {
    if (username.equals("username") && password.equals("password")) {
      accessLevel = 1;
      return true;
    } else if (username.equals("admin") && password.equals("password")) {
      accessLevel = 2;
      return true;
    } else {
      return false;
    }
  }

  public void printInformation() {
    System.out.println("The patientID is " + this.username);
    System.out.println("The endRoom " + this.password);
  }
}
