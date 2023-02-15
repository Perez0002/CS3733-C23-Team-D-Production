package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.controllers.ServiceRequestController;
import edu.wpi.cs3733.C23.teamD.controllers.components.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.controllers.components.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.controllers.components.UrgencySelectorBoxController;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.SecurityServiceRequest;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;

public class SecurityServiceRequestController extends ServiceRequestController
    implements ServiceRequestVBoxController {
  @FXML private Parent employeeBox;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;
  @FXML private Parent locationBox;

  @FXML private RoomPickComboBoxController locationBoxController;

  @FXML private Parent urgencyBox;
  @FXML private UrgencySelectorBoxController urgencyBoxController;
  @FXML private MFXCheckbox addSecurityNode;
  @FXML private MFXCheckbox addRequestSecurityNode;
  @FXML private MFXTextField problemTextField;

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
  }

  @Override
  public Node getVBox() {
    return null;
  }
}
