package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.NavigationServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequestVBoxController;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequests;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RequestDetailsPopupController {

  @FXML private Pane pane;

  private ServiceRequestVBoxController currentController;

  public void initialize() {
    switchVBox(ServiceRequests.SANITATION_REQUEST);
  }

  public Pane getPane() {
    return pane;
  }

  void switchVBox(ServiceRequests switchTo) {
    currentController = NavigationServiceRequests.navigate(switchTo, getPane());
  }
}
