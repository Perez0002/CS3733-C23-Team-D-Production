package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class RequestTypeComboBoxController {
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  ArrayList<String> types = new ArrayList<String>();

  public RequestTypeComboBoxController() {

    types.add("PatientTransportData");
    types.add("SanitationRequestData");
    types.add("ComputerService");
    types.add("AVRequest");
    types.add("Security");
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(types));
  }

  public String getRequestType() {
    return mfxFilterComboBox.getValue();
  }

  public void setRequestType(String s) {
    mfxFilterComboBox.setValue(s);
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
