package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import org.w3c.dom.Text;

public class PathfindingController {
  @FXML private MFXButton cancelButton;

  @FXML private MFXTextField endRoom;

  @FXML private Text pathText;

  @FXML private MFXTextField startRoom;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void clearFields() {}

  @FXML
  void displayHelp() {}

  @FXML
  void submit() {}
}
