package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
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

import java.net.URL;
import java.util.ResourceBundle;

public class LocationTableController extends Application
    implements Initializable, DatabaseController {
  @FXML private BorderPane LocationTableBorderPane;
  @FXML private TableView<LocationName> locationTable;
  @FXML private TableColumn<LocationName, String> longName;
  @FXML private TableColumn<LocationName, String> locationType;
  @FXML private TableColumn<LocationName, String> shortName;

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
