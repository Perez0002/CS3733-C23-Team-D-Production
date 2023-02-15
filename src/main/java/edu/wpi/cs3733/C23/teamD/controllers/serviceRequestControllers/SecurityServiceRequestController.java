package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.controllers.ServiceRequestController;
import edu.wpi.cs3733.C23.teamD.controllers.components.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.controllers.components.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.controllers.components.UrgencySelectorBoxController;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.SecurityServiceRequest;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class SecurityServiceRequestController extends ServiceRequestController {
  @FXML private Parent staffDropdownBox;
  @FXML private EmployeeDropdownComboBoxController staffDropdownBoxController;
  @FXML private Parent locationDropdownBox;

  @FXML private RoomPickComboBoxController locationDropdownBoxController;

  @FXML private Parent urgencyDropdownBox;
  @FXML private UrgencySelectorBoxController urgencyDropdownBoxController;
  @FXML private MFXCircleToggleNode addSecurityNode;
  @FXML private MFXCircleToggleNode addRequestSecurityNode;
  @FXML private MFXTextField problemTextField;

  public SecurityServiceRequestController() {}

  public void initialize() {
    // TODO temp
  }

  public void submit() {
    String typeOfRequest = "";
    if (addSecurityNode.isFocused()) {
      typeOfRequest = "Add Security";
    } else if (addRequestSecurityNode.isFocused()) {
      typeOfRequest = "Request Security";
    }

    SecurityServiceRequest securityServiceRequest =
        new SecurityServiceRequest(
            typeOfRequest,
            urgencyDropdownBoxController.getEmployeeName(),
            staffDropdownBoxController.getEmployeeName(),
            problemTextField.getText());
    FDdb.getInstance().saveServiceRequest(securityServiceRequest);
  }
}
