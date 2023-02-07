package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Application;
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
import javafx.util.converter.IntegerStringConverter;

public class DBcontroller extends Application implements Initializable {
  @FXML private TableView<Move> moveTable;
  @FXML private TableColumn<Move, String> moveNodeID;
  @FXML private TableColumn<Move, Date> moveDate;
  @FXML private TableColumn<Move, String> moveLongName;
  @FXML private TableColumn<Node, String> building;

  @FXML private TableColumn<Node, String> floor;

  @FXML private TableColumn<Node, String> nodeID;

  @FXML private TableView<Node> nodeTableView;

  @FXML private TableColumn<Node, Integer> xCoord;

  @FXML private TableColumn<Node, Integer> yCoord;

  @FXML private TableColumn<Node, String> shortName;

  @FXML private TableColumn<Node, String> longName;

  @FXML private TableColumn<Node, String> locationType;

  @FXML private TableView<Edge> edgeTable;

  @FXML private TableColumn<Edge, String> edgeID;
  @FXML private TableColumn<Edge, String> startNode;
  @FXML private TableColumn<Edge, String> endNode;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/DBApp.fxml"));
    primaryStage.setTitle("NodeTable");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public void tablehandling() {
    nodeTableView.setEditable(true);
    ArrayList<Node> nodes = Ddb.createJavaNodes();
    Ddb.connectNodestoLocations(nodes);
    ObservableList<Node> nodeList = FXCollections.observableArrayList(nodes);
    ObservableList<Move> moveList = FXCollections.observableArrayList(Ddb.createJavaMoves());
    ObservableList<Edge> edgeList = FXCollections.observableArrayList(Ddb.createJavaEdges(nodes));
    nodeID.setCellValueFactory(new PropertyValueFactory<Node, String>("nodeID"));
    xCoord.setCellValueFactory(new PropertyValueFactory<Node, Integer>("Xcoord"));
    xCoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    xCoord.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, Integer>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, Integer> event) {
            Node node = event.getRowValue();
            int newCoord = event.getNewValue();
            node.setXcoord(newCoord);
            Ddb.updateObj(node);
          }
        });

    yCoord.setCellValueFactory(new PropertyValueFactory<Node, Integer>("Ycoord"));
    yCoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    yCoord.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, Integer>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, Integer> event) {
            Node node = event.getRowValue();
            int newCoord = event.getNewValue();
            node.setYcoord(newCoord);
            String stmnt = "UPDATE Node SET yCoord = ? WHERE nodeID = ?";
            Ddb.updateObj(node);
          }
        });
    floor.setCellValueFactory(new PropertyValueFactory<Node, String>("floor"));
    floor.setCellFactory(TextFieldTableCell.forTableColumn());
    floor.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> event) {
            Node node = event.getRowValue();
            String newFloor = event.getNewValue();
            node.setFloor(newFloor);
            String stmnt = "UPDATE Node SET floor = ? WHERE nodeID = ?";
            Ddb.updateObj(node);
          }
        });

    building.setCellValueFactory(new PropertyValueFactory<Node, String>("building"));
    building.setCellFactory(TextFieldTableCell.forTableColumn());
    building.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> event) {
            Node node = event.getRowValue();
            String newBuild = event.getNewValue();
            node.setBuilding(newBuild);
            String stmnt = "UPDATE Node SET building = ? WHERE nodeID = ?";
            Ddb.updateObj(node);
          }
        });

    longName.setCellValueFactory(new PropertyValueFactory<Node, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<Node, String>("shortName"));
    shortName.setCellFactory(TextFieldTableCell.forTableColumn());
    shortName.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> event) {
            LocationName name = event.getRowValue().getLocation();
            String newShort = event.getNewValue();
            name.setShortName(newShort);
            String stmnt = "UPDATE locationName SET shortName = ? WHERE longName = ?";
            Ddb.updateObj(name);
          }
        });

    locationType.setCellValueFactory(new PropertyValueFactory<Node, String>("locationType"));
    locationType.setCellFactory(TextFieldTableCell.forTableColumn());
    locationType.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<Node, String> event) {
            LocationName name = event.getRowValue().getLocation();
            String newType = event.getNewValue();
            name.setLocationType(newType);
            String stmnt = "UPDATE locationName SET locationType = ? WHERE longName = ?";
            Ddb.updateObj(name);
          }
        });
    moveNodeID.setCellValueFactory(new PropertyValueFactory<Move, String>("nodeID"));
    moveDate.setCellValueFactory(new PropertyValueFactory<Move, Date>("moveDate"));
    moveLongName.setCellValueFactory(new PropertyValueFactory<Move, String>("longName"));
    edgeID.setCellValueFactory(new PropertyValueFactory<Edge, String>("edgeID"));
    startNode.setCellValueFactory(new PropertyValueFactory<Edge, String>("fromNodeID"));
    endNode.setCellValueFactory(new PropertyValueFactory<Edge, String>("toNodeID"));
    nodeTableView.setItems(nodeList);
    edgeTable.setItems(edgeList);
    moveTable.setItems(moveList);
    nodeTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    nodeTableView.getColumns().stream()
        .forEach(
            (column) -> {
              double size = nodeTableView.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = nodeTableView.getLayoutBounds().getWidth();
              for (int i = 0; i < nodeTableView.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (nodeTableView.getMaxWidth() / size > currentMax)
                column.setMinWidth(nodeTableView.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
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
    moveTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    moveTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = moveTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = moveTable.getLayoutBounds().getWidth();
              for (int i = 0; i < moveTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (moveTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(moveTable.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
