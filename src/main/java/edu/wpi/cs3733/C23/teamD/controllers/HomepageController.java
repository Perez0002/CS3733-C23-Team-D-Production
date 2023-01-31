package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class HomepageController {

  @FXML private Label bottomHelpText;

  @FXML MFXButton internalPatientTransportationRequestFormButton;

  @FXML MFXButton sanitationServiceRequestFormButton;

  @FXML MFXButton pathfindingButton;

  @FXML private BorderPane homepageBorderPane;

  @FXML private Label serviceRequestHelpText;
  @FXML private MFXButton DBAppButton;

  @FXML
  public void initialize() {
    internalPatientTransportationRequestFormButton.setOnMouseClicked(
        event -> Navigation.navigate(Screen.PATIENT_TRANSPORT_REQUEST));
    sanitationServiceRequestFormButton.setOnMouseClicked(
        event -> Navigation.navigate(Screen.SANITATION_FORM));
    DBAppButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_EDIT));
    pathfindingButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING_REQUEST));
  }

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
}
