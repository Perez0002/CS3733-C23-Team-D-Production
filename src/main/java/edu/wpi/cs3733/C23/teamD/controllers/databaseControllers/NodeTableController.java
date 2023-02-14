package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Application;
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
import javafx.util.converter.IntegerStringConverter;

public class NodeTableController extends Application implements Initializable, DatabaseController {
  @FXML private BorderPane NodeTableBorderPane;
  @FXML private TableView<edu.wpi.cs3733.C23.teamD.entities.Node> nodeTable;
  @FXML private TableColumn<edu.wpi.cs3733.C23.teamD.entities.Node, String> building;

  @FXML private TableColumn<edu.wpi.cs3733.C23.teamD.entities.Node, String> floor;

  @FXML private TableColumn<edu.wpi.cs3733.C23.teamD.entities.Node, String> nodeID;

  @FXML private TableColumn<edu.wpi.cs3733.C23.teamD.entities.Node, Integer> xCoord;

  @FXML private TableColumn<edu.wpi.cs3733.C23.teamD.entities.Node, Integer> yCoord;

  @FXML private TableColumn<edu.wpi.cs3733.C23.teamD.entities.Node, String> shortName;

  @FXML private TableColumn<edu.wpi.cs3733.C23.teamD.entities.Node, String> longName;

  @FXML private TableColumn<edu.wpi.cs3733.C23.teamD.entities.Node, String> locationType;

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
    return NodeTableBorderPane;
  }

  public void setVisible() {
    if (NodeTableBorderPane.isVisible()) {
      NodeTableBorderPane.setVisible(false);
    } else {
      NodeTableBorderPane.setVisible(true);
    }
  }

  public void tablehandling() {
    nodeTable.setEditable(true);
    ArrayList<edu.wpi.cs3733.C23.teamD.entities.Node> nodes = FDdb.getInstance().getAllNodes();
    Ddb.connectNodestoLocations(nodes);
    ObservableList<edu.wpi.cs3733.C23.teamD.entities.Node> nodeList =
        FXCollections.observableArrayList(nodes);
    nodeID.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.cs3733.C23.teamD.entities.Node, String>("nodeID"));
    xCoord.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.cs3733.C23.teamD.entities.Node, Integer>("xcoord"));
    xCoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    xCoord.setOnEditCommit(
        new EventHandler<
            TableColumn.CellEditEvent<edu.wpi.cs3733.C23.teamD.entities.Node, Integer>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<edu.wpi.cs3733.C23.teamD.entities.Node, Integer> event) {
            edu.wpi.cs3733.C23.teamD.entities.Node node = event.getRowValue();
            int newCoord = event.getNewValue();
            node.setXcoord(newCoord);
            FDdb.getInstance().saveNode(node);
          }
        });

    yCoord.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.cs3733.C23.teamD.entities.Node, Integer>("ycoord"));
    yCoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    yCoord.setOnEditCommit(
        new EventHandler<
            TableColumn.CellEditEvent<edu.wpi.cs3733.C23.teamD.entities.Node, Integer>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<edu.wpi.cs3733.C23.teamD.entities.Node, Integer> event) {
            edu.wpi.cs3733.C23.teamD.entities.Node node = event.getRowValue();
            int newCoord = event.getNewValue();
            node.setYcoord(newCoord);
            FDdb.getInstance().saveNode(node);
          }
        });
    floor.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.cs3733.C23.teamD.entities.Node, String>("floor"));
    floor.setCellFactory(TextFieldTableCell.forTableColumn());
    floor.setOnEditCommit(
        new EventHandler<
            TableColumn.CellEditEvent<edu.wpi.cs3733.C23.teamD.entities.Node, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<edu.wpi.cs3733.C23.teamD.entities.Node, String> event) {
            edu.wpi.cs3733.C23.teamD.entities.Node node = event.getRowValue();
            String newFloor = event.getNewValue();
            node.setFloor(newFloor);
            FDdb.getInstance().saveNode(node);
          }
        });

    building.setCellValueFactory(
        new PropertyValueFactory<edu.wpi.cs3733.C23.teamD.entities.Node, String>("building"));
    building.setCellFactory(TextFieldTableCell.forTableColumn());
    building.setOnEditCommit(
        new EventHandler<
            TableColumn.CellEditEvent<edu.wpi.cs3733.C23.teamD.entities.Node, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<edu.wpi.cs3733.C23.teamD.entities.Node, String> event) {
            edu.wpi.cs3733.C23.teamD.entities.Node node = event.getRowValue();
            String newBuild = event.getNewValue();
            node.setBuilding(newBuild);
            FDdb.getInstance().saveNode(node);
          }
        });
    nodeTable.setItems(nodeList);
    nodeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    nodeTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = nodeTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = nodeTable.getLayoutBounds().getWidth();
              for (int i = 0; i < nodeTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (nodeTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(nodeTable.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
