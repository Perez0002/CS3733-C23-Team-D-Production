package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.AVRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;

import static edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequestMap.mapSingleton;

public class AVRequestController implements ServiceRequestVBoxController {
  @FXML private Parent employeeBox;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;

  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;

  @FXML private MFXComboBox urgencyBox;

  @FXML private MFXTextField systemFailureTextField;

  @FXML private MFXDatePicker datePicker;


   public void initialize() {
     if (locationBoxController.getLocation() != null) {
       mapSingleton.centerOnNode(100, 100);
     }
  }

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
