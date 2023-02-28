package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.LabRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;

public class LabRequestController implements ServiceRequestVBoxController {
  @FXML private Parent employeeBox;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;

  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;

  @FXML private MFXComboBox urgencyBox;

  @FXML private MFXTextField descriptionTextField;

  @FXML private MFXComboBox LabTypeBox;

  public void clearTransportForms() {
    employeeBoxController.clearForm();
    locationBoxController.clearForm();
    urgencyBox.clearSelection();
    LabTypeBox.clear();
    descriptionTextField.clear();
  }

  public boolean submit() {
    if (checkFields()) {
      LabRequest request =
          new LabRequest(
              employeeBoxController.getEmployee(),
              descriptionTextField.getText(),
              "LabRequest",
              LabTypeBox.getValue().toString(),
              locationBoxController.getLocation(),
              urgencyBox.getValue().toString());
      FDdb.getInstance().saveServiceRequest(request);
      return true;
    } else {
      return false;
    }
  }

  private boolean checkFields() {
    return !(employeeBoxController.getEmployeeName().isEmpty()
            || descriptionTextField.getText().isEmpty()
            || urgencyBox.getText().isEmpty()
            || locationBoxController.getLocationLongName().isEmpty())
        || LabTypeBox.getValue().toString().isEmpty();
  }

  public Node getVBox() {
    return null;
  }

  @Override
  public void setFieldsDisable(ServiceRequest serviceRequest) {
    urgencyBox.setDisable(true);
    LabTypeBox.setDisable((true));
    descriptionTextField.setDisable(true);
    locationBoxController.setDisable(true);
    employeeBoxController.setDisable(true);
    if (serviceRequest.getClass().equals(LabRequest.class)) {
      urgencyBox.setText(serviceRequest.getUrgency());
      LabTypeBox.setText(serviceRequest.getDateAndTime().toString());
      descriptionTextField.setText(serviceRequest.getReason());
      locationBoxController.setText(serviceRequest.getLocation().getLongName());
      employeeBoxController.setText(
          serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
    }
  }
}
