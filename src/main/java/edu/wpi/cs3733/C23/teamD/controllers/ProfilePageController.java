package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.entities.Employee;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ProfilePageController {

  @FXML private MFXButton changeProfilePicture;

  @FXML private MFXButton viewPassword;

  @FXML private MFXButton changePassword;

  @FXML private MFXTextField email;

  @FXML private MFXTextField phoneNumber;

  @FXML private MFXTextField address;

  @FXML private MFXDatePicker birthday;

  @FXML private MFXTextField accountCreated;

  @FXML private TableView<ServiceRequest> serviceRequestHistory;
  @FXML private TableColumn<ServiceRequest, String> serviceRequests;
  @FXML private TableColumn<ServiceRequest, Date> serviceDates;

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
    serviceRequestTable();
    accountCreated.setDisable(true);
    birthday.setDisable(true);
  }

  private void setText() {
    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
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
    if (currentUser.getBirthday() == null) {
    } else {
      birthday.setValue(
          currentUser.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
  }

  @FXML
  private void resetChanges() {
    setText();
  }

  @FXML
  private void saveChanges() {
    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    currentUser.setEmail(email.getText());
    currentUser.setAddress(address.getText());
    currentUser.setPhoneNumber(phoneNumber.getText());
    FDdb.getInstance().updateEmployee(currentUser);
    ToastController.makeText("Changes Saved.", 1500, 50, 50, 675, 750);
  }

  public void serviceRequestTable() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();
    ArrayList<ServiceRequest> employeeServiceList = new ArrayList<>();

    for (ServiceRequest s : genericServiceList) {
      if (s.getStaffAssigned() == null) {
        continue;
      } else if (s.getAssociatedStaff().equals(currentuser)) {
        employeeServiceList.add(s);
      }
    }

    ObservableList<ServiceRequest> listserviceRequests =
        FXCollections.observableArrayList(employeeServiceList);

    serviceDates.setCellValueFactory(new PropertyValueFactory<ServiceRequest, Date>("dateAndTime"));
    serviceRequests.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("serviceRequestType"));
    serviceRequestHistory.setItems(listserviceRequests);
    serviceRequestHistory.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    serviceRequestHistory.getColumns().stream()
        .forEach(
            (column) -> {
              double size = serviceRequestHistory.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = serviceRequestHistory.getLayoutBounds().getWidth();
              for (int i = 0; i < serviceRequestHistory.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (serviceRequestHistory.getMaxWidth() / size > currentMax)
                column.setMinWidth(serviceRequestHistory.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
