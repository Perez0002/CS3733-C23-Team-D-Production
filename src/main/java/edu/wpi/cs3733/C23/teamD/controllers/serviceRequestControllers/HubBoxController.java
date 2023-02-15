package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HubBoxController implements ServiceRequestVBoxController {

  @FXML private VBox serviceRequestHubVBox;

  public HubBoxController() {}

  @Override
  public void clearTransportForms() {}

  @Override
  public boolean submit() {
    return false;
  }

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
