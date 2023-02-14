package edu.wpi.cs3733.C23.teamD.controllers.components;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class EmployeeDropdownComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  @FXML private ArrayList<String> employeeNames;

  public EmployeeDropdownComboBoxController() {
    employeeNames = new ArrayList<String>();
    employeeNames.add("Annie");
    employeeNames.add("Ari");
    employeeNames.add("Jonathon");
    employeeNames.add("Wyatt");
    employeeNames.add("Bryce");
    employeeNames.add("Abigail");
    employeeNames.add("Liv");
    employeeNames.add("Mike");
    employeeNames.add("Theo");
    employeeNames.add("Gibson");
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(employeeNames));
  }

  public String getEmployeeName() {
    return mfxFilterComboBox.getValue();
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
