package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ComputerServiceRequest;
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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;

public class ComputerServiceRequestController extends ServiceRequestController
    implements ServiceRequestVBoxController {
  @FXML private ArrayList<String> deviceType;
  @FXML private MFXComboBox deviceTypeBox;
  @FXML private MFXComboBox urgencyBox;
  @FXML private Parent employeeBox;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;

  @FXML private MFXTextField descriptionBox;
  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;

  public ComputerServiceRequestController() {
    deviceType = new ArrayList<String>();
    deviceType.add("Laptop Computer");
    deviceType.add("Desktop Computer");
    deviceType.add("Tablet");
    deviceType.add("Kiosk");
    deviceType.add("Other");
  }

  public void initialize() {
    deviceTypeBox.setItems(FXCollections.observableArrayList(deviceType));
    locationBoxController
        .giveComboBox()
        .setOnAction(
            event ->
                ServiceRequestMapController.getMapSingleton().mapCenters(locationBoxController));
  }

  @Override
  public void clearTransportForms() {}

  public boolean submit() {

    if (descriptionBox.getText() != null
        && urgencyBox.getText() != null
        && deviceTypeBox.getText() != null
        && employeeBoxController.getEmployeeName() != null
        && locationBoxController.getLocation() != null) {
      System.out.println("Submit computer request");

      ComputerServiceRequest computerServiceRequest =
          new ComputerServiceRequest(
              descriptionBox.getText(),
              employeeBoxController.getEmployee(),
              urgencyBox.getText(),
              deviceTypeBox.getText(),
              locationBoxController.getLocation());
      FDdb.getInstance().saveServiceRequest(computerServiceRequest);

      return true;
    }
    return false;
  }

  @Override
  public Node getVBox() {
    return null;
  }

  @Override
  public void setFieldsDisable(ServiceRequest serviceRequest) {
    descriptionBox.setDisable(true);
    employeeBoxController.setDisable(true);
    urgencyBox.setDisable(true);
    deviceTypeBox.setDisable(true);
    locationBoxController.setDisable(true);

    if (serviceRequest.getClass().equals(ComputerServiceRequest.class)) {
      // TODO fix/check the employee box
      descriptionBox.setText(serviceRequest.getReason());
      employeeBoxController.setText(
          serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
      urgencyBox.setText(serviceRequest.getUrgency());
      deviceTypeBox.setText(((ComputerServiceRequest) serviceRequest).getDeviceType());
      locationBoxController.setText(serviceRequest.getLocation().getLongName());
    }
  }

  // TODO: set the rest to clear
  public void clearComputerForms() {
    deviceTypeBox.clearSelection();
    urgencyBox.clearSelection();
    locationBoxController.clearForm();
    descriptionBox.clear();
    employeeBoxController.clearForm();
  }

  public void fillFields(Move move) {
    locationBoxController.setLocationName(move.getLongName());
    locationBoxController.setText(move.getLongName());
    Employee e = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    employeeBoxController.setEmployeeName(e.getFirstName() + " " + e.getLastName());
    employeeBoxController.setText(e.getFirstName() + " " + e.getLastName());
    urgencyBox.setValue("Low");
    urgencyBox.setText("Low");
    deviceTypeBox.setValue("Desktop Computer");
    deviceTypeBox.setText("Desktop Computer");
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
        "Please check computer in  "
            + locationName.getLongName()
            + " in preperation for a move on "
            + formatter.format(localDate)
            + ".");
    descriptionBox.setDisable(true);
    locationBoxController.setDisable(true);
  }

  public void autoSubmit(Date date) {
    ComputerServiceRequest requestData =
        new ComputerServiceRequest(
            descriptionBox.getText(),
            employeeBoxController.getEmployee(),
            urgencyBox.getValue().toString(),
            deviceTypeBox.getText(),
            locationBoxController.getLocation());

    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);
    calendar.add(Calendar.DATE, -1);
    System.out.println(date.toString());
    System.out.println(calendar.getTime().toString());

    FDdb.getInstance().saveServiceRequest(requestData);
    requestData.setDateAndTime(calendar.getTime());
    FDdb.getInstance().updateServiceRequest(requestData);
  }
}
