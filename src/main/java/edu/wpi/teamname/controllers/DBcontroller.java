package edu.wpi.teamname.controllers;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
public class DBcontroller extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    @FXML
    private TableColumn<?, ?> building;

    @FXML
    private TableColumn<?, ?> floor;

    @FXML
    private TableColumn<?, ?> locationType;

    @FXML
    private TableColumn<?, ?> longName;

    @FXML
    private TableColumn<?, ?> nodeID;

    @FXML
    private TableColumn<?, ?> shortName;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> xCoord;

    @FXML
    private TableColumn<?, ?> yCoord;

}

