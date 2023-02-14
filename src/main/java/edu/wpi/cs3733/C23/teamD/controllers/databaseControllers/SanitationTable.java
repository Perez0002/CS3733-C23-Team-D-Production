package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequest;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequest;
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

public class SanitationTable extends Application implements Initializable, DatabaseController {
  @FXML private BorderPane SanitationRequestBorderPane;

  @FXML private TableView<SanitationRequest> sanitationTable;

  @FXML private TableColumn<SanitationRequest, Integer> formID;

  @FXML private TableColumn<SanitationRequest, String> location;

  @FXML private TableColumn<SanitationRequest, String> reason;

  @FXML private TableColumn<SanitationRequest, Integer> bioLevel;

  @FXML private TableColumn<SanitationRequest, String> staff;
  @FXML private TableColumn<SanitationRequest, String> status;
  @FXML private TableColumn<SanitationRequest, Date> date;

  @FXML private MFXButton cancelButton;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public Node getBox() {
    return SanitationRequestBorderPane;
  }

  public void tablehandling() {
    sanitationTable.setEditable(true);
    ObservableList<SanitationRequest> requestList =
        FXCollections.observableArrayList(FDdb.getInstance().getAllSanitationRequest());
    if (requestList.size() != 0) {
      formID.setCellValueFactory(
          new PropertyValueFactory<SanitationRequest, Integer>("serviceRequestId"));
      location.setCellValueFactory(new PropertyValueFactory<SanitationRequest, String>("location"));
      reason.setCellValueFactory(new PropertyValueFactory<SanitationRequest, String>("reason"));
      bioLevel.setCellValueFactory(
          new PropertyValueFactory<SanitationRequest, Integer>("bioLevel"));
      status.setCellValueFactory(
          new Callback<
              TableColumn.CellDataFeatures<SanitationRequest, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                TableColumn.CellDataFeatures<SanitationRequest, String> param) {
              return new SimpleStringProperty(param.getValue().getStat().toString());
            }
          });

      staff.setCellValueFactory(
          new PropertyValueFactory<SanitationRequest, String>("associatedStaff"));
      status.setOnEditCommit(
          new EventHandler<TableColumn.CellEditEvent<SanitationRequest, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SanitationRequest, String> event) {
              SanitationRequest form = event.getRowValue();
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
      date.setCellValueFactory(new PropertyValueFactory<SanitationRequest, Date>("dateAndTime"));
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
