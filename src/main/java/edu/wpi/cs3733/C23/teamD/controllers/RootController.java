package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUser;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class RootController {

  @FXML private MenuButton databaseMenuButton;
  @FXML private MenuButton formsMenuButton;

  @FXML private MFXButton helpPageButton;

  @FXML
  public void initialize() throws IOException {
    checkAccessLevel();
  }

  public void checkAccessLevel() {

    CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();

    if (currentUser.getAccessLevel() == 2) {
      databaseMenuButton.setDisable(false);
      formsMenuButton.setDisable(false);
    } else if (currentUser.getAccessLevel() == 1) {
      databaseMenuButton.setDisable(true);
      formsMenuButton.setDisable(false);
    } else {
      databaseMenuButton.setDisable(true);
      formsMenuButton.setDisable(true);
    }
  }

  @FXML
  public void openLoginPage() throws IOException {
    App.getRootPane().setTop(null);
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
