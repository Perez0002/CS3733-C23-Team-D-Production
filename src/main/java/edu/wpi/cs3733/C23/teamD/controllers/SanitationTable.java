package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.locationName;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
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

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class SanitationTable extends Application implements Initializable {

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

    @FXML
    private TableColumn<Node, String> building;

    @FXML private TableColumn<Node, String> floor;

    @FXML private TableColumn<Node, String> nodeID;

    @FXML private TableView<Node> nodeTableView;

    @FXML private TableColumn<Node, Integer> xCoord;

    @FXML private TableColumn<Node, Integer> yCoord;

    @FXML private TableColumn<locationName, String> shortName;

    @FXML private TableColumn<locationName, String> longName;

    @FXML private TableColumn<locationName, String> locationType;

    @FXML private TableView<locationName> locationNameTableView;

    @FXML private MFXButton cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tablehandling();
        cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    }

    public void tablehandling() {
        nodeTableView.setEditable(true);
        locationNameTableView.setEditable(true);
        Connection conn = Ddb.makeConnection();
        ObservableList<Node> nodeList = FXCollections.observableArrayList(Ddb.createJavaNodes(conn));
        ObservableList<locationName> locList =
                FXCollections.observableArrayList(Ddb.createJavaLocat(conn));
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
                        String stmnt = "UPDATE Node SET xCoord = ? WHERE nodeID = ?";
                        Ddb.updateObjInt(conn, stmnt, node.getNodeID(), newCoord);
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
                        Ddb.updateObjInt(conn, stmnt, node.getNodeID(), newCoord);
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
                        Ddb.updateObjString(conn, stmnt, node.getNodeID(), newFloor);
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
                        Ddb.updateObjString(conn, stmnt, node.getNodeID(), newBuild);
                    }
                });

        longName.setCellValueFactory(new PropertyValueFactory<locationName, String>("longName"));
        shortName.setCellValueFactory(new PropertyValueFactory<locationName, String>("shortName"));
        shortName.setCellFactory(TextFieldTableCell.forTableColumn());
        shortName.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<locationName, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<locationName, String> event) {
                        locationName name = event.getRowValue();
                        String newShort = event.getNewValue();
                        name.setShortName(newShort);
                        String stmnt = "UPDATE locationName SET shortName = ? WHERE longName = ?";
                        Ddb.updateObjString(conn, stmnt, name.getLongName(), newShort);
                    }
                });

        locationType.setCellValueFactory(
                new PropertyValueFactory<locationName, String>("locationType"));
        locationType.setCellFactory(TextFieldTableCell.forTableColumn());
        locationType.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<locationName, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<locationName, String> event) {
                        locationName name = event.getRowValue();
                        String newType = event.getNewValue();
                        name.setLocationType(newType);
                        String stmnt = "UPDATE locationName SET locationType = ? WHERE longName = ?";
                        Ddb.updateObjString(conn, stmnt, name.getLongName(), newType);
                    }
                });

        nodeTableView.setItems(nodeList);
        locationNameTableView.setItems(locList);
    }
}
