package edu.wpi.cs3733.C23.teamD.servicerequest.util;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class LoginChecker {
  private String email;
  private String password;
  private int accessLevel;

  public LoginChecker(String email, String password) {
    this.email = email;
    this.password = password;
  }

  // Returns a hashtable of employee information and email
  public Hashtable<Employee, String> exisitngUserInfo() {
    Hashtable<Employee, String> employeeInfo = new Hashtable<Employee, String>();
    ArrayList<Employee> employees = FDdb.getInstance().getAllEmployees();
    for (Employee e : employees) {
      String username = e.getEmail();
      employeeInfo.put(e, username);
    }
    return employeeInfo;
  }

  public boolean setAccessLevel() {
    Hashtable<Employee, String> employeeInfo = exisitngUserInfo();
    Set<Employee> employee = employeeInfo.keySet();

    // looks through each username if exists, checks if correct password, checks the employeetype
    // for access
    for (Employee s : employee) {
      if (s.getEmail().equals(email)) {
        if (s.getPassword().equals(password)) {
          CurrentUserEnum._CURRENTUSER.setCurrentUser(s);
          return true;
        }
      }
    }
    return false;
  }
}
