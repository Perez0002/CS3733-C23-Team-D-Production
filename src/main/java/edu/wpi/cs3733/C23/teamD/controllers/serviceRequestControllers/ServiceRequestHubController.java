package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class ServiceRequestHubController {

  @FXML private MFXButton clickableTest;

  @FXML private Pane requestFormHubPane;

  @FXML private Parent hubVBox;

  @FXML private HubBoxController hubVBoxController;

  public void initialize() {

    clickableTest.setOnMouseClicked(event -> hubVBoxController.setVisible());
  }
}
