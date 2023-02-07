package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.CurrentUser;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class RootController {
  @FXML
  public void initialize() {
    CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    currentUser.setAccessLevel(0);
  }

  public void checkAccessLevel() {
    CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
  }

  @FXML
  void openLoginPage() {
    Navigation.navigate(Screen.LOGIN_PAGE);
  }

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
  void openHelpPage() {
    Navigation.navigate(Screen.HELP_PAGE);
  }

  @FXML
  void exit() {
    Platform.exit();
  }
}
