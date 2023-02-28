package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class EmployeeTypeComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;

  public void initialize() {
    ArrayList<String> types = new ArrayList<String>();
    types.add("ADMIN");
    types.add("STAFF");
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(types));
  }

  public void clearForm() {
    mfxFilterComboBox.clear();
  }

  public void setDisable(boolean b) {
    mfxFilterComboBox.setDisable(b);
  }

  public String getText() {
    return mfxFilterComboBox.getValue();
  }

  public void setText(String s) {
    mfxFilterComboBox.setValue(s);
  }
}
