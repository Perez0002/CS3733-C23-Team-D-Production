package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.SecurityServiceRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.UrgencySelectorBoxController;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class SecurityServiceRequestController extends ServiceRequestController
    implements ServiceRequestVBoxController {
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;

  @FXML private RoomPickComboBoxController locationBoxController;

  @FXML private UrgencySelectorBoxController urgencyBoxController;
  @FXML private MFXCheckbox addSecurityNode, addRequestSecurityNode;
  @FXML private TextField problemTextField;

  @FXML private MFXComboBox employeeBox, urgencyBox, locationBox;

  public SecurityServiceRequestController() {}

  public void initialize() {
    // TODO temp
  }

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
      if (addRequestSecurityNode.isFocused() && addSecurityNode.isFocused()) {
        typeOfRequest = "Add Security,Request Security";
      } else if (addSecurityNode.isFocused()) {
        typeOfRequest = "Add Security";
      } else if (addRequestSecurityNode.isFocused()) {
        typeOfRequest = "Request Security";
      }

      SecurityServiceRequest securityServiceRequest =
          new SecurityServiceRequest(
              typeOfRequest,
              urgencyBoxController.getEmployeeName(),
              employeeBoxController.getEmployeeName(),
              problemTextField.getText());
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
    if (employeeBox.getValue() != null
        && locationBox.getValue() != null
        && problemTextField.getText() != null
        && urgencyBox.getValue() != null) {
      return true;
    } else return false;
  }

  @Override
  public Node getVBox() {
    return null;
  }
}
