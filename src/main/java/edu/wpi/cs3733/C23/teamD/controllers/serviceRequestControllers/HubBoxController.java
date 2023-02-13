package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HubBoxController {

  @FXML private VBox serviceRequestHubVBox;

  public HubBoxController() {}

  public VBox getVBox() {
    return serviceRequestHubVBox;
  }

  public void initialize() {};

  public void setVisible() {
    if (serviceRequestHubVBox.isVisible()) {
      serviceRequestHubVBox.setVisible(false);
    } else {
      serviceRequestHubVBox.setVisible(true);
    }
  }
}
