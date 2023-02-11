package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.CurrentUser;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.entities.LoginData;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lombok.Setter;

public class LoginController {

  private boolean helpVisible = false;
  @FXML private Text incorrectUsernameOrPasswordText;

  @FXML private Text successfulLoginText;

  @FXML private Text currentUserText;

  @FXML private Text usernameText;

  @FXML private Text successfulLogoutText;

  @FXML private Text passwordText;

  @FXML private MFXTextField username;

  @FXML private MFXTextField password;

  @Setter RootController rootController;

  public LoginController() throws IOException {}

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
  public void clearFields() throws IOException {
    successfulLoginText.setVisible(false);
    incorrectUsernameOrPasswordText.setVisible(false);
    successfulLogoutText.setVisible(false);
    username.clear();
    password.clear();
    // System.out.println("clearFields"); // for debugging purposes
  } // end clearFields()

  /*
  checkFields
  @param void
  @return boolean
  helper function for submit() function,
  ensures all necessary fields are filled before submission
  */
  private boolean checkFields() {
    if (checkPassword() && checkUsername()) {
      return true;
    } else {
      if (!helpVisible) {
        displayHelp();
      }
      return false;
    }
  } // end checkFields()

  /**
   * checks to see if there is any text in the username field
   *
   * @return true if there is any text
   */
  private boolean checkUsername() {
    return (!username.getText().equals(""));
  }

  /**
   * checks to see if there is any text in the password field
   *
   * @return true if there is any text
   */
  private boolean checkPassword() {
    return (!password.getText().equals(""));
  }

  @FXML
  public void initialize() {
    displayCurrentUser();
  }

  private void displayCurrentUser() {
    CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    if (currentUser.getAccessLevel() == 0) {
      currentUserText.setText("please log in");
    } else {
      currentUserText.setText("You are logged in as: \n" + currentUser.getUsername());
    }
  }

  @FXML
  /*
  submit()
  @param void
  @return void
  linked to "submit" button on SceneBuilder page, when selected
  submits information filled out in forms
  */
  public void submitLogin() throws IOException {

    LoginData loginInfo =
        new LoginData(
            username.getText(), password.getText()); // creates PatientTransportData object

    if (loginInfo.setAccessLevel()) {

      CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
      currentUser.setAccessLevel(loginInfo.getAccessLevel());
      currentUser.setUsername(loginInfo.getUsername());
      successfulLoginText.setVisible(true);

      incorrectUsernameOrPasswordText.setVisible(false);
      successfulLogoutText.setVisible(false);
      displayCurrentUser();
      rootController.checkAccessLevel();

    } else {
      successfulLoginText.setVisible(false);
      incorrectUsernameOrPasswordText.setVisible(true);
      successfulLogoutText.setVisible(false);
    }
  } // end submit()

  @FXML
  public void logout() {
    successfulLoginText.setVisible(false);
    incorrectUsernameOrPasswordText.setVisible(false);
    successfulLogoutText.setVisible(true);
    CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    currentUser.setAccessLevel(0);
    currentUser.setUsername("");
    displayCurrentUser();
  }
}
