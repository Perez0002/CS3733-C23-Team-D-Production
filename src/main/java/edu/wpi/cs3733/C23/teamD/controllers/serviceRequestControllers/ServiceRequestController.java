package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController {
  private RequestSubmitter requestSubmitter;
  private FieldClearer fieldClearer;

  public void submit() {
    requestSubmitter.submit();
  }

  public void clearFields() {
    fieldClearer.clearFields();
  }
}
