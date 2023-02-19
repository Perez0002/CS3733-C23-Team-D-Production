package edu.wpi.cs3733.C23.teamD.database.controllers;

import lombok.Getter;
import lombok.Setter;

public abstract class AddFormController {

  @Getter @Setter private DatabaseHubController databaseHubController;

  public abstract void switchToAdd(boolean b);
}
