package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import javafx.scene.layout.VBox;

public class ServiceRequestVBoxController {

  private VBox vbox;

  void initialize() {}

  public void setVisible() {
    if (vbox.isVisible()) {
      vbox.setVisible(false);
    } else {
      vbox.setVisible(true);
    }
  }
}
