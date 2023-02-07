package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequestForm;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ServiceRequestTable extends Application implements Initializable {
  @FXML private TableColumn<ServiceRequestForm, Date> date;

  @FXML private TableColumn<ServiceRequestForm, Integer> formID;

  @FXML private TableColumn<ServiceRequestForm, String> reason;
  @FXML private TableView<ServiceRequestForm> serviceTable;

  @FXML private TableColumn<ServiceRequestForm, String> requestType;

  @FXML private TableColumn<ServiceRequestForm, String> staff;

  @FXML private TableColumn<ServiceRequestForm, String> status;
  @FXML private MFXButton cancelButton;

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
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  public void tablehandling() {
    serviceTable.setEditable(true);
    ObservableList<ServiceRequestForm> requestList =
        FXCollections.observableArrayList(Ddb.createServiceList());
    if (requestList.size() != 0) {
      formID.setCellValueFactory(
          new PropertyValueFactory<ServiceRequestForm, Integer>("serviceRequestId"));
      reason.setCellValueFactory(new PropertyValueFactory<ServiceRequestForm, String>("reason"));
      status.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<ServiceRequestForm, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<ServiceRequestForm, String> param) {
              return new SimpleStringProperty(param.getValue().getStat().toString());
            }
          });
      status.setOnEditCommit(
          new EventHandler<TableColumn.CellEditEvent<ServiceRequestForm, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ServiceRequestForm, String> event) {
              ServiceRequestForm form = event.getRowValue();
              String newStatus = event.getNewValue();
              try {
                ServiceRequestForm.Status stat1 =
                    Enum.valueOf(ServiceRequestForm.Status.class, newStatus);
                form.setStat(stat1);
                Ddb.updateObj(form);
              } catch (IllegalArgumentException e) {
                e.printStackTrace();
              }
            }
          });
      status.setCellFactory(TextFieldTableCell.forTableColumn());
      staff.setCellValueFactory(
          new PropertyValueFactory<ServiceRequestForm, String>("associatedStaff"));
      date.setCellValueFactory(new PropertyValueFactory<ServiceRequestForm, Date>("dateAndTime"));
      requestType.setCellValueFactory(
          new PropertyValueFactory<ServiceRequestForm, String>("requestType"));
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
