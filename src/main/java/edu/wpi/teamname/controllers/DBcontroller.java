package edu.wpi.teamname.controllers;

import edu.wpi.teamname.*;
import java.net.URL;
import java.sql.*;
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

public class DBcontroller extends Application implements Initializable {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/DBApp.fxml"));
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
    Connection conn = Ddb.makeConnection();
    ObservableList<Node> nodeList = FXCollections.observableArrayList(Ddb.createJavaNodes(conn));
    ObservableList<locationName> locList =
        FXCollections.observableArrayList(Ddb.createJavaLocat(conn));
    nodeID.setCellValueFactory(new PropertyValueFactory<Node, String>("nodeID"));
    xCoord.setCellValueFactory(new PropertyValueFactory<Node, Integer>("Xcoord"));
    yCoord.setCellValueFactory(new PropertyValueFactory<Node, Integer>("Ycoord"));
    floor.setCellValueFactory(new PropertyValueFactory<Node, String>("floor"));
    building.setCellValueFactory(new PropertyValueFactory<Node, String>("building"));
    longName.setCellValueFactory(new PropertyValueFactory<locationName, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<locationName, String>("shortName"));
    locationType.setCellValueFactory(
        new PropertyValueFactory<locationName, String>("locationType"));
    nodeTableView.setItems(nodeList);
    locationNameTableView.setItems(locList);
  }
}
