package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.AVRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class AVRequestController implements ServiceRequestVBoxController {
  @FXML private Parent employeeBox;
  @FXML private TextField descriptionTextField, systemFailureTextField;

  @FXML private MFXDatePicker datePicker;

  @FXML private EmployeeDropdownComboBoxController employeeBoxController;

  public void clearTransportForms() {
    employeeBoxController.clearForm();
    systemFailureTextField.clear();
    descriptionTextField.clear();
  }

  public boolean submit() {
    if (checkFields()) {
      AVRequest request =
          new AVRequest(
              employeeBoxController.getEmployeeName(),
              descriptionTextField.getText(),
              "AVRequest",
              systemFailureTextField.getText(),
              datePicker.getValue());
      FDdb.getInstance().saveServiceRequest(request);

      return true;
    } else {
      return false;
    }
  }

  private boolean checkFields() {
    return !(descriptionTextField.getText().isEmpty()
        || employeeBoxController.getEmployeeName().isEmpty()
        || systemFailureTextField.getText().isEmpty());
  }

  public Node getVBox() {
    return null;
  }
}
