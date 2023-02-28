package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.*;
import java.awt.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.Setter;
import org.kordamp.ikonli.javafx.FontIcon;

// order of requests: sanitation, computer, av, security, patient transport
public class AutoGeneratePopupController {

  @FXML private Pane pane;

  @FXML private FontIcon icon1;
  @FXML private FontIcon icon2;
  @FXML private FontIcon icon3;
  @FXML private FontIcon icon4;
  @FXML private FontIcon icon5;

  @FXML private Label requestHeader;

  @Setter private Move move;

  @Setter private MoveRequestTableController moveRequestTableController;
  private ServiceRequestVBoxController currentController;

  private FontIcon currentIcon;

  public void initialize() {
    switchVBox(ServiceRequests.SANITATION_REQUEST);
    currentIcon = icon1;
    requestHeader.setText("Sanitation Request");
  }

  public void fillFields() {
    if (currentController.getClass() == SanitationRequestController.class) {
      SanitationRequestController sanitationRequestController =
          (SanitationRequestController) currentController;
      sanitationRequestController.fillFields(move);
    }
  }

  public Pane getPane() {
    return pane;
  }

  void switchVBox(ServiceRequests switchTo) {
    currentController = NavigationServiceRequests.navigate(switchTo, getPane());
  }

  @FXML
  void resetChanges() {
    moveRequestTableController.closePopOver();
  }

  @FXML
  void submitChanges() throws IOException {
    if (currentController.getClass() == SanitationRequestController.class) {
      SanitationRequestController sanitationRequestController =
          (SanitationRequestController) currentController;
      sanitationRequestController.autoSubmit(move.getMoveDate());
      switchVBox(ServiceRequests.COMPUTER_REQUEST);

      ComputerServiceRequestController computerRequestController =
          (ComputerServiceRequestController) currentController;
      computerRequestController.fillFields(move);
    } else if (currentController.getClass() == ComputerServiceRequestController.class) {
      ComputerServiceRequestController computerRequestController =
          (ComputerServiceRequestController) currentController;
      computerRequestController.autoSubmit(move.getMoveDate());
      switchVBox(ServiceRequests.AV_REQUEST);

      AVRequestController avRequestController = (AVRequestController) currentController;
      avRequestController.fillFields(move);
    } else if (currentController.getClass() == AVRequestController.class) {
      AVRequestController avRequestController = (AVRequestController) currentController;
      avRequestController.autoSubmit(move.getMoveDate());
      switchVBox(ServiceRequests.SECURITY_REQUEST);

      SecurityServiceRequestController securityServiceRequestController =
          (SecurityServiceRequestController) currentController;
      securityServiceRequestController.fillFields(move);
    } else if (currentController.getClass() == SecurityServiceRequestController.class) {
      SecurityServiceRequestController securityServiceRequestController =
          (SecurityServiceRequestController) currentController;
      securityServiceRequestController.autoSubmit(move.getMoveDate());
      switchVBox(ServiceRequests.PATIENT_TRANSPORT);

      PatientTransportVBoxController patientTransportVBoxController =
          (PatientTransportVBoxController) currentController;
      patientTransportVBoxController.fillFields(move);

    } else if (currentController.getClass() == PatientTransportVBoxController.class) {
      PatientTransportVBoxController patientTransportVBoxController =
          (PatientTransportVBoxController) currentController;
      patientTransportVBoxController.autoSubmit(move.getMoveDate());
      moveRequestTableController.closePopOver();
    }
  }

  private void turnCurrentCheck() {
    currentIcon.setIconLiteral("fltral-checkmark-circle-24");
    increaseIcon();
  }

  private void turnCurrentX() {
    currentIcon.setIconLiteral("fltral-dismiss-circle-24");
    increaseIcon();
  }

  private void increaseIcon() {
    if (currentIcon.equals(icon1)) {
      currentIcon = icon2;
      requestHeader.setText("Computer Request");
    } else if (currentIcon.equals(icon2)) {
      currentIcon = icon3;
      requestHeader.setText("A/V Request");
    } else if (currentIcon.equals(icon3)) {
      currentIcon = icon4;
      requestHeader.setText("Security Request");
    } else if (currentIcon.equals(icon4)) {
      currentIcon = icon5;
      requestHeader.setText("Patient Transport Request");
    }
  }
}
