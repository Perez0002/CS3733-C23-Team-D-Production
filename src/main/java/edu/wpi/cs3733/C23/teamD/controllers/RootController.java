package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
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
}
