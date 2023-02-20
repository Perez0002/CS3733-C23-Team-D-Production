package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.StatusComboBoxController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import lombok.Setter;

public class ChangeServiceRequestController implements AddFormController<ServiceRequest> {

  @Setter DatabaseController databaseController;
  @FXML private MFXButton submitButton;
  @FXML private MFXTextField reasonTextField;
  @FXML private Parent employeeBox;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;
  private ServiceRequest currentRequest;
  @FXML private Parent statusBox;

  @FXML private MFXDatePicker datePicker;

  @FXML private MFXTextField requestTypeTextField;

  @FXML private StatusComboBoxController statusBoxController;

  @FXML
  public void submit() {}

  @FXML
  public void clearFields() {
    statusBoxController.clearForm();
    datePicker.setValue(null);
    employeeBoxController.clearForm();
    reasonTextField.clear();
    reasonTextField.clear();
  }

  @Override
  public void dataToChange(ServiceRequest serviceRequest) {
    currentRequest = serviceRequest;
    if (serviceRequest == null) {
      clearFields();
    } else {
      statusBoxController.setStatus(serviceRequest.getStat().toString());
      employeeBoxController.setEmployeeName(serviceRequest.getAssociatedStaff());
      reasonTextField.setText(serviceRequest.getReason());
      requestTypeTextField.setText(serviceRequest.getServiceRequestType());
      datePicker.setValue(
          serviceRequest.getDateAndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
  }
}
