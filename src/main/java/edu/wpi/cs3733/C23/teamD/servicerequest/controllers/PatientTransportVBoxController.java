package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.PatientTransportRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class PatientTransportVBoxController implements ServiceRequestVBoxController {
  @FXML private VBox patientTransportRequestVBox;
  @FXML private Parent employeeComboBox;
  @FXML private EmployeeDropdownComboBoxController employeeComboBoxController;
  @FXML private Parent startingLocation;
  @FXML private LocationComboBoxController startingLocationController;
  @FXML private Parent endLocationComboBox;
  @FXML private LocationComboBoxController endLocationComboBoxController;
  @FXML private MFXComboBox urgencyBox;
  @FXML private MFXTextField descriptionBox;

  public PatientTransportVBoxController() {}

  public void initialize() {
    startingLocationController
        .giveComboBox()
        .setOnAction(
            event -> ServiceRequestMap.getMapSingleton().mapCenters(startingLocationController));
    endLocationComboBoxController
        .giveComboBox()
        .setOnAction(
            event -> ServiceRequestMap.getMapSingleton().mapCenters(endLocationComboBoxController));
  };

  public Node getVBox() {
    return patientTransportRequestVBox;
  }

  public void clearTransportForms() {
    employeeComboBoxController.clearForm();
    startingLocationController.clearForm();
    endLocationComboBoxController.clearForm();
    urgencyBox.clearSelection();
    descriptionBox.clear();
  }

  boolean checkFieldsFull() {
    if (employeeComboBoxController.getEmployeeName() != null
        && startingLocationController.getLocationLongName() != null
        && endLocationComboBoxController.getLocationLongName() != null
        && urgencyBox.getValue() != null
        && descriptionBox.getText() != null) {
      return true;
    }
    return false;
  }

  public boolean submit() {
    // if the fields are full, go to submit item
    if (checkFieldsFull()) {
      PatientTransportRequest newForm =
          new PatientTransportRequest(
              endLocationComboBoxController.getLocationLongName(),
              descriptionBox.getText(),
              employeeComboBoxController.getEmployee(),
              urgencyBox.getValue().toString(),
              startingLocationController.getLocation());
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
