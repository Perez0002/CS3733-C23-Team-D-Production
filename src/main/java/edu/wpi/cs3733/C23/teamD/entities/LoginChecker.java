package edu.wpi.cs3733.C23.teamD.entities;

public class LoginChecker {
  private String username;
  private String password;

  private int accessLevel;

  public LoginChecker(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public boolean setAccessLevel() {
    // TODO add database implementation.
    if (username.equals("username") && password.equals("password")) {
      CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
      currentUser.setAccessLevel(1);
      currentUser.setUsername(username);
      return true;
    } else if (username.equals("admin") && password.equals("password")) {
      CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
      currentUser.setAccessLevel(2);
      currentUser.setUsername(username);
      return true;
    } else {
      return false;
    }
  }
}
