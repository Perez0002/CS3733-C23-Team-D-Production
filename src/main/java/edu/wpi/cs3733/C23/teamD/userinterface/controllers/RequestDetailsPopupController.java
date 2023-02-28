package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.NavigationServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.detailsControllers.RequestDetailsController;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.SanitationRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class RequestDetailsPopupController {

  @FXML private Pane pane;

  private RequestDetailsController currentController;

  public void initialize() {
    switchVBox(ServiceRequests.SANITATION_REQUEST_DETAILS);
  }

  public Pane getPane() {
    return pane;
  }

  void switchVBox(ServiceRequests switchTo) {
    currentController =
        NavigationServiceRequests.navigateHomepage(switchTo, getPane(), new ServiceRequest());
  }

  public void setfields(SanitationRequest sanitationRequest) {
    if (sanitationRequest != null) {
      currentController.setFields(sanitationRequest);
    }
  }
}
