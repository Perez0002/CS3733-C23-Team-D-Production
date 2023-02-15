package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.controllers.components.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.AVRequest;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.Date;

public class AVRequestController implements ServiceRequestVBoxController {
  @FXML private Parent employeeBox;
  @FXML private MFXTextField descriptionTextField;

  @FXML private MFXTextField systemFailureTextField;

  @FXML private MFXTextField timeTextField;

  @FXML private EmployeeDropdownComboBoxController employeeBoxController;




  public void clearTransportForms() {
    employeeBoxController.clearForm();
    timeTextField.clear();
    systemFailureTextField.clear();
    descriptionTextField.clear();
  }

  @Override
  public boolean submit() {
    AVRequest request = new AVRequest(employeeBoxController.getEmployeeName(), ServiceRequest.Status stat, String reason, String serviceRequestType, String systemFailureTextField, Date dateFirstSeen);
    FDdb.getInstance().saveServiceRequest();

    return true;
  }

  @Override
  public Node getVBox() {
    return null;
  }
}
