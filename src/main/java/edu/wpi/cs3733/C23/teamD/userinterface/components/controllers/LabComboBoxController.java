package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class LabComboBoxController {
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  ArrayList<String> Labs = new ArrayList<String>();

  public LabComboBoxController() {
    Labs.add("Blood Oxygen Levels");
    Labs.add("Stool Test");
    Labs.add("Skin Sample");
    Labs.add("Mouth Swab");
    Labs.add("Shotgun");
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(Labs));
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
