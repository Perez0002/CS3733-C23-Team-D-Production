package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class RootController {
  @FXML
  public void initialize() {}

  @FXML
  void openHomepage() {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void openPathfindingForm() {
    Navigation.navigate(Screen.PATHFINDING_REQUEST);
  }

  @FXML
  void openSanitationForm() {
    Navigation.navigate(Screen.SANITATION_FORM);
  }

  @FXML
  void openPatientTransport() {
    Navigation.navigate(Screen.PATIENT_TRANSPORT_REQUEST);
  }

  @FXML
  void openDatabase() {
    Navigation.navigate(Screen.DATABASE_EDIT);
  }

  @FXML
  void openPatientTransportTable() {
    Navigation.navigate(Screen.PATIENT_TRANSPORT_TABLE);
  }

  @FXML
  void openSanitationTable() {
    Navigation.navigate(Screen.SANITATION_TABLE);
  }

  @FXML
  void exit() {
    Platform.exit();
  }
}
