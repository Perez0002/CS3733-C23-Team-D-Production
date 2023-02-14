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
    ArrayList<Employee> employees = FDdb.getInstance().getAllEmployees();
    for (Employee e : employees) {
      String username = e.getUsername();
      String password = e.getPassword();
      employeeInfo.put(username, password);
    }
    return employeeInfo;
  }

  // Returns a hashtable of username and accessLevels in database
  public Hashtable<String, String> userAccessLevel() {
    Hashtable<String, String> employeeAccessLevel = new Hashtable<String, String>();
    ArrayList<Employee> employees = FDdb.getInstance().getAllEmployees();
    for (Employee e : employees) {
      String accessLevel = e.getEmployeeType();
      employeeAccessLevel.put(username, accessLevel);
    }
    return employeeAccessLevel;
  }

  public boolean setAccessLevel() {
    Hashtable<String, String> employeeInfo = exisitngUserInfo();
    Hashtable<String, String> userAccessLevelInfo = userAccessLevel();
    Set<String> usernames = employeeInfo.keySet();
    // looks through each username if exists, check if correct password
    for (String s : usernames) {
      if (s.equals(username)) {
        if (password.equals(employeeInfo.get(s))) {
          CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
          if (userAccessLevelInfo.get(s).equals("ADMIN")) {
            currentUser.setAccessLevel(2);
            currentUser.setUsername(username);
            return true;
          } else {
            currentUser.setAccessLevel(1);
            return true;
          }
        }
      }
    }
    return false;
  }
}
