package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.servicerequest.util.LoginChecker;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class LoginController {

  private boolean helpVisible = false;
  @FXML private Text helpText;

  @FXML private Label usernameLabel, passwordLabel;

  @FXML private TextField username;

  @FXML private PasswordField password;

  @FXML
  public void initialize() {
    password.setOnKeyPressed(
        event -> {
          if (event.getCode().equals(KeyCode.ENTER)) {
            try {
              submitLogin();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        });
  }

  @FXML
  /**
   * displayHelp()
   *
   * @param void
   * @return void linked to "Help" button on SceneBuilder page, when selected reverts to "Help"
   *     functions (undetermined)
   */
  private void displayHelp() {
    helpVisible = !helpVisible;

    usernameLabel.setVisible(helpVisible);
  }

  @FXML
  /**
   * checkFields
   *
   * @param void
   * @return boolean helper function for submit() function, ensures all necessary fields are filled
   *     before submission
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
  /**
   * submit()
   *
   * @param void
   * @return void linked to "submit" button on SceneBuilder page, when selected submits information
   *     filled out in forms
   */
  public void submitLogin() throws IOException {
    if (checkFields()) {

      LoginChecker loginInfo =
          new LoginChecker(
              username.getText(), password.getText()); // creates PatientTransportData object

      if (loginInfo.setAccessLevel()) {

        helpText.setText("");
        App.getRootPane()
            .setLeft(
                FXMLLoader.load(
                    getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/NavBar.fxml")));

        Navigation.navigate(Screen.HOME);
        App.getPrimaryStage().setMaximized(true);

      } else {
        helpText.setText("The username or password is incorrect");
      }
    } else {
      helpText.setText("Please enter a username and password");
    }
  } // end submit()
}
