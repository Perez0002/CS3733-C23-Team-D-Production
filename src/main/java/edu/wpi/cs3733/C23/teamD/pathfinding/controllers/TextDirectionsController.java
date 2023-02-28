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
      if (direction != null) {
        directionStr = directionStr.concat(direction + "\n");
        if (direction.contains("floor")) {
          directionStr = directionStr.concat("\n");
        }
      }
    }
    textDirectionsTextBox.setText(directionStr);
    textDirectionsTextBox.setStyle("-fx-font-size: 20pt");
  }
}
