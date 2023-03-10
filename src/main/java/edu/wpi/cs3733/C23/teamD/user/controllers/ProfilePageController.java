package edu.wpi.cs3733.C23.teamD.user.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.user.entities.Setting;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lombok.Getter;
import org.controlsfx.control.PopOver;

public class ProfilePageController {

  @FXML private Label helloBox;

  @FXML private MFXButton changeProfilePicture;

  @FXML private MFXButton viewPassword;

  @FXML private MFXButton changePassword;

  @FXML private MFXTextField firstNameBox;
  @FXML private MFXTextField lastNameBox;
  @FXML private MFXTextField email;
  @FXML private MFXTextField phoneNumber;
  @FXML private MFXTextField address;
  @Getter @FXML private MFXToggleButton confettiButton;
  @FXML private MFXButton helpButton;
  @FXML private TableView<ServiceRequest> serviceRequestHistory;
  @FXML private TableColumn<ServiceRequest, String> serviceRequests;
  @FXML private TableColumn<ServiceRequest, Date> serviceDates;
  @FXML private TableColumn<ServiceRequest, Integer> requestID;

  public ProfilePageController() {}

  public void initialize() {
    serviceRequestTable();
    setConfettiToggle();
    setDarkModeToggle();
    setText();
    changePassword.setOnMouseClicked(
        event -> {
          try {
            changePasswordPopup();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    helpButton.setOnMouseClicked(
        event -> {
          try {
            help();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void changePasswordPopup() throws IOException {
    final var resource =
        App.class.getResource("views/VBoxInjections/ProfilePageChangePassword.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    PopOver popover = new PopOver(loader.load());
    popover.setArrowSize(0);
    popover.setCornerRadius(32);
    popover.setTitle("Change Password");
    popover.show(App.getPrimaryStage());
  }

  private void setText() {
    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    helloBox.setText(
        "Hello, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    if (currentUser.getFirstName() == null) {
      firstNameBox.setText("Information Unavailable");
    } else {
      firstNameBox.setText(currentUser.getFirstName());
    }
    if (currentUser.getLastName() == null) {
      lastNameBox.setText("Information Unavailable");
    } else {
      lastNameBox.setText(currentUser.getLastName());
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

  private void setConfettiToggle() {
    Setting currentSetting = CurrentUserEnum._CURRENTUSER.getSetting();
    if (currentSetting.getConfetti() == 1) {
      confettiButton.setSelected(true);
    } else {
      confettiButton.setSelected(false);
    }
  }

  private void setDarkModeToggle() {
    Setting currentSetting = CurrentUserEnum._CURRENTUSER.getSetting();
    if (currentSetting.getConfetti() == 1) {
      confettiButton.setSelected(true);
    } else {
      confettiButton.setSelected(false);
    }
  }

  @FXML
  private void resetChanges() {
    setText();
  }

  @FXML
  private void setConfettiDatabase() {
    Setting currentSetting = CurrentUserEnum._CURRENTUSER.getSetting();

    if (confettiButton.isSelected()) {
      currentSetting.setConfetti(1);
      FDdb.getInstance().updateSetting(currentSetting);
    } else {
      currentSetting.setConfetti(0);
      FDdb.getInstance().updateSetting(currentSetting);
    }
  }

  //  @FXML
  //  private void setNightModeDatabase() {
  //    Setting currentSetting = CurrentUserEnum._CURRENTUSER.getSetting();
  //
  //    if (darkModeButton.isSelected()) {
  //      currentSetting.setConfetti(1);
  //      FDdb.getInstance().updateSetting(currentSetting);
  //    } else {
  //      currentSetting.setConfetti(0);
  //      FDdb.getInstance().updateSetting(currentSetting);
  //    }
  //  }

  @FXML
  private void saveChanges() {
    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    currentUser.setFirstName(firstNameBox.getText());
    currentUser.setLastName(lastNameBox.getText());
    currentUser.setEmail(email.getText());
    currentUser.setAddress(address.getText());
    currentUser.setPhoneNumber(phoneNumber.getText());
    FDdb.getInstance().updateEmployee(currentUser);
    ToastController.makeText("Changes Saved.", 1500, 50, 50);
    resetChanges();
  }

  void help() throws IOException {
    final var resource = App.class.getResource("views/VBoxInjections/ProfilePageHelp.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    PopOver popover = new PopOver(loader.load());
    popover.setArrowSize(0);
    popover.setTitle("Help");
    popover.show(App.getPrimaryStage());
  }

  public void serviceRequestTable() {
    Employee currentuser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    ArrayList<ServiceRequest> genericServiceList =
        FDdb.getInstance().getAllGenericServiceRequests();
    ArrayList<ServiceRequest> employeeServiceRequests = new ArrayList<>();

    for (ServiceRequest s : genericServiceList) {
      if (s.getAssociatedStaff() == null) {
        continue;
      } else if (s.getAssociatedStaff().getEmployeeID() == currentuser.getEmployeeID()
          && s.getStat().equals(ServiceRequest.Status.DONE)
          && !employeeServiceRequests.contains(s)) {
        employeeServiceRequests.add(s);
      }
    }

    ObservableList<ServiceRequest> listserviceRequests =
        FXCollections.observableArrayList(employeeServiceRequests);

    serviceRequests.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, String>("serviceRequestType"));
    serviceDates.setCellValueFactory(new PropertyValueFactory<ServiceRequest, Date>("dateAndTime"));
    requestID.setCellValueFactory(
        new PropertyValueFactory<ServiceRequest, Integer>("serviceRequestId"));

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
