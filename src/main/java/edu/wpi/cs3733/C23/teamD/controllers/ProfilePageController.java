package edu.wpi.cs3733.C23.teamD.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class ProfilePageController {

  @FXML private MFXButton changeProfilePicture;

  @FXML private MFXButton viewPassword;

  @FXML private MFXButton changePassword;

  @FXML private MFXTextField email;

  @FXML private MFXTextField phoneNumber;

  @FXML private MFXTextField address;

  @FXML private MFXTextField birthday;

  @FXML private MFXTextField accountCreated;

  //  @FXML private TableView serviceRequestHistory;
  //
  //  @FXML private TableView databaseEditHistory;
  //
  //  @FXML private TableColumn serviceRequests;
  //
  //  @FXML private TableColumn serviceDates;
  //
  //  @FXML private TableColumn databaseEdits;
  //
  //  @FXML private TableColumn databaseEditDates;

  public void initialize() {
    setFloatingText();
  }

  private void setFloatingText() {
    accountCreated.setFloatingText("");
    birthday.setFloatingText("");
    email.setFloatingText("");
    phoneNumber.setFloatingText("");
    address.setFloatingText("");
  }
}
