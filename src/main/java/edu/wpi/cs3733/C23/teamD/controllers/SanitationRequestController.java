package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.SanitationRequestData;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SanitationRequestController {

  private boolean helpDisplayed = false;

  public static ObservableList<SanitationRequestData> sanitationList =
      FXCollections.observableArrayList();
  @FXML private Text textHelp;
  @FXML private MFXRadioButton radioBSL1;
  @FXML private MFXRadioButton radioBSL2;
  @FXML private MFXRadioButton radioBSL3;
  @FXML private MFXRadioButton radioBSL4;
  @FXML private MFXTextField fieldReason;
  @FXML private MFXTextField fieldLocation;

  @FXML private MFXTextField fieldStaffIDSanitation;

  @FXML private Text formSubmittedText;
  @FXML MFXButton cancelButton;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

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
          new SanitationRequestData(
              fieldLocation.getText(),
              fieldReason.getText(),
              i,
              fieldStaffIDSanitation.getText(),
              SanitationRequestData.status.DONE);
      sanitationList.add(requestData);
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
  public void displayHelp() {
    helpDisplayed = !helpDisplayed;
    textHelp.setVisible(helpDisplayed);
  }

  private boolean isFieldsSaturated() {
    return (((fieldReason.getText() != ""
            && fieldLocation.getText() != ""
            && fieldStaffIDSanitation.getText() != ""))
        && (radioBSL1.isSelected()
            || radioBSL2.isSelected()
            || radioBSL3.isSelected()
            || radioBSL4.isSelected()));
  }

  public static ObservableList<SanitationRequestData> getSanitationList() {
    return sanitationList;
  }

  public void setSanitationList(ObservableList<SanitationRequestData> sanitationList) {
    this.sanitationList = sanitationList;
  }
}
