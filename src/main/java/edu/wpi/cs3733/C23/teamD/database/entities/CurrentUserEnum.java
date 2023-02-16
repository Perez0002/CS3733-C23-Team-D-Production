package edu.wpi.cs3733.C23.teamD.database.entities;

import edu.wpi.cs3733.C23.teamD.user.entities.Employee;

public enum CurrentUserEnum {
  _CURRENTUSER;

  private Employee currentUser = new Employee();

  public Employee getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(Employee currentUser) {
    this.currentUser = currentUser;
  }
}
