package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequestData;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class SanitationRequestController {

  private boolean helpDisplayed = false;

  @FXML private Text textHelp;
  @FXML private MFXRadioButton radioBSL1;
  @FXML private MFXRadioButton radioBSL2;
  @FXML private MFXRadioButton radioBSL3;
  @FXML private MFXRadioButton radioBSL4;
  @FXML private MFXTextField fieldReason;
  //  @FXML private MFXTextField fieldLocation;
  @FXML private Text formSubmittedText;
  @FXML Text locationHelpText;
  @FXML Text reasonHelpText;
  @FXML Text staffIDHelpText;
  @FXML MFXTextField staffIDTextField;

  @FXML private Parent fieldLocation;

  @FXML private RoomPickComboBoxController fieldLocationController;

  @FXML
  public void submitSanitationRequest() {
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
      formSubmittedText.setVisible(true);
      SanitationRequestData requestData =
          new SanitationRequestData(
              fieldLocationController.getNodeValue(),
              fieldReason.getText(),
              i,
              staffIDTextField.getText(),
              SanitationRequestData.Status.BLANK);
      Ddb.insertNewForm(requestData);

      textHelp.setVisible(false);
      locationHelpText.setVisible(false);
      reasonHelpText.setVisible(false);
      staffIDHelpText.setVisible(false);
      formSubmittedText.setVisible(true);
    } else {
      helpDisplayed = false;
      formSubmittedText.setVisible(false);
      textHelp.setVisible(true);
      displayHelp();
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
    formSubmittedText.setVisible(false);
    formSubmittedText.setVisible(false);
    // System.out.print("Fields Cleared\n");
  }

  @FXML
  public void displayHelp() {
    helpDisplayed = !helpDisplayed; // if help is already displayed, turns off (toggle feature)
    reasonHelpText.setVisible(helpDisplayed);
    locationHelpText.setVisible(helpDisplayed);
    staffIDHelpText.setVisible(helpDisplayed);
  }

  private boolean isFieldsSaturated() {
    // System.out.print("Submit Success2: ");
    return (fieldReason.getText() != ""
        && (fieldLocationController.getNodeValue() != null)
        && staffIDTextField.getText() != ""
        && (radioBSL1.isSelected()
            || radioBSL2.isSelected()
            || radioBSL3.isSelected()
            || radioBSL4.isSelected()));
  }
}
