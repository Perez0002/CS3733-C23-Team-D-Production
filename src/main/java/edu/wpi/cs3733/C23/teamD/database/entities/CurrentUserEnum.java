package edu.wpi.cs3733.C23.teamD.database.entities;

import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.user.entities.Setting;

public enum CurrentUserEnum {
  _CURRENTUSER;

  private Employee currentUser = new Employee();
  private Setting currentSetting = new Setting();

  public Employee getCurrentUser() {
    return currentUser;
  }

  public Setting getSetting() {
    return currentSetting;
  }

  public void setCurrentSetting(Setting currentSetting) {
    this.currentSetting = currentSetting;
  }

  public void setCurrentUser(Employee currentUser) {
    this.currentUser = currentUser;
  }
}
