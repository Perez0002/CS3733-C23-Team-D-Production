package edu.wpi.cs3733.C23.teamD.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LoginController {

  private boolean helpVisible = false;
  private boolean incorrectInfoVisible = false;
  @FXML private Text incorrectUsernameOrPasswordText;

  @FXML private Text usernameText;

  @FXML private Text passwordText;

  @FXML private MFXTextField username;

  @FXML private MFXTextField password;

  @FXML
  /*
  displayHelp()
  @param void
  @return void
  linked to "Help" button on SceneBuilder page, when selected
  reverts to "Help" functions (undetermined)
  */
  private void displayHelp() {
    helpVisible = !helpVisible;

    usernameText.setVisible(helpVisible);
    passwordText.setVisible(helpVisible);
  }

  @FXML
  /*
  clearFields()
  @param void
  @return void
  linked to "Clear" button on SceneBuilder page
  */
  public void clearFields() {
    username.clear();
    password.clear();
    // System.out.println("clearFields"); // for debugging purposes
  } // end clearFields()
}
