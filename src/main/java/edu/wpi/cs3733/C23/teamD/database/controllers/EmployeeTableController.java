package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import lombok.Setter;

public class EmployeeTableController implements DatabaseController {

  @Setter private AddFormController addFormController;
  @FXML private BorderPane LocationTableBorderPane;

  @FXML private TableColumn<Employee, String> email;

  @FXML private TableColumn<Employee, String> employeeType;

  @FXML private TableColumn<Employee, String> firstName;

  @FXML private TableColumn<Employee, String> lastName;

  @FXML private TableView<Employee> employeeTable;

  public boolean delete() {
    if (employeeTable.getSelectionModel().getSelectedItem() != null) {
      FDdb.getInstance().deleteEmployee(employeeTable.getSelectionModel().getSelectedItem());
      refresh();
      return true;
    } else {
      return false;
    }
  }

  public void deselect() {
    employeeTable.getSelectionModel().clearSelection();
  }

  public void refresh() {
    ObservableList<Employee> employees =
        FXCollections.observableArrayList(FDdb.getInstance().getAllEmployees());
    firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
    lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
    employeeType.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeType"));
    email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
    employeeTable.setItems(employees);
    employeeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    employeeTable.getColumns().stream()
        .forEach(
            (column) -> {
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = employeeTable.getLayoutBounds().getWidth();
              for (int i = 0; i < employeeTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
            });
  }

  @FXML
  public void getSelectedRow() {
    addFormController.dataToChange(employeeTable.getSelectionModel().getSelectedItem());
  }

  public Node getBox() {
    return LocationTableBorderPane;
  }

  public void setVisible() {
    if (LocationTableBorderPane.isVisible()) {
      LocationTableBorderPane.setVisible(false);
    } else {
      LocationTableBorderPane.setVisible(true);
    }
  }

  public void initialize() {
    tablehandling();
  }

  public void tablehandling() {
    ObservableList<Employee> employees =
        FXCollections.observableArrayList(FDdb.getInstance().getAllEmployees());
    System.out.println(employees.size());
    firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
    lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
    employeeType.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeType"));
    email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
    employeeTable.setItems(employees);
    employeeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    employeeTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = employeeTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = employeeTable.getLayoutBounds().getWidth();
              for (int i = 0; i < employeeTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (employeeTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(employeeTable.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
