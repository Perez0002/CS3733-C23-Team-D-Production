package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.controllers.components.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequest;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class SanitationRequestController implements ServiceRequestVBoxController {

  private boolean helpDisplayed = false;

  @FXML private Text textHelp;
  @FXML private MFXRadioButton radioBSL1;
  @FXML private MFXRadioButton radioBSL2;
  @FXML private MFXRadioButton radioBSL3;
  @FXML private MFXRadioButton radioBSL4;
  @FXML private MFXTextField fieldReason;
  @FXML private MFXComboBox staffIDTextField;

  @FXML private Parent fieldLocation;

  @FXML private RoomPickComboBoxController fieldLocationController;

  @FXML
  public boolean submitSanitationRequest() {
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
              fieldLocationController.getNodeValue(),
              fieldReason.getText(),
              i,
              staffIDTextField.getText());
      FDdb.getInstance().saveServiceRequest(requestData);
      return true;

    } else {
      return false;
    }
  }

  @Override
  public void clearTransportForms() {}

  @Override
  public boolean submit() {
    return false;
  }

  @Override
  public Node getVBox() {
    return null;
  }

  @FXML
  public void clearFields() {
    fieldLocationController.clearForm();
    fieldReason.clear();
    staffIDTextField.clear();
    radioBSL1.setSelected(false);
    radioBSL2.setSelected(false);
    radioBSL3.setSelected(false);
    radioBSL4.setSelected(false);
    // System.out.print("Fields Cleared\n");
  }

  private boolean isFieldsSaturated() {
    // System.out.print("Submit Success2: ");
    return (fieldReason.getText() != ""
        && (fieldLocationController.getNodeValue() != null)
        && staffIDTextField.getText() != null
        && (radioBSL1.isSelected()
            || radioBSL2.isSelected()
            || radioBSL3.isSelected()
            || radioBSL4.isSelected()));
  }
}
