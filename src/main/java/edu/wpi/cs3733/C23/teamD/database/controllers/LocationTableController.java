package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;

public class LocationTableController extends Application
    implements Initializable, DatabaseController {
  @FXML private BorderPane LocationTableBorderPane;
  @FXML private TableView<LocationName> locationTable;
  @FXML private TableColumn<LocationName, String> longName;
  @FXML private TableColumn<LocationName, String> locationType;
  @FXML private TableColumn<LocationName, String> shortName;
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
  public void refresh() {}

  @Override
  public void deselect() {
    locationTable.getSelectionModel().clearSelection();
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

  public void tablehandling() {
    ObservableList<LocationName> locationList =
        FXCollections.observableArrayList(FDdb.getInstance().getAllLocationNames());
    longName.setCellValueFactory(new PropertyValueFactory<LocationName, String>("longName"));
    locationType.setCellValueFactory(
        new PropertyValueFactory<LocationName, String>("locationType"));
    shortName.setCellValueFactory(new PropertyValueFactory<LocationName, String>("shortName"));
    locationTable.setItems(locationList);
    locationTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    locationTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = locationTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = locationTable.getLayoutBounds().getWidth();
              for (int i = 0; i < locationTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (locationTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(locationTable.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
