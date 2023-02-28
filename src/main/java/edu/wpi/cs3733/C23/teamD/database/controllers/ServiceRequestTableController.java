package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Setter;

public class ServiceRequestTableController extends Application
    implements Initializable, DatabaseController {
  @FXML private BorderPane ServiceRequestTableBorderPane;
  @FXML private TableView<ServiceRequest> serviceTable;

  @FXML private TableColumn<ServiceRequest, Date> date;

  @FXML private TableColumn<ServiceRequest, Integer> formID;

  @FXML private TableColumn<ServiceRequest, String> reason;

  @FXML private TableColumn<ServiceRequest, String> requestType;

  @FXML private TableColumn<ServiceRequest, String> staff;

  @FXML private TableColumn<ServiceRequest, String> status;

  @Setter private AddFormController addFormController;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  @Override
  public boolean delete() {
    if (serviceTable.getSelectionModel().getSelectedItem() != null) {
      FDdb.getInstance().deleteServiceRequest(serviceTable.getSelectionModel().getSelectedItem());
      refresh();
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void refresh() {
    serviceTable.setEditable(true);
    ObservableList<ServiceRequest> requestList =
        FXCollections.observableArrayList(FDdb.getInstance().getAllGenericServiceRequests());
    if (requestList.size() != 0) {
      formID.setCellValueFactory(
          new PropertyValueFactory<ServiceRequest, Integer>("serviceRequestId"));
      reason.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("reason"));
      status.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<ServiceRequest, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<ServiceRequest, String> param) {
              return new SimpleStringProperty(param.getValue().getStat().toString());
            }
          });
      staff.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<ServiceRequest, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<ServiceRequest, String> param) {
              Employee e = FDdb.getInstance().getEmployee(param.getValue().getAssociatedStaff());
              String employee = e.getFirstName() + " " + e.getLastName();
              return new SimpleStringProperty(employee);
            }
          });
      staff.setCellFactory(TextFieldTableCell.forTableColumn());
      date.setCellValueFactory(new PropertyValueFactory<ServiceRequest, Date>("dateAndTime"));
      requestType.setCellValueFactory(
          new PropertyValueFactory<ServiceRequest, String>("serviceRequestType"));
    }
    serviceTable.setItems(requestList);
    serviceTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    serviceTable.getColumns().stream()
        .forEach(
            (column) -> {
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = serviceTable.getLayoutBounds().getWidth();
              for (int i = 0; i < serviceTable.getItems().size(); i++) {
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

  @Override
  public void deselect() {
    serviceTable.getSelectionModel().clearSelection();
  }

  public Node getBox() {
    return ServiceRequestTableBorderPane;
  }

  @FXML
  public void getSelectedRow() {
    addFormController.dataToChange(serviceTable.getSelectionModel().getSelectedItem());
  }

  public void setVisible() {
    if (ServiceRequestTableBorderPane.isVisible()) {
      ServiceRequestTableBorderPane.setVisible(false);
    } else {
      ServiceRequestTableBorderPane.setVisible(true);
    }
  }

  public void tablehandling() {
    serviceTable.setEditable(true);
    ObservableList<ServiceRequest> requestList =
        FXCollections.observableArrayList(FDdb.getInstance().getAllGenericServiceRequests());
    if (requestList.size() != 0) {
      formID.setCellValueFactory(
          new PropertyValueFactory<ServiceRequest, Integer>("serviceRequestId"));
      reason.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("reason"));
      status.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<ServiceRequest, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<ServiceRequest, String> param) {
              return new SimpleStringProperty(param.getValue().getStat().toString());
            }
          });
      status.setCellFactory(TextFieldTableCell.forTableColumn());
      staff.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<ServiceRequest, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<ServiceRequest, String> param) {
              Employee e = FDdb.getInstance().getEmployee(param.getValue().getAssociatedStaff());
              String employee = e.getFirstName() + " " + e.getLastName();
              return new SimpleStringProperty(employee);
            }
          });
      staff.setCellFactory(TextFieldTableCell.forTableColumn());
      date.setCellValueFactory(new PropertyValueFactory<ServiceRequest, Date>("dateAndTime"));
      requestType.setCellValueFactory(
          new PropertyValueFactory<ServiceRequest, String>("serviceRequestType"));
    }
    serviceTable.setItems(requestList);
    serviceTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    serviceTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = serviceTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = serviceTable.getLayoutBounds().getWidth();
              for (int i = 0; i < serviceTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (serviceTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(serviceTable.getMaxWidth() / size);
              if (currentMax < 350) {
                column.setMinWidth(currentMax);
              }
            });
  }
}
