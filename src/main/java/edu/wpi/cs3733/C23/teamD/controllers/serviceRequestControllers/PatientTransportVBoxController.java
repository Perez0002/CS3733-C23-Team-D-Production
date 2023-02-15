package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import static edu.wpi.cs3733.C23.teamD.entities.ServiceRequest.Status.DONE;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.PatientTransportRequest;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class PatientTransportVBoxController implements ServiceRequestVBoxController {

  @FXML private VBox patientTransportRequestVBox;

  @FXML private MFXComboBox employeeBox;
  @FXML private MFXComboBox startLocationComboBox;
  @FXML private MFXComboBox endLocationComboBox;
  @FXML private MFXComboBox urgencyBox;
  @FXML private MFXTextField descriptionBox;

  public PatientTransportVBoxController() {}

  public void initialize() {};

  public Node getVBox() {
    return patientTransportRequestVBox;
  }

  public void clearTransportForms() {
    employeeBox.clearSelection();
    startLocationComboBox.clearSelection();
    endLocationComboBox.clearSelection();
    urgencyBox.clearSelection();
    descriptionBox.clear();
  }

  boolean checkFieldsFull() {
    if (employeeBox.getValue() != null
        && startLocationComboBox.getValue() != null
        && endLocationComboBox.getValue() != null
        && urgencyBox.getValue() != null
        && descriptionBox.getText() != null) {
      return true;
    }
    return false;
  }

  public boolean submit() {
    // if the fields are full, go to submit item
    if (checkFieldsFull()) {
      System.out.println(startLocationComboBox.getValue().toString());
      PatientTransportRequest newForm =
          new PatientTransportRequest(
              "PATIENT ID",
              startLocationComboBox.getValue().toString(),
              endLocationComboBox.getValue().toString(),
              "equipment",
              descriptionBox.getText(),
              employeeBox.getValue().toString(),
              DONE);
      FDdb.getInstance().saveServiceRequest(newForm);
      return true;
    }
    // else, display text that says you need to fill fields
    else {
      // TODO: write text that says fields must be full
      return false;
    }
  }
}
