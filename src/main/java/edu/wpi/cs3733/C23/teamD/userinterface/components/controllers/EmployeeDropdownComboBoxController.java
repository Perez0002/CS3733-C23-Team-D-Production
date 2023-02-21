package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class EmployeeDropdownComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  TreeMap<String, Employee> employeeToEmployeeName;

  public EmployeeDropdownComboBoxController() {
    employeeToEmployeeName = new TreeMap<>();
    ArrayList<Employee> employees = FDdb.getInstance().getAllEmployees();
    for (Employee e : employees) {
      Employee employee = e;
      String firstName = e.getFirstName();
      String lastName = e.getLastName();
      String fullname = firstName + " " + lastName;
      employeeToEmployeeName.put(fullname, employee);
    }
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(employeeToEmployeeName.keySet()));
  }

  public String getEmployeeName() {
    return mfxFilterComboBox.getText();
  }

  public Employee getEmployee() {
    return employeeToEmployeeName.get(mfxFilterComboBox.getValue());
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
