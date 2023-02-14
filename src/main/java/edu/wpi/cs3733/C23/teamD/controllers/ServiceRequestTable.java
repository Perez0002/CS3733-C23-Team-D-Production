package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequest;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ServiceRequestTable extends Application implements Initializable {
  @FXML private TableColumn<ServiceRequest, Date> date;

  @FXML private TableColumn<ServiceRequest, Integer> formID;

  @FXML private TableColumn<ServiceRequest, String> reason;
  @FXML private TableView<ServiceRequest> serviceTable;

  @FXML private TableColumn<ServiceRequest, String> requestType;

  @FXML private TableColumn<ServiceRequest, String> staff;

  @FXML private TableColumn<ServiceRequest, String> status;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/SanitationRequestTable.fxml"));
    primaryStage.setTitle("SanitationRequestTable");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public void tablehandling() {
    serviceTable.setEditable(true);
    ObservableList<ServiceRequest> requestList =
        FXCollections.observableArrayList(Ddb.createServiceList());
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
      status.setOnEditCommit(
          new EventHandler<TableColumn.CellEditEvent<ServiceRequest, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ServiceRequest, String> event) {
              ServiceRequest form = event.getRowValue();
              String newStatus = event.getNewValue();
              try {
                ServiceRequest.Status stat1 = Enum.valueOf(ServiceRequest.Status.class, newStatus);
                form.setStat(stat1);
                Ddb.updateObj(form);
              } catch (IllegalArgumentException e) {
                e.printStackTrace();
              }
            }
          });
      status.setCellFactory(TextFieldTableCell.forTableColumn());
      staff.setCellValueFactory(
          new PropertyValueFactory<ServiceRequest, String>("associatedStaff"));
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
              column.setMinWidth(currentMax);
            });
  }
}
