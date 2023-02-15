package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.PatientTransportRequest;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequest;
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
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PatientTransportTable extends Application implements Initializable {

  @FXML private TableColumn<PatientTransportRequest, String> endRoom;

  @FXML private TableColumn<PatientTransportRequest, String> urgency;

  @FXML private TableColumn<PatientTransportRequest, Integer> formID;

  @FXML private TableView<PatientTransportRequest> patientTable;

  @FXML private TableColumn<PatientTransportRequest, String> patientID;

  @FXML private TableColumn<PatientTransportRequest, String> reason;
  @FXML private TableColumn<PatientTransportRequest, String> startRoom;

  @FXML private TableColumn<PatientTransportRequest, String> status;

  @FXML private TableColumn<PatientTransportRequest, String> sendTo;

  @FXML private TableColumn<PatientTransportRequest, Date> date;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public void tablehandling() {
    ObservableList<PatientTransportRequest> transportList =
        FXCollections.observableArrayList(FDdb.getInstance().getAllPatientTransportRequests());
    patientTable.setEditable(true);
    if (transportList.size() != 0) {
      endRoom.setCellValueFactory(
          new PropertyValueFactory<PatientTransportRequest, String>("endRoom"));
      urgency.setCellValueFactory(
          new PropertyValueFactory<PatientTransportRequest, String>("urgency"));
      formID.setCellValueFactory(
          new PropertyValueFactory<PatientTransportRequest, Integer>("serviceRequestId"));
      patientID.setCellValueFactory(
          new PropertyValueFactory<PatientTransportRequest, String>("patientID"));
      reason.setCellValueFactory(
          new PropertyValueFactory<PatientTransportRequest, String>("reason"));
      date.setCellValueFactory(
          new PropertyValueFactory<PatientTransportRequest, Date>("dateAndTime"));
      startRoom.setCellValueFactory(
          new PropertyValueFactory<PatientTransportRequest, String>("startRoom"));
      status.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<PatientTransportRequest, String>,
              ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<PatientTransportRequest, String> param) {
              return new SimpleStringProperty(param.getValue().getStat().toString());
            }
          });
      status.setOnEditCommit(
          new EventHandler<TableColumn.CellEditEvent<PatientTransportRequest, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<PatientTransportRequest, String> event) {
              PatientTransportRequest form = event.getRowValue();
              String newStatus = event.getNewValue();
              try {
                ServiceRequest.Status stat1 = Enum.valueOf(ServiceRequest.Status.class, newStatus);
                form.setStat(stat1);
                FDdb.getInstance().updateServiceRequest(form);
              } catch (IllegalArgumentException e) {
                e.printStackTrace();
              }
            }
          });
      status.setCellFactory(TextFieldTableCell.forTableColumn());
      sendTo.setCellValueFactory(
          new PropertyValueFactory<PatientTransportRequest, String>("associatedStaff"));
      patientTable.setItems(transportList);
      patientTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
      patientTable.getColumns().stream()
          .forEach(
              (column) -> {
                double size = patientTable.getColumns().size();
                Text serviceTableValue = new Text(column.getText());
                Object cellData;
                double currentMax = patientTable.getLayoutBounds().getWidth();
                for (int i = 0; i < patientTable.getItems().size(); i++) {
                  cellData = column.getCellData(i);
                  if (cellData != null) {
                    serviceTableValue = new Text(cellData.toString());
                    double width = serviceTableValue.getLayoutBounds().getWidth();
                    if (width > currentMax) {
                      currentMax = width;
                    }
                  }
                }
                if (patientTable.getMaxWidth() / size > currentMax)
                  column.setMinWidth(patientTable.getMaxWidth() / size);
                column.setMinWidth(currentMax);
              });
    }
  }
}
