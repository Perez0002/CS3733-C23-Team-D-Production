package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import javafx.fxml.FXML;

public class cancelButtonController {

  // navigates to homepage
  @FXML
  public void openHomepage() {
    Navigation.navigate(Screen.HOME);
  }
}
