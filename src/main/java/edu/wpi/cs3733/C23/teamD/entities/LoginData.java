package edu.wpi.cs3733.C23.teamD.entities;

public class LoginData {
  private String username;
  private String password;

  public enum Status {
    BLANK,
    PROCESSING,
    DONE;
  }

  private Status stat;

  public LoginData(String username, String password, Status stat) {
    this.username = username;
    this.password = password;
    this.stat = stat;
  }

  public void printInformation() {
    System.out.println("The patientID is " + this.username);
    System.out.println("The endRoom " + this.password);
    System.out.println("The equipment required is " + this.stat);
  }
}
