package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ServiceRequestHubController {

  @FXML private MFXButton clickableTest;

  @FXML private Pane requestFormHubPane;

  @FXML private HubBoxController hubBoxController;

  public void initialize() {

    clickableTest.setOnMouseClicked(
        event -> requestFormHubPane.getChildren().add(hubBoxController.getVBox()));
  }
}
