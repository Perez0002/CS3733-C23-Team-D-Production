package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HubBoxController implements DatabaseController {

  @FXML private VBox databaseHubBox;

  public HubBoxController() {}

  public VBox getBox() {
    return databaseHubBox;
  }

  public void initialize() {};

  public void setVisible() {
    if (databaseHubBox.isVisible()) {
      databaseHubBox.setVisible(false);
    } else {
      databaseHubBox.setVisible(true);
    }
  }
}
