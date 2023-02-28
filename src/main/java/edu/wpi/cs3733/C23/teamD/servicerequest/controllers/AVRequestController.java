package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.AVRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;

public class AVRequestController implements ServiceRequestVBoxController {
  @FXML private Parent employeeBox;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;

  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;

  @FXML private MFXComboBox urgencyBox;

  @FXML private MFXTextField systemFailureTextField;

  @FXML private MFXDatePicker datePicker;

  public void initialize() {
    locationBoxController
        .giveComboBox()
        .setOnAction(
            event ->
                ServiceRequestMapController.getMapSingleton().mapCenters(locationBoxController));
  }

  public void clearTransportForms() {
    employeeBoxController.clearForm();
    locationBoxController.clearForm();
    urgencyBox.clearSelection();
    datePicker.clear();
    systemFailureTextField.clear();
  }

  public boolean submit() {
    if (checkFields()) {
      if (datePicker.getValue() == null) {
        AVRequest request =
            new AVRequest(
                employeeBoxController.getEmployee(),
                systemFailureTextField.getText(),
                "AVRequest",
                null,
                locationBoxController.getLocation(),
                urgencyBox.getValue().toString());
        FDdb.getInstance().saveServiceRequest(request);
      } else {
        AVRequest request =
            new AVRequest(
                employeeBoxController.getEmployee(),
                systemFailureTextField.getText(),
                "AVRequest",
                datePicker.getValue(),
                locationBoxController.getLocation(),
                urgencyBox.getValue().toString());
        FDdb.getInstance().saveServiceRequest(request);
      }
      return true;
    } else {
      return false;
    }
  }

  private boolean checkFields() {
    return !(employeeBoxController.getEmployeeName().isEmpty()
        || systemFailureTextField.getText().isEmpty()
        || urgencyBox.getText().isEmpty()
        || locationBoxController.getLocationLongName().isEmpty());
  }

  public Node getVBox() {
    return null;
  }

  @Override
  public void setFieldsDisable(ServiceRequest serviceRequest) {
    urgencyBox.setDisable(true);
    datePicker.setDisable((true));
    systemFailureTextField.setDisable(true);
    locationBoxController.setDisable(true);
    employeeBoxController.setDisable(true);
    if (serviceRequest.getClass().equals(AVRequest.class)) {
      urgencyBox.setText(serviceRequest.getUrgency());
      datePicker.setText(serviceRequest.getDateAndTime().toString());
      systemFailureTextField.setText(serviceRequest.getReason());
      locationBoxController.setText(serviceRequest.getLocation().getLongName());
      employeeBoxController.setText(
          serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
    }
  }

  public void fillFields(Move move) {
    locationBoxController.setLocationName(move.getLongName());
    locationBoxController.setText(move.getLongName());
    Employee e = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    employeeBoxController.setEmployeeName(e.getFirstName() + " " + e.getLastName());
    employeeBoxController.setText(e.getFirstName() + " " + e.getLastName());
    urgencyBox.setValue("Low");
    urgencyBox.setText("Low");
    LocalDate localDate =
        Instant.ofEpochMilli(move.getMoveDate().getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    ArrayList<Move> moves = FDdb.getInstance().getAllCurrentMoves(new Date());
    LocationName locationName = null;
    for (Move m : moves) {
      if (m.getNode() != null) {
        if (m.getNodeID().equals(move.getNodeID())) {
          System.out.println(move.getLongName());
          locationName = move.getLocation();
        }
      }
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    systemFailureTextField.setText(
        "Please check AV in  "
            + locationName.getLongName()
            + " in preperation for a move on "
            + formatter.format(localDate)
            + ".");
    Calendar calendar = Calendar.getInstance();
    datePicker.setValue(
        calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    datePicker.setDisable(true);
    systemFailureTextField.setDisable(true);
    locationBoxController.setDisable(true);
  }

  public void autoSubmit(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, -1);

    AVRequest requestData =
        new AVRequest(
            employeeBoxController.getEmployee(),
            systemFailureTextField.getText(),
            "AVRequest",
            calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            locationBoxController.getLocation(),
            urgencyBox.getValue().toString());

    System.out.println(date.toString());
    System.out.println(calendar.getTime().toString());

    FDdb.getInstance().saveServiceRequest(requestData);
    requestData.setDateAndTime(calendar.getTime());
    FDdb.getInstance().updateServiceRequest(requestData);
  }
}
