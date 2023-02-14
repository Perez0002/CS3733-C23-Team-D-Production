package edu.wpi.cs3733.C23.teamD.entities;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class LoginChecker {
  private String username;
  private String password;

  private int accessLevel;

  public LoginChecker(String username, String password) {
    this.username = username;
    this.password = password;
  }

  // Returns a hashtable of username and passwords in database
  public Hashtable<String, String> exisitngUserInfo() {
    Hashtable<String, String> employeeInfo = new Hashtable<String, String>();
    FDdb.getInstance();
    ArrayList<Employee> employees = FDdb.getInstance().getAllEmployees();
    for (Employee e : employees) {
      String username = e.getUsername();
      String password = e.getPassword();
      employeeInfo.put(username, password);
    }
    return employeeInfo;
  }

  public boolean setAccessLevel() {
    Hashtable<String, String> employeeInfo = exisitngUserInfo();
    Set<String> usernames = employeeInfo.keySet();
    // looks through each username if exists, check if correct password
    for (String s : usernames) {
      if (s.equals(username)) {
        if (password.equals(employeeInfo.get(s))) {
          CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
          currentUser.setAccessLevel(1);
          currentUser.setUsername(username);
          return true;
        }
      }
    }
    if (username.equals("staff") && password.equals("password")) {
      CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
      currentUser.setAccessLevel(1);
      currentUser.setUsername(username);
      return true;
    }
    if (username.equals("admin") && password.equals("password")) {
      CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
      currentUser.setAccessLevel(2);
      currentUser.setUsername(username);
      return true;
    } else {
      return false;
    }
  }
}
