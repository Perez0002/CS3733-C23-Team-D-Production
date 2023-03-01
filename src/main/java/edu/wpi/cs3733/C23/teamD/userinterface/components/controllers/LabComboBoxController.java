package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class LabComboBoxController {
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  ArrayList<String> Labs = new ArrayList<String>();

  /*
    Complete Blood Count (CBC)
  Comprehensive Metabolic Panel (CMP)
  Hemoglobin A1C (HbA1C)
  Lipid Panel.
  Thyroid Panel.
  Vitamin D.
  Anemia Panel.
  How to understand your results.
     */
  public LabComboBoxController() {
    Labs.add("Complete Blood Count (CBC)");
    Labs.add("Comprehensive Metabolic Panel (CMP)\n");
    Labs.add("Hemoglobin A1C (HbA1C)");
    Labs.add("Lipid Panel");
    Labs.add("Thyroid Panel");
    Labs.add("Vitamin D");
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
