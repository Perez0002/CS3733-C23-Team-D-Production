package edu.wpi.cs3733.C23.teamD.controllers;

import static javafx.application.Application.launch;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequestData;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.application.Application;
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

public class SanitationTable extends Application implements Initializable {

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

  @FXML private TableColumn<SanitationRequestData, Integer> formID;

  @FXML private TableColumn<SanitationRequestData, String> location;

  @FXML private TableColumn<SanitationRequestData, String> reason;

  @FXML private TableColumn<SanitationRequestData, Integer> biolevel;

  @FXML private TableView<SanitationRequestData> sanitationTable;

  @FXML private TableColumn<SanitationRequestData, String> staff;
  @FXML private TableColumn<SanitationRequestData, SanitationRequestData.status> status;

  @FXML private MFXButton cancelButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  public void tablehandling() {
    Connection conn = Ddb.makeConnection();
    ObservableList<SanitationRequestData> requestList =
        FXCollections.observableArrayList(SanitationRequestController.getSanitationList());
    if (requestList.size() != 0) {
      formID.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, Integer>("formID"));
      location.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, String>("location"));
      reason.setCellValueFactory(new PropertyValueFactory<SanitationRequestData, String>("reason"));
      biolevel.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, Integer>("biolevel"));
      status.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, SanitationRequestData.status>("stat"));
      staff.setCellValueFactory(new PropertyValueFactory<SanitationRequestData, String>("staff"));
    }
  }
}
