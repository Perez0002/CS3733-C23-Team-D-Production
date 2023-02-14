package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HubBoxController implements DatabaseVBoxController {

  @FXML private VBox databaseHubVBox;

  public HubBoxController() {}

  public VBox getVBox() {
    return databaseHubVBox;
  }

  public void initialize() {};

  public void setVisible() {
    if (databaseHubVBox.isVisible()) {
      databaseHubVBox.setVisible(false);
    } else {
      databaseHubVBox.setVisible(true);
    }
  }
}
