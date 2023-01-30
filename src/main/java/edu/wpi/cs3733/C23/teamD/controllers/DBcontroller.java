package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.locationName;
import java.net.URL;
import java.sql.*;
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
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class DBcontroller extends Application implements Initializable {

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

  @FXML private TableColumn<Node, String> building;

  @FXML private TableColumn<Node, String> floor;

  @FXML private TableColumn<Node, String> nodeID;

  @FXML private TableView<Node> nodeTableView;

  @FXML private TableColumn<Node, Integer> xCoord;

  @FXML private TableColumn<Node, Integer> yCoord;

  @FXML private TableColumn<locationName, String> shortName;

  @FXML private TableColumn<locationName, String> longName;

  @FXML private TableColumn<locationName, String> locationType;

  @FXML private TableView<locationName> locationNameTableView;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tableinit();
  }

  public void tableinit() {
    nodeTableView.setEditable(true);
    locationNameTableView.setEditable(true);
    Connection conn = Ddb.makeConnection();
    ObservableList<Node> nodeList = FXCollections.observableArrayList(Ddb.createJavaNodes(conn));
    ObservableList<locationName> locList =
        FXCollections.observableArrayList(Ddb.createJavaLocat(conn));
    nodeID.setCellValueFactory(new PropertyValueFactory<Node, String>("nodeID"));
    xCoord.setCellValueFactory(new PropertyValueFactory<Node, Integer>("Xcoord"));
    xCoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    yCoord.setCellValueFactory(new PropertyValueFactory<Node, Integer>("Ycoord"));
    yCoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    floor.setCellValueFactory(new PropertyValueFactory<Node, String>("floor"));
    floor.setCellFactory(TextFieldTableCell.forTableColumn());
    building.setCellValueFactory(new PropertyValueFactory<Node, String>("building"));
    building.setCellFactory(TextFieldTableCell.forTableColumn());
    longName.setCellValueFactory(new PropertyValueFactory<locationName, String>("longName"));
    longName.setCellFactory(TextFieldTableCell.forTableColumn());
    shortName.setCellValueFactory(new PropertyValueFactory<locationName, String>("shortName"));
    shortName.setCellFactory(TextFieldTableCell.forTableColumn());
    locationType.setCellValueFactory(
        new PropertyValueFactory<locationName, String>("locationType"));
    locationType.setCellFactory(TextFieldTableCell.forTableColumn());
    locationType.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<locationName, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<locationName, String> event) {}
        });
    nodeTableView.setItems(nodeList);
    locationNameTableView.setItems(locList);
  }
}
