package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.PatientTransportRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.RoomPickComboBoxController;
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
  @FXML private RoomPickComboBoxController endLocationComboBoxController;
  @FXML private MFXComboBox urgencyBox;
  @FXML private MFXTextField descriptionBox;

  public PatientTransportVBoxController() {}

  public void initialize() {};

  public Node getVBox() {
    return patientTransportRequestVBox;
  }

  @Override
  public void setFieldsDisable(ServiceRequest serviceRequest) {
    endLocationComboBoxController.setDisable(true);
    descriptionBox.setDisable(true);
    employeeComboBoxController.setDisable(true);
    urgencyBox.setDisable(true);
    startingLocationController.setDisable(true);

    // TODO fix/check employeebox and also test the start and end locations are correct
    if (serviceRequest.getClass().equals(PatientTransportRequest.class)) {
      endLocationComboBoxController.setLocationName(serviceRequest.getLocation().getLongName());
      descriptionBox.setText(serviceRequest.getReason());
      // employeeComboBoxController.setText();
      urgencyBox.setText(serviceRequest.getUrgency());
      startingLocationController.setLocationName(serviceRequest.getLocation().getLongName());
    }
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
        && endLocationComboBoxController.getLocationName() != null
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
              endLocationComboBoxController.getLocationName(),
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
