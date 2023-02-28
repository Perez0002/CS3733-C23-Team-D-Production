package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeTypeComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import lombok.Setter;

public class AddEmployeeController implements AddFormController<Employee> {

  @Setter DatabaseController databaseController;
  @FXML private MFXButton deleteButton;

  @FXML private MFXTextField emailTextField;

  @FXML private Parent employeeTypeComboBox;
  @FXML EmployeeTypeComboBoxController employeeTypeComboBoxController;

  @FXML private Text errorText;

  @FXML private MFXTextField firstNameTextField;

  @FXML private MFXTextField lastNameTextField;

  @FXML private MFXPasswordField passwordField;

  @FXML private MFXButton submitButton;

  @FXML private Label titleLabel;
  private Employee currentEmployee;

  @FXML
  public void initialize() {
    deleteButton.setOnMouseClicked(event -> deleteRow());
  }

  private void deleteRow() {
    databaseController.delete();
    ToastController.makeText(
        "the employee has been deleted!",
        1500,
        50,
        100,
        (int) Screen.getPrimary().getBounds().getWidth() - 375,
        (int) Screen.getPrimary().getBounds().getHeight() - 275);
    dataToChange(null);
  }

  @FXML
  public void submit() {
    if (checkFields()) {
      if (currentEmployee == null) {

        Employee employee = new Employee();

        employee.setFirstName(firstNameTextField.getText());
        employee.setLastName(lastNameTextField.getText());
        employee.setEmployeeType(employeeTypeComboBoxController.getText());
        employee.setEmail(emailTextField.getText());
        employee.setPassword(passwordField.getText());
        FDdb.getInstance().saveEmployee(employee);
        databaseController.refresh();
        dataToChange(null);
        ToastController.makeText(
            "Your edge has been added!",
            1500,
            50,
            100,
            (int) Screen.getPrimary().getBounds().getWidth() - 375,
            (int) Screen.getPrimary().getBounds().getHeight() - 275);
      } else {
        currentEmployee.setFirstName(firstNameTextField.getText());
        currentEmployee.setLastName(lastNameTextField.getText());
        currentEmployee.setPassword(passwordField.getText());
        currentEmployee.setEmail(emailTextField.getText());
        currentEmployee.setEmployeeType(employeeTypeComboBoxController.getText());

        FDdb.getInstance().updateEmployee(currentEmployee);
        clearFields();
        ToastController.makeText(
            "The Employee has been changed!",
            1500,
            50,
            100,
            (int) Screen.getPrimary().getBounds().getWidth() - 375,
            (int) Screen.getPrimary().getBounds().getHeight() - 275);
        databaseController.refresh();
      }
    } else {
      errorText.setVisible(true);
    }
  }

  private boolean checkFields() {
    return !(firstNameTextField.getText().isEmpty()
        || lastNameTextField.getText().isEmpty()
        || passwordField.getText().isEmpty()
        || emailTextField.getText().isEmpty()
        || employeeTypeComboBoxController.getText() == null);
  }

  @FXML
  public void clearFields() {
    errorText.setVisible(false);
    firstNameTextField.clear();
    lastNameTextField.clear();
    passwordField.clear();
    emailTextField.clear();
    employeeTypeComboBoxController.clearForm();
  }

  @Override
  public void dataToChange(Employee employee) {
    currentEmployee = employee;
    if (employee == null) {
      submitButton.setText("Add Employee");
      titleLabel.setText("Add an Employee");
      clearFields();
      deleteButton.setDisable(true);
    } else {
      deleteButton.setDisable(false);
      submitButton.setText("Submit Changes");
      titleLabel.setText("Change an Employee");
      firstNameTextField.setText(employee.getFirstName());
      lastNameTextField.setText(employee.getLastName());
      passwordField.setText(employee.getPassword());
      emailTextField.setText(employee.getEmail());
      employeeTypeComboBoxController.setText(employee.getEmployeeType());
    }
  }
}
