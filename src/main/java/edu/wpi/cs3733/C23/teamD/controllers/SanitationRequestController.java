package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequestData;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
  @FXML private MFXTextField fieldLocation;
  @FXML private Text formSubmittedText;

  @FXML
  public void submit() {
    if (isFieldsSaturated()) {
      System.out.print("Submit Success: ");
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
          new SanitationRequestData(fieldLocation.getText(), fieldReason.getText(), i);
      requestData.printSanititationInfo();
    } else {
      helpDisplayed = false;
      formSubmittedText.setVisible(false);
      displayHelp();
      // System.out.println("Please fill in the appropriate response. ");
    }
  }

  @FXML
  public void clearFields() {
    fieldLocation.clear();
    fieldReason.clear();
    radioBSL1.setSelected(false);
    radioBSL2.setSelected(false);
    radioBSL3.setSelected(false);
    radioBSL4.setSelected(false);
    formSubmittedText.setVisible(false);
    System.out.print("Fields Cleared\n");
  }

  @FXML
  public void openHomeController() {
    try {
      Parent root =
          FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/Homepage.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    } // for debugging purposes
  }

  @FXML
  public void displayHelp() {
    helpDisplayed = !helpDisplayed;
    textHelp.setVisible(helpDisplayed);
  }

  private boolean isFieldsSaturated() {
    return (((fieldReason.getText() != "" && fieldLocation.getText() != ""))
        && (radioBSL1.isSelected()
            || radioBSL2.isSelected()
            || radioBSL3.isSelected()
            || radioBSL4.isSelected()));
  }
}
