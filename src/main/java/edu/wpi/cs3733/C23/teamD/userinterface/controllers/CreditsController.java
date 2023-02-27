package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class CreditsController {
  @FXML private VBox vBox;
  // private Duration duration;
  public void initialize() {
    Line pathLine = new Line();

    PathTransition pathTransition = new PathTransition();
    pathTransition.setNode(vBox);
    pathTransition.setDuration(Duration.seconds(10));
    pathTransition.setPath(pathLine);
    pathTransition.play();
  }
}
