package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class AboutController {
  @FXML ImageView imageOne;

  @FXML
  public void initialize() {
    imageOne.setViewport(new Rectangle2D(0, 0, 720, 720));
    imageOne.setFitHeight(150);
  }
}
