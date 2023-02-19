package edu.wpi.cs3733.C23.teamD.database.controllers;

import javafx.scene.Node;

public interface DatabaseController {

  public void setAddFormController(AddFormController addFormController);

  public void refresh();

  Node getBox();
}
