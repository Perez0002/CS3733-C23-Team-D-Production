package edu.wpi.cs3733.C23.teamD.entities;

public enum CurrentUserEnum {
  _CURRENTUSER;

  CurrentUser currentUser = new CurrentUser();

  public CurrentUser getCurrentUser() {
    return currentUser;
  }
}
