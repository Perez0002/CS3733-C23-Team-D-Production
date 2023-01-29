package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class HomepageController {

  @FXML private Label bottomHelpText;

  @FXML private BorderPane homepageBorderPane;

  @FXML private Label serviceRequestHelpText;

  @FXML
  /** user open menubutton, clicks Exit, and it closes the window. */
  void closeApplication(ActionEvent event) {
    homepageBorderPane.getScene().getWindow().hide();
  }

  @FXML
  /**
   * checks label text. Sets label text to help text when button clicked, and sets label text to
   * empty when button clicked again
   */
  void toggleHelpText(ActionEvent event) {
    if (serviceRequestHelpText.getText().equals("") || bottomHelpText.getText().equals("")) {
      serviceRequestHelpText.setText("Click the buttons below to fill out service request forms!");
      bottomHelpText.setText(
          "<-Use the leftmost button to exit the program    Click the rightmost button to remove the help text->");
    } else {
      serviceRequestHelpText.setText("");
      bottomHelpText.setText("");
    }
  }

  @FXML
  public void openPatientTransportRequestForm() {
    // System.out.println("openHomeController"); // for debugging purposes
    try {
      Parent root =
          FXMLLoader.load(
              getClass()
                  .getResource(
                      "/edu/wpi/teamname/views/InternalPatientTransportationRequestForm.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  public void openSanitationServicesRequestForm() {
    // System.out.println("openHomeController"); // for debugging purposes
    try {
      Parent root =
          FXMLLoader.load(
              getClass().getResource("/edu/wpi/teamname/views/SanitationRequestForm.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
