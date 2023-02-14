package edu.wpi.cs3733.C23.teamD.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

import javax.annotation.processing.Generated;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

public class ProfilePageController {

    @FXML
    private MFXButton changeProfilePicture;

    @FXML
    private MFXButton viewPassword;

    @FXML
    private MFXButton changePassword;

    @FXML
    private MFXTextField email;

    @FXML
    private MFXTextField phoneNumber;

    @FXML
    private MFXTextField address;

    @FXML
    private MFXTextField birthday;

    @FXML
    private MFXTextField accountCreated;

    @FXML
    private TableView serviceRequestHistory;

    @FXML
    private TableView databaseEditHistory;

    @FXML
    private TableColumn serviceRequests;

    @FXML
    private TableColumn serviceDates;

    @FXML
    private TableColumn databaseEdits;

    @FXML
    private TableColumn databaseEditDates;


}
