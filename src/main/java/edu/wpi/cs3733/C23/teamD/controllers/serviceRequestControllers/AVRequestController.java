package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class AVRequestController extends ServiceRequestController {
  @FXML private MFXTextField descriptionTextField;

  @FXML private MFXTextField staffIDTextField;

  @FXML private MFXTextField systemFailureTextField;

  @FXML private MFXTextField timeTextField;

  private RequestSubmitter requestSubmitter = new AVRequestSubmitter() {};

  public void initialize() {
    setFieldClearer(
        new AVFieldClearer(
            descriptionTextField, staffIDTextField, systemFailureTextField, timeTextField));
  }
}
