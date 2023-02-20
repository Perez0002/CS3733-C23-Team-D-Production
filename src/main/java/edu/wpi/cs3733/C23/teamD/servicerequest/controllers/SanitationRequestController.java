package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.SanitationRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SanitationRequestController implements ServiceRequestVBoxController {

  @FXML private Text textHelp;
  @FXML private MFXRadioButton radioBSL1, radioBSL2, radioBSL3, radioBSL4;
  @FXML private TextField fieldReason;
  @FXML private Parent staffIDTextField;
  @FXML private EmployeeDropdownComboBoxController staffIDTextFieldController;
  @FXML private Parent fieldLocation;
  @FXML private LocationComboBoxController fieldLocationController;
  @FXML private MFXComboBox urgencyBox;
  private boolean helpDisplayed = false;

  @Override
  public void clearTransportForms() {}

  @FXML
  public boolean submit() {
    if (isFieldsSaturated()) {
      // System.out.print("Submit Success: ");
      int i = 0;
      if (radioBSL1.isSelected()) {
        i = 1;
      } else if (radioBSL2.isSelected()) {
        i = 2;
      } else if (radioBSL3.isSelected()) {
        i = 3;
      } else if (radioBSL4.isSelected()) {
        i = 4;
      }
      SanitationRequest requestData =
          new SanitationRequest(
              fieldReason.getText(),
              i,
              staffIDTextFieldController.getEmployee(),
              fieldLocationController.getLocation(),
              urgencyBox.getValue().toString());
      FDdb.getInstance().saveServiceRequest(requestData);
      return true;

    } else {
      return false;
    }
  }

  @Override
  public Node getVBox() {
    return null;
  }

  @FXML
  public boolean clearSanitationForms() {
    fieldLocationController.clearForm();
    fieldReason.clear();
    staffIDTextFieldController.clearForm();
    radioBSL1.setSelected(false);
    radioBSL2.setSelected(false);
    radioBSL3.setSelected(false);
    radioBSL4.setSelected(false);
    urgencyBox.clearSelection();
    return true;
    // System.out.print("Fields Cleared\n");
  }

  private boolean isFieldsSaturated() {
    // System.out.print("Submit Success2: ");
    return (fieldReason.getText() != ""
        && (fieldLocationController.getLocation() != null)
        && staffIDTextFieldController.getEmployeeName() != null
        && (radioBSL1.isSelected()
            || radioBSL2.isSelected()
            || radioBSL3.isSelected()
            || radioBSL4.isSelected())
        && urgencyBox.getValue() != null);
  }
}
