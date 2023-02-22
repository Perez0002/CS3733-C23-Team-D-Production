package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.SecurityServiceRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.UrgencySelectorBoxController;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;

public class SecurityServiceRequestController extends ServiceRequestController
    implements ServiceRequestVBoxController {
  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;
  @FXML private Parent urgencyBox;
  @FXML private UrgencySelectorBoxController urgencyBoxController;
  @FXML private MFXCheckbox addSecurityNode;
  @FXML private MFXCheckbox addRequestSecurityNode;
  @FXML private MFXTextField problemTextField;
  @FXML private Parent employeeBox;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;

  public SecurityServiceRequestController() {}

  public void initialize() {}

  public void clearFields() {
    employeeBoxController.clearForm();
    locationBoxController.clearForm();
    urgencyBoxController.clearForm();
    this.problemTextField.clear();
    this.addSecurityNode.setSelected(false);
    this.addRequestSecurityNode.setSelected(false);
  }

  @Override
  public void clearTransportForms() {}

  public boolean submit() {
    if (checkFields()) {
      String typeOfRequest = "";
      if (addRequestSecurityNode.isSelected() && addSecurityNode.isSelected()) {
        typeOfRequest = "Add Security,Request Security";
      } else if (addSecurityNode.isSelected()) {
        typeOfRequest = "Add Security";
      } else if (addRequestSecurityNode.isSelected()) {
        typeOfRequest = "Request Security";
      }

      SecurityServiceRequest securityServiceRequest =
          new SecurityServiceRequest(
              typeOfRequest,
              employeeBoxController.getEmployee(),
              problemTextField.getText(),
              locationBoxController.getLocation(),
              urgencyBoxController.getUrgency());
      try {
        FDdb.getInstance().saveServiceRequest(securityServiceRequest);
        return true;
      } catch (Exception ex) {
        ex.printStackTrace();
        return false;
      }
    } else return false;
  }

  private boolean checkFields() {
    if (employeeBoxController.getEmployeeName() != null
        && locationBoxController.getLocation() != null
        && problemTextField.getText() != null
        && urgencyBoxController.getUrgency() != null) {
      return true;
    } else return false;
  }

  @Override
  public void setFieldsDisable(ServiceRequest serviceRequest) {

    employeeBoxController.setDisable(true);
    problemTextField.setDisable(true);
    locationBoxController.setDisable(true);
    urgencyBoxController.setDisable(true);
    addSecurityNode.setDisable(true);
    addRequestSecurityNode.setDisable(true);
    if (serviceRequest.getClass().equals(SecurityServiceRequest.class)) {
      problemTextField.setText(serviceRequest.getReason().toString());
      urgencyBoxController.setText(serviceRequest.getUrgency());
      locationBoxController.setText(serviceRequest.getLocation().getLongName());
      if (((SecurityServiceRequest) serviceRequest)
          .getTypeOfSecurityRequest()
          .equals("Add Security")) {
        addSecurityNode.setSelected(true);
        addRequestSecurityNode.setSelected(false);
      } else if (((SecurityServiceRequest) serviceRequest)
          .getTypeOfSecurityRequest()
          .equals("Request Security")) {
        addRequestSecurityNode.setSelected(true);
        addSecurityNode.setSelected(false);
      } else if (((SecurityServiceRequest) serviceRequest)
          .getTypeOfSecurityRequest()
          .equals("Add Security,Request Security")) {
        addRequestSecurityNode.setSelected(true);
        addSecurityNode.setSelected(true);
      } else {
        addRequestSecurityNode.setSelected(false);
        addSecurityNode.setSelected(false);
      }
      employeeBoxController.setText(
          serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
    }
  }

  @Override
  public Node getVBox() {
    return null;
  }
}
