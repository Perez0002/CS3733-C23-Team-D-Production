package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ClockController {

  @FXML Label timer;

  @FXML
  public void initialize() {

    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e ->
                    timer.setText(
                        LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("EEE, LLL dd, h:mm a")))),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }
}
