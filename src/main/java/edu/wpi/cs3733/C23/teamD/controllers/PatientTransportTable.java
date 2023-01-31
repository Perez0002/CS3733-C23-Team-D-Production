package edu.wpi.cs3733.C23.teamD.controllers;

import static javafx.application.Application.launch;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.PatientTransportData;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

  @FXML private TableColumn<PatientTransportData, String> staff;

  @FXML private TableColumn<PatientTransportData, String> startRoom;

  @FXML private TableColumn<PatientTransportData, String> status;

  @FXML private TableColumn<PatientTransportData, String> sendTo;

  @FXML private MFXButton cancelButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Connection con = Ddb.makeConnection(); /*
    ArrayList<String> equipment = new ArrayList<>();
    equipment.add("defib");
    equipment.add("giga");
    String[] giga3 = new String[2];
    giga3[0] = "giga";
    giga3[1] = "giga2";
    PatientTransportData giga =
        new PatientTransportData(
            "GigaPatientofHell",
            1,
            "room1",
            "room2",
            equipment,
            "cause i feel like it",
            giga3,
            PatientTransportData.status.DONE,
            "giga4");
    Ddb.insertNewForm(con, giga);*/
    tablehandling(con);
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  public void tablehandling(Connection conn) {
    ObservableList<PatientTransportData> transportList =
        FXCollections.observableArrayList(Ddb.getPatientTransportData(conn));
    if (transportList.size() != 0) {
      endRoom.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, String>("endRoom"));

      equipment.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<PatientTransportData, String>,
              ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<PatientTransportData, String> param) {
              ArrayList<String> equip = param.getValue().getEquipment();
              if (equip != null && equip.size() != 0)
                return new SimpleStringProperty(String.join(",", equip));
              else return new SimpleStringProperty("<no value>");
            }
          });
      formID.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, Integer>("patientTransportID"));
      patientID.setCellValueFactory(
          new PropertyValueFactory<PatientTransportData, String>("patientID"));
      reason.setCellValueFactory(new PropertyValueFactory<PatientTransportData, String>("reason"));
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
      staff.setCellValueFactory(new PropertyValueFactory<PatientTransportData, String>("staff"));
      sendTo.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<PatientTransportData, String>,
              ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<PatientTransportData, String> param) {
              String[] sendTo = param.getValue().getSendTo();
              if (sendTo != null)
                return new SimpleStringProperty(Arrays.toString(param.getValue().getSendTo()));
              else return new SimpleStringProperty("<no value>");
            }
          });
      patientTable.setItems(transportList);
    }
  }
}
