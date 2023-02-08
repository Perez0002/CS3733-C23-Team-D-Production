package edu.wpi.cs3733.C23.teamD.controllers;

import static javafx.application.Application.launch;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.PatientTransportData;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequestForm;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PatientTransportTable extends Application implements Initializable {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root =
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/PatientTransportTable.fxml"));
    primaryStage.setTitle("PatientTransportTable");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  @FXML private TableColumn<PatientTransportData, String> endRoom;

  @FXML private TableColumn<PatientTransportData, String> equipment;

  @FXML private TableColumn<PatientTransportData, Integer> formID;

  @FXML private TableView<PatientTransportData> patientTable;

  @FXML private TableColumn<PatientTransportData, String> patientID;

  @FXML private TableColumn<PatientTransportData, String> reason;
  @FXML private TableColumn<PatientTransportData, String> startRoom;

  @FXML private TableColumn<PatientTransportData, String> status;

  @FXML private TableColumn<PatientTransportData, String> sendTo;

  @FXML private TableColumn<PatientTransportData, Date> date;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public void tablehandling() {
    ObservableList<PatientTransportData> transportList =
        FXCollections.observableArrayList(Ddb.getPatientTransportData());
    patientTable.setEditable(true);
    if (transportList.size() != 0) {
      endRoom.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, String>("endRoom"));
      equipment.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, String>("equipment"));
      formID.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, Integer>("serviceRequestId"));
      patientID.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, String>("patientID"));
      reason.setCellValueFactory(new PropertyValueFactory<PatientTransportData, String>("reason"));
      date.setCellValueFactory(new PropertyValueFactory<PatientTransportData, Date>("dateAndTime"));
      startRoom.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, String>("startRoom"));
      status.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<PatientTransportData, String>,
              ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<PatientTransportData, String> param) {
              return new SimpleStringProperty(param.getValue().getStat().toString());
            }
          });
      status.setOnEditCommit(
          new EventHandler<TableColumn.CellEditEvent<PatientTransportData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<PatientTransportData, String> event) {
              PatientTransportData form = event.getRowValue();
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
      sendTo.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, String>("associatedStaff"));
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
