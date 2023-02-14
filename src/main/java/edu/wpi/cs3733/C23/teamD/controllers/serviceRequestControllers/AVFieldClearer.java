package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import io.github.palexdev.materialfx.controls.MFXTextField;

public class AVFieldClearer implements FieldClearer {
  MFXTextField descriptionTextField;
  MFXTextField staffIDTextField;
  MFXTextField systemFailureTextField;
  MFXTextField timeTextField;

  public AVFieldClearer(
      MFXTextField descriptionTextField,
      MFXTextField staffIDTextField,
      MFXTextField systemFailureTextField,
      MFXTextField timeTextField) {
    this.descriptionTextField = descriptionTextField;
    this.staffIDTextField = staffIDTextField;
    this.systemFailureTextField = systemFailureTextField;
    this.timeTextField = timeTextField;
  }

  public void clearFields() {
    descriptionTextField.clear();
    staffIDTextField.clear();
    systemFailureTextField.clear();
    timeTextField.clear();
  }
}
