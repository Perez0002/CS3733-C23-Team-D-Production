package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.Setter;

public class AutoGeneratePopupController {

  @FXML private Pane pane;

  @Setter private Move move;

  @Setter private MoveRequestTableController moveRequestTableController;
  private ServiceRequestVBoxController currentController;

  public void initialize() {
    switchVBox(ServiceRequests.SANITATION_REQUEST);
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
}
