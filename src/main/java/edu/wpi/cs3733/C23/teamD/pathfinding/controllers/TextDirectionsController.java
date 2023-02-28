package edu.wpi.cs3733.C23.teamD.pathfinding.controllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Setter;

public class TextDirectionsController {
  @FXML private Label textDirectionsTextBox;
  @Setter PathfindingController pathfindingController;

  public void setDirections(ArrayList<String> directions) {
    String directionStr = "";
    for (String direction : directions) {
      directionStr.concat(direction);
    }
    textDirectionsTextBox.setText(directionStr);
  }
}
