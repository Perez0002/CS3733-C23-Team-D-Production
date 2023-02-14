package edu.wpi.cs3733.C23.teamD.controllers.components;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class UrgencySelectorBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  @FXML private ArrayList<String> urgency;

  public UrgencySelectorBoxController() {
    urgency = new ArrayList<String>();
    urgency = new ArrayList<>();
    urgency.add("High");
    urgency.add("Medium");
    urgency.add("Low");
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(urgency));
  }

  public String getEmployeeName() {
    return mfxFilterComboBox.getValue();
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
