package edu.wpi.cs3733.C23.teamD.controllers.components;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.Employee;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class EmployeeDropdownComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  @FXML private ArrayList<String> employeeNames;

  public EmployeeDropdownComboBoxController() {
    employeeNames = new ArrayList<>();
    ArrayList<Employee> employees = FDdb.getInstance().getAllEmployees();
    for (Employee e : employees) {
      String firstName = e.getFirstName();
      String lastName = e.getLastName();
      employeeNames.add(firstName + " " + lastName);
    }
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
