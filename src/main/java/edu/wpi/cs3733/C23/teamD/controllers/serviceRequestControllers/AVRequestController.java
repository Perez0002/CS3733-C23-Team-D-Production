package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class AVRequestController implements ServiceRequestVBoxController {
  @FXML private MFXTextField descriptionTextField;

  @FXML private MFXTextField staffIDTextField;

  @FXML private MFXTextField systemFailureTextField;

  @FXML private MFXTextField timeTextField;

  @Override
  public Node getVBox() {
    return null;
  }
}
