package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.cardreader.CommPort;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.servicerequest.util.LoginChecker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;

public class LoginController {

  private boolean helpVisible = false;
  @FXML private Text incorrectUsernameOrPasswordText;

  @FXML private Text usernameText;

  @FXML private MFXTextField username;

  @FXML private MFXPasswordField password;

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
  }

  @FXML
  /*
  checkFields
  @param void
  @return boolean
  helper function for submit() function,
  ensures all necessary fields are filled before submission
  */
  public void initialize() {
    CommPort commPort = new CommPort("NFC Card Reader");
    commPort
        .getPort()
        .addDataListener(
            new SerialPortDataListener() {
              @Override
              public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
              }

              @Override
              public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                  if (commPort.readSerial().contains("41 38 EE 20")) {
                    commPort.closeSerial();

                    LoginChecker loginInfo =
                        new LoginChecker("admin", "admin"); // creates PatientTransportData object

                    if (loginInfo.setAccessLevel()) {

                      incorrectUsernameOrPasswordText.setVisible(false);
                      try {
                        App.getRootPane()
                            .setLeft(
                                FXMLLoader.load(
                                    getClass()
                                        .getResource(
                                            "/edu/wpi/cs3733/C23/teamD/views/NavBar.fxml")));
                      } catch (IOException e) {
                        throw new RuntimeException(e);
                      }

                      Navigation.navigate(Screen.HOME);
                      App.getPrimaryStage().setMaximized(true);
                    }
                  } else {
                    System.out.println("Invalid Card");
                  }
                }
              }
            });
  }

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
  /*
  submit()
  @param void
  @return void
  linked to "submit" button on SceneBuilder page, when selected
  submits information filled out in forms
  */
  public void submitLogin() throws IOException {

    LoginChecker loginInfo =
        new LoginChecker(
            username.getText(), password.getText()); // creates PatientTransportData object

    if (loginInfo.setAccessLevel()) {

      incorrectUsernameOrPasswordText.setVisible(false);
      App.getRootPane()
          .setLeft(
              FXMLLoader.load(
                  getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/NavBar.fxml")));

      Navigation.navigate(Screen.HOME);
      App.getPrimaryStage().setMaximized(true);

    } else {
      incorrectUsernameOrPasswordText.setVisible(true);
    }
  } // end submit()
}
