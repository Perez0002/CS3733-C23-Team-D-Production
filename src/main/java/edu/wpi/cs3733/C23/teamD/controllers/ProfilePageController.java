package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.CurrentUser;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUserEnum;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ProfilePageController {

  @FXML private MFXButton changeProfilePicture;

  @FXML private MFXButton viewPassword;

  @FXML private MFXButton changePassword;

  @FXML private MFXTextField email;

  @FXML private MFXTextField phoneNumber;

  @FXML private MFXTextField address;

  @FXML private DatePicker birthday;

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
    setText();
  }

  private void setText() {
    CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    if (currentUser.getAccountCreated() == null) {
      accountCreated.setText("Information Unavailable");
    } else {
      accountCreated.setText(dateFormat.format(currentUser.getAccountCreated()));
    }
    if (currentUser.getEmail() == null) {
      email.setText("Information Unavailable");
    } else {
      email.setText(currentUser.getEmail());
    }
    if (currentUser.getAddress() == null) {
      address.setText("Information Unavailable");
    } else {
      address.setText(currentUser.getAddress());
    }
    if (currentUser.getPhoneNumber() == null) {
      phoneNumber.setText("Information Unavailable");
    } else {
      phoneNumber.setText(currentUser.getPhoneNumber());
    }
  }
}
