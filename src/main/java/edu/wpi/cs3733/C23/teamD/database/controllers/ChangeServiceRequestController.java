package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.StatusComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.ZoneId;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
  @FXML private Text errorText;

  @FXML
  public void submit() {
    if (checkFields()) {
      if (currentRequest != null) {
        currentRequest.setServiceRequestType(requestTypeTextField.getText());
        currentRequest.setReason(reasonTextField.getText());
        currentRequest.setStat(statusBoxController.getStatus());
        currentRequest.setDateAndTime(
            Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        currentRequest.setAssociatedStaff(employeeBoxController.getEmployee());
        FDdb.getInstance().updateServiceRequest(currentRequest);
        databaseController.refresh();
        dataToChange(null);
        ToastController.makeText(
            "Your service request has been changed!",
            1500,
            50,
            100,
            (int) Screen.getPrimary().getBounds().getWidth() - 375,
            (int) Screen.getPrimary().getBounds().getHeight() - 275);
      }
    } else {
      errorText.setVisible(true);
    }
  }

  private boolean checkFields() {
    return !(reasonTextField.getText().isEmpty()
        || employeeBoxController.getEmployeeName() == null
        || statusBoxController.getStatus() == null
        || datePicker.getValue() == null
        || requestTypeTextField.getText() == null);
  }

  @FXML
  public void clearFields() {
    errorText.setVisible(false);
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
      employeeBoxController.setEmployeeName(
          serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
      reasonTextField.setText(serviceRequest.getReason());
      requestTypeTextField.setText(serviceRequest.getServiceRequestType());
      datePicker.setValue(
          serviceRequest.getDateAndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
  }
}
