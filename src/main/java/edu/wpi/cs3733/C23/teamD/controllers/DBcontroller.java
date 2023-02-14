package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

public class DBcontroller implements Initializable {
  @FXML private TableColumn<NodePathfinding, String> building;

  @FXML private TableColumn<NodePathfinding, String> floor;

  @FXML private TableColumn<NodePathfinding, String> nodeID;

  @FXML private TableView<NodePathfinding> nodeTableView;

  @FXML private TableColumn<NodePathfinding, Integer> xCoord;

  @FXML private TableColumn<NodePathfinding, Integer> yCoord;

  @FXML private TableColumn<NodePathfinding, String> shortName;

  @FXML private TableColumn<NodePathfinding, String> longName;

  @FXML private TableColumn<NodePathfinding, String> locationType;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public void tablehandling() {
    nodeTableView.setEditable(true);
    ArrayList<NodePathfinding> nodes = Ddb.nodeToPathfinding(FDdb.getInstance().getAllNodes());
    FDdb.getInstance().downloadCSV();
    Ddb.connectNodestoLocations(nodes);
    ObservableList<NodePathfinding> nodeList = FXCollections.observableArrayList(nodes);
    nodeID.setCellValueFactory(new PropertyValueFactory<NodePathfinding, String>("nodeID"));
    xCoord.setCellValueFactory(new PropertyValueFactory<NodePathfinding, Integer>("Xcoord"));
    xCoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    xCoord.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<NodePathfinding, Integer>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<NodePathfinding, Integer> event) {
            NodePathfinding node = event.getRowValue();
            int newCoord = event.getNewValue();
            node.setXcoord(newCoord);
            FDdb.getInstance().updateNode(node.pathToDB());
          }
        });

    yCoord.setCellValueFactory(new PropertyValueFactory<NodePathfinding, Integer>("Ycoord"));
    yCoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    yCoord.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<NodePathfinding, Integer>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<NodePathfinding, Integer> event) {
            NodePathfinding node = event.getRowValue();
            int newCoord = event.getNewValue();
            node.setYcoord(newCoord);
            FDdb.getInstance().updateNode(node.pathToDB());
          }
        });
    floor.setCellValueFactory(new PropertyValueFactory<NodePathfinding, String>("floor"));
    floor.setCellFactory(TextFieldTableCell.forTableColumn());
    floor.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<NodePathfinding, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<NodePathfinding, String> event) {
            NodePathfinding node = event.getRowValue();
            String newFloor = event.getNewValue();
            node.setFloor(newFloor);
            FDdb.getInstance().updateNode(node.pathToDB());
          }
        });

    building.setCellValueFactory(new PropertyValueFactory<NodePathfinding, String>("building"));
    building.setCellFactory(TextFieldTableCell.forTableColumn());
    building.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<NodePathfinding, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<NodePathfinding, String> event) {
            NodePathfinding node = event.getRowValue();
            String newBuild = event.getNewValue();
            node.setBuilding(newBuild);
            FDdb.getInstance().updateNode(node.pathToDB());
          }
        });

    longName.setCellValueFactory(new PropertyValueFactory<NodePathfinding, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<NodePathfinding, String>("shortName"));
    shortName.setCellFactory(TextFieldTableCell.forTableColumn());
    shortName.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<NodePathfinding, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<NodePathfinding, String> event) {
            LocationName name = event.getRowValue().getLocation();
            String newShort = event.getNewValue();
            name.setShortName(newShort);
            FDdb.getInstance().updateLocationName(name);
          }
        });

    locationType.setCellValueFactory(
        new PropertyValueFactory<NodePathfinding, String>("locationType"));
    locationType.setCellFactory(TextFieldTableCell.forTableColumn());
    locationType.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<NodePathfinding, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<NodePathfinding, String> event) {
            LocationName name = event.getRowValue().getLocation();
            String newType = event.getNewValue();
            name.setLocationType(newType);
            FDdb.getInstance().updateLocationName(name);
          }
        });
    nodeTableView.setItems(nodeList);
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
  }
}
