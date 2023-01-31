package edu.wpi.cs3733.C23.teamD.controllers;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class RoomPickComboBoxController {
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;

  private HashMap<String, String> comboBoxValues;

  public RoomPickComboBoxController() {
    comboBoxValues = new HashMap<>();
    comboBoxValues.put("some room", "CCONF001L1");
    comboBoxValues.put("some other room", "CCONF002L1");
  }

  @FXML
  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(comboBoxValues.keySet()));
  }

  public String getValue() {
    return comboBoxValues.get(mfxFilterComboBox.getValue());
  }
}
