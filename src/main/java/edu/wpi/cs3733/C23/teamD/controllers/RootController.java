package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.CurrentUser;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class RootController {

  @FXML private MenuButton databaseMenuButton;
  @FXML private MenuButton formsMenuButton;

  @FXML private MFXButton helpPageButton;

  @FXML
  public void initialize() {
    // checkAccessLevel();
  }

  public void checkAccessLevel() {

    CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();

    if (currentUser.getAccessLevel() == 2) {
      System.out.println("2");
      databaseMenuButton.setVisible(false);
      formsMenuButton.setDisable(false);
      helpPageButton.setDisable(false);
    } else if (currentUser.getAccessLevel() == 1) {
      System.out.println("1");
      databaseMenuButton.setDisable(true);
      formsMenuButton.setDisable(false);
      helpPageButton.setDisable(true);
    } else {
      System.out.println("0");
      databaseMenuButton.setDisable(true);
      helpPageButton.setDisable(false);
      formsMenuButton.setDisable(true);
    }
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
