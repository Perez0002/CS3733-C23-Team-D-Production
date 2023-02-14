package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.controllers.components.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequest;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class SanitationRequestVBoxController implements ServiceRequestVBoxController {
  @FXML private MFXRadioButton radioBSL1;
  @FXML private MFXRadioButton radioBSL2;
  @FXML private MFXRadioButton radioBSL3;
  @FXML private MFXRadioButton radioBSL4;
  @FXML private MFXTextField fieldReason;
  @FXML MFXTextField staffIDTextField;
  @FXML private VBox sanitationRequestVBOX;

  @FXML private Parent fieldLocation;

  @FXML private RoomPickComboBoxController fieldLocationController;

  @FXML
  public void submitSanitationRequest() {
    if (isFieldsSaturated()) {
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
              staffIDTextField.getText(),
              SanitationRequest.Status.BLANK);
      Ddb.insertNewForm(requestData);
    }
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
  }

  private boolean isFieldsSaturated() {
    return (fieldReason.getText() != ""
        && (fieldLocationController.getNodeValue() != null)
        && staffIDTextField.getText() != ""
        && (radioBSL1.isSelected()
            || radioBSL2.isSelected()
            || radioBSL3.isSelected()
            || radioBSL4.isSelected()));
  }

  @Override
  public Node getVBox() {
    return sanitationRequestVBOX;
  }
}
