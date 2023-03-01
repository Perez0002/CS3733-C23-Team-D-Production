package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.NavigationServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequests;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.detailsControllers.*;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.*;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.Setter;

public class RequestDetailsPopupController {

  @FXML private Pane pane;

  private RequestDetailsController currentController;

  @Setter MoveDisplayContainerController moveDisplayContainerController;

  @Setter ArrayList<ServiceRequest> serviceRequests;

  @Setter Move move;

  private int page = 0;

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

  public void setFields(int i) {
    if (serviceRequests.size() > i && i >= 0) {
      System.out.println("set fields");
      ServiceRequest s = serviceRequests.get(i);
      if (s.getClass() == SanitationRequest.class) {
        System.out.println("set sanitaiton fields");
        currentController =
            NavigationServiceRequests.navigateHomepage(
                ServiceRequests.SANITATION_REQUEST_DETAILS, getPane(), new ServiceRequest());
        SanitationRequestDetailsController sanitationRequestDetailsController =
            (SanitationRequestDetailsController) currentController;
        sanitationRequestDetailsController.setFields(serviceRequests.get(i));
      } else if (s.getClass() == SecurityServiceRequest.class) {
        System.out.println("set sanitaiton fields");
        currentController =
            NavigationServiceRequests.navigateHomepage(
                ServiceRequests.SECURITY_REQUEST_DETAILS, getPane(), new ServiceRequest());
        SecurityRequestDetailsController securityRequestDetailsController =
            (SecurityRequestDetailsController) currentController;
        securityRequestDetailsController.setFields(serviceRequests.get(i));
      } else if (s.getClass() == AVRequest.class) {
        System.out.println("set sanitaiton fields");
        currentController =
            NavigationServiceRequests.navigateHomepage(
                ServiceRequests.AV_REQUEST_DETAILS, getPane(), new ServiceRequest());
        AVRequestDetailsController avRequestDetailsController =
            (AVRequestDetailsController) currentController;
        avRequestDetailsController.setFields(serviceRequests.get(i));
      } else if (s.getClass() == ComputerServiceRequest.class) {
        System.out.println("set sanitaiton fields");
        currentController =
            NavigationServiceRequests.navigateHomepage(
                ServiceRequests.COMPUTER_REQUEST_DETAILS, getPane(), new ServiceRequest());
        ComputerRequestDetailsController computerRequestDetailsController =
            (ComputerRequestDetailsController) currentController;
        computerRequestDetailsController.setFields(serviceRequests.get(i));
      } else if (s.getClass() == PatientTransportRequest.class) {
        System.out.println("set sanitaiton fields");
        currentController =
            NavigationServiceRequests.navigateHomepage(
                ServiceRequests.PATIENT_TRANSPORT_DETAILS, getPane(), new ServiceRequest());
        TransportRequestDetailsController transportRequestDetailsController =
            (TransportRequestDetailsController) currentController;
        transportRequestDetailsController.setFields(serviceRequests.get(i));
      }
    }
  }

  @FXML
  public void left() {
    if (page > 0) {
      page--;
      setFields(page);
    }
  }

  @FXML
  public void right() {
    if (page < serviceRequests.size() - 1) {
      page++;
      setFields(page);
    }
  }
}
