package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.AVRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class AVRequestController implements ServiceRequestVBoxController {
  @FXML private Parent employeeBox;
  @FXML private TextField descriptionTextField, systemFailureTextField;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;
  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;
  @FXML private MFXComboBox urgencyBox;
  @FXML private MFXDatePicker datePicker;

  public void clearTransportForms() {
    employeeBoxController.clearForm();
    locationBoxController.clearForm();
    urgencyBox.clearSelection();
    datePicker.clear();
    systemFailureTextField.clear();
  }

  public boolean submit() {
    if (checkFields()) {
      AVRequest request =
          new AVRequest(
              employeeBoxController.getEmployee(),
              systemFailureTextField.getText(),
              "AVRequest",
              datePicker.getValue(),
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
        || systemFailureTextField.getText().isEmpty());
  }

  public Node getVBox() {
    return null;
  }
}
