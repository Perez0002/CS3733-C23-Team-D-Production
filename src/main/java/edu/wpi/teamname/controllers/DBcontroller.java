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
    Connection conn = Ddb.makeConnection();
    ObservableList<Node> nodeList = FXCollections.observableArrayList(Ddb.createJavaNodes(conn));
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/views/DBApp.fxml"));
    primaryStage.setTitle("NodeTable");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    // table.setItems(nodeList);
  }

  @FXML private TableColumn<Node, String> building;

  @FXML private TableColumn<Node, String> floor;

  @FXML private TableColumn<Node, String> locationType;

  @FXML private TableColumn<Node, String> longName;

  @FXML private TableColumn<Node, String> nodeID;

  @FXML private TableColumn<Node, String> shortName;

  @FXML private TableView<Node> table;

  @FXML private TableColumn<Node, Integer> xCoord;

  @FXML private TableColumn<Node, Integer> yCoord;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    yCoord.setCellValueFactory(new PropertyValueFactory<Node, Integer>("yCoord"));
    xCoord.setCellValueFactory(new PropertyValueFactory<Node, Integer>("xCoord"));
    building.setCellValueFactory(new PropertyValueFactory<Node, String>("building"));
    locationType.setCellValueFactory(new PropertyValueFactory<Node, String>("locationType"));
    shortName.setCellValueFactory(new PropertyValueFactory<Node, String>("shortName"));
    longName.setCellValueFactory(new PropertyValueFactory<Node, String>("longName"));
    nodeID.setCellValueFactory(new PropertyValueFactory<Node, String>("nodeID"));
    floor.setCellValueFactory(new PropertyValueFactory<Node, String>("floor"));
  }
}
