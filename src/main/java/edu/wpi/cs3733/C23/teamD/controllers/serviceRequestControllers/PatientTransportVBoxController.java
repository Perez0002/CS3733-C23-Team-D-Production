package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class PatientTransportVBoxController extends ServiceRequestVBoxController {

  @FXML private VBox patientTransportRequestVBox;

  public PatientTransportVBoxController() {}

  public void initialize() {};

  public Node getVBox() {
    return patientTransportRequestVBox;
  }

  public void setVisible() {
    if (patientTransportRequestVBox.isVisible()) {
      patientTransportRequestVBox.setVisible(false);
    } else {
      patientTransportRequestVBox.setVisible(true);
    }
  }
}
