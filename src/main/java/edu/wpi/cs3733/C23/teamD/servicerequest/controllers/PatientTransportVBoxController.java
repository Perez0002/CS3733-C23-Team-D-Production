package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.PatientTransportRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
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
import javafx.scene.layout.VBox;

public class PatientTransportVBoxController implements ServiceRequestVBoxController {
  @FXML private VBox patientTransportRequestVBox;
  @FXML private Parent employeeComboBox;
  @FXML private EmployeeDropdownComboBoxController employeeComboBoxController;
  @FXML private Parent startingLocation;
  @FXML private LocationComboBoxController startingLocationController;
  @FXML private Parent endLocationComboBox;
  @FXML private LocationComboBoxController endLocationComboBoxController;
  @FXML private MFXComboBox urgencyBox;
  @FXML private MFXTextField descriptionBox;

  public PatientTransportVBoxController() {}

  public void initialize() {
    startingLocationController
        .giveComboBox()
        .setOnAction(
            event ->
                ServiceRequestMapController.getMapSingleton()
                    .mapCenters(startingLocationController));
    endLocationComboBoxController
        .giveComboBox()
        .setOnAction(
            event ->
                ServiceRequestMapController.getMapSingleton()
                    .mapCenters(endLocationComboBoxController));
  };

  public Node getVBox() {
    return patientTransportRequestVBox;
  }

  @Override
  public void setFieldsDisable(ServiceRequest serviceRequest) {
    endLocationComboBoxController.setDisable(true);
    descriptionBox.setDisable(true);
    employeeComboBoxController.setDisable(true);
    urgencyBox.setDisable(true);
    startingLocationController.setDisable(true);

    // TODO fix/check employeebox and also test the start and end locations are correct
    if (serviceRequest.getClass().equals(PatientTransportRequest.class)) {
      endLocationComboBoxController.setText(
          ((PatientTransportRequest) serviceRequest).getEndRoom());
      descriptionBox.setText(serviceRequest.getReason());
      employeeComboBoxController.setText(
          serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
      urgencyBox.setText(serviceRequest.getUrgency());
      startingLocationController.setText(serviceRequest.getLocation().getLongName());
    }
  }

  public void clearTransportForms() {
    employeeComboBoxController.clearForm();
    startingLocationController.clearForm();
    endLocationComboBoxController.clearForm();
    urgencyBox.clearSelection();
    descriptionBox.clear();
  }

  boolean checkFieldsFull() {
    if (employeeComboBoxController.getEmployeeName() != null
        && startingLocationController.getLocationLongName() != null
        && endLocationComboBoxController.getLocationLongName() != null
        && urgencyBox.getValue() != null
        && descriptionBox.getText() != null) {
      return true;
    }
    return false;
  }

  public boolean submit() {
    // if the fields are full, go to submit item
    if (checkFieldsFull()) {
      PatientTransportRequest newForm =
          new PatientTransportRequest(
              endLocationComboBoxController.getLocationLongName(),
              descriptionBox.getText(),
              employeeComboBoxController.getEmployee(),
              urgencyBox.getValue().toString(),
              startingLocationController.getLocation());
      FDdb.getInstance().saveServiceRequest(newForm);
      return true;
    }
    // else, display text that says you need to fill fields
    else {
      // TODO: write text that says fields must be full
      return false;
    }
  }

  public void fillFields(Move move) {
    startingLocationController.setLocationName(move.getLongName());
    startingLocationController.setText(move.getLongName());
    endLocationComboBoxController.setLocationName(move.getLongName());
    endLocationComboBoxController.setText(move.getLongName());
    Employee e = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    employeeComboBoxController.setEmployeeName(e.getFirstName() + " " + e.getLastName());
    employeeComboBoxController.setText(e.getFirstName() + " " + e.getLastName());
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
    descriptionBox.setText(
        localDate
            + ";"
            + move.getLongName()
            + ";"
            + move.getNodeID()
            + ";"
            + "Please move patient in  "
            + locationName.getLongName()
            + " in preperation for a move on "
            + formatter.format(localDate)
            + ".");
    descriptionBox.setDisable(true);
    startingLocationController.setDisable(true);
    endLocationComboBoxController.setDisable(true);
  }

  public void autoSubmit(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, -1);

    PatientTransportRequest requestData =
        new PatientTransportRequest(
            endLocationComboBoxController.getLocationLongName(),
            descriptionBox.getText(),
            employeeComboBoxController.getEmployee(),
            urgencyBox.getValue().toString(),
            startingLocationController.getLocation());

    System.out.println(date.toString());
    System.out.println(calendar.getTime().toString());

    FDdb.getInstance().saveServiceRequest(requestData);
    requestData.setDateAndTime(calendar.getTime());
    FDdb.getInstance().updateServiceRequest(requestData);
  }
}
