package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class DatabaseLandingPageController {
  @FXML private MFXButton backButton;

  @FXML private MFXButton DBAppButton;

  @FXML private MFXButton patientTransportTableButton;

  @FXML private MFXButton sanitationTableButton;
  @FXML private MFXButton updateMovesButton;
  @FXML private MFXButton serviceTableButton;

  @FXML
  public void initialize() {
    DBAppButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_EDIT));
    patientTransportTableButton.setOnMouseClicked(
        event -> Navigation.navigate(Screen.PATIENT_TRANSPORT_TABLE));
    sanitationTableButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SANITATION_TABLE));
    serviceTableButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_TABLE));
    updateMovesButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MOVES_TABLE));
  }
}
