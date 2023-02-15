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

  // Returns a hashtable of employee information and username
  public Hashtable<Employee, String> exisitngUserInfo() {
    Hashtable<Employee, String> employeeInfo = new Hashtable<Employee, String>();
    ArrayList<Employee> employees = FDdb.getInstance().getAllEmployees();
    for (Employee e : employees) {
      String username = e.getUsername();
      employeeInfo.put(e, username);
    }
    return employeeInfo;
  }

  public boolean setAccessLevel() {
    Hashtable<Employee, String> employeeInfo = exisitngUserInfo();
    Set<Employee> employee = employeeInfo.keySet();

    // looks through each username if exists, check if correct password
    for (Employee s : employee) {
      if (s.getUsername().equals(username)) {
        if (s.getPassword().equals(password)) {
          CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
          if (s.getEmployeeType().equals("ADMIN")) {
            currentUser.setAccessLevel(2);
            setCurrentUserInfo(s, currentUser);
          } else {
            currentUser.setAccessLevel(1);
            setCurrentUserInfo(s, currentUser);
          }
          return true;
        }
      }
    }
    return false;
  }

  private void setCurrentUserInfo(Employee s, CurrentUser currentUser) {
    currentUser.setUsername(username);
    currentUser.setFirstName(s.getFirstName());
    currentUser.setLastName(s.getLastName());
    currentUser.setEmail(s.getEmail());
    currentUser.setPhoneNumber(s.getPhoneNumber());
    currentUser.setAddress(s.getAddress());
    currentUser.setBirthday(s.getBirthday());
  }
}
