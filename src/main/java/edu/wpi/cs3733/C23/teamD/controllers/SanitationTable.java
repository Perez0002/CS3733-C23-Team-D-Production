package edu.wpi.cs3733.C23.teamD.controllers;

import static javafx.application.Application.launch;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequestData;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

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

  @FXML private TableColumn<SanitationRequestData, Integer> bioLevel;

  @FXML private TableView<SanitationRequestData> sanitationTable;

  @FXML private TableColumn<SanitationRequestData, String> staff;
  @FXML private TableColumn<SanitationRequestData, String> status;
  @FXML private TableColumn<SanitationRequestData, Date> date;

  @FXML private MFXButton cancelButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  public void tablehandling() {
    sanitationTable.setEditable(true);
    ObservableList<SanitationRequestData> requestList =
        FXCollections.observableArrayList(Ddb.createSanitationRequestList());
    if (requestList.size() != 0) {
      formID.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, Integer>("serviceRequestId"));
      location.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, String>("location"));
      reason.setCellValueFactory(new PropertyValueFactory<SanitationRequestData, String>("reason"));
      bioLevel.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, Integer>("bioLevel"));
      status.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<SanitationRequestData, String>,
              ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<SanitationRequestData, String> param) {
              return new SimpleStringProperty(param.getValue().getStat().toString());
            }
          });

      staff.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, String>("associatedStaff"));
      status.setOnEditCommit(
          new EventHandler<TableColumn.CellEditEvent<SanitationRequestData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SanitationRequestData, String> event) {
              SanitationRequestData form = event.getRowValue();
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
      date.setCellValueFactory(
          new PropertyValueFactory<SanitationRequestData, Date>("dateAndTime"));
    }
    sanitationTable.setItems(requestList);
    sanitationTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    sanitationTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = sanitationTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = sanitationTable.getLayoutBounds().getWidth();
              for (int i = 0; i < sanitationTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (sanitationTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(sanitationTable.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
