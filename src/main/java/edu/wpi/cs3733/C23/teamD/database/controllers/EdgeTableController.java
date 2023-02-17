package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
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

public class EdgeTableController extends Application implements Initializable, DatabaseController {
  @FXML private BorderPane EdgeTableBorderPane;
  @FXML private TableView<Edge> edgeTable;
  @FXML private TableColumn<Edge, String> edgeID;
  @FXML private TableColumn<Edge, String> startNode;
  @FXML private TableColumn<Edge, String> endNode;

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
    return EdgeTableBorderPane;
  }

  public void setVisible() {
    if (EdgeTableBorderPane.isVisible()) {
      EdgeTableBorderPane.setVisible(false);
    } else {
      EdgeTableBorderPane.setVisible(true);
    }
  }

  public void tablehandling() {
    ObservableList<Edge> edgeList =
        FXCollections.observableArrayList(FDdb.getInstance().getAllEdges());
    edgeID.setCellValueFactory(new PropertyValueFactory<Edge, String>("edgeID"));
    startNode.setCellValueFactory(new PropertyValueFactory<Edge, String>("fromNodeID"));
    endNode.setCellValueFactory(new PropertyValueFactory<Edge, String>("toNodeID"));
    edgeTable.setItems(edgeList);
    edgeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    edgeTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = edgeTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = edgeTable.getLayoutBounds().getWidth();
              for (int i = 0; i < edgeTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (edgeTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(edgeTable.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
