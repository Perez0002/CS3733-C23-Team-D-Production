package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.SecurityServiceRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.UrgencySelectorBoxController;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
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

public class SecurityServiceRequestController extends ServiceRequestController
    implements ServiceRequestVBoxController {
  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;
  @FXML private Parent urgencyBox;
  @FXML private UrgencySelectorBoxController urgencyBoxController;
  @FXML private MFXCheckbox addSecurityNode;
  @FXML private MFXCheckbox addRequestSecurityNode;
  @FXML private MFXTextField problemTextField;
  @FXML private Parent employeeBox;
  @FXML private EmployeeDropdownComboBoxController employeeBoxController;

  public SecurityServiceRequestController() {}

  public void initialize() {
    locationBoxController
        .giveComboBox()
        .setOnAction(
            event ->
                ServiceRequestMapController.getMapSingleton().mapCenters(locationBoxController));
  }

  public void clearFields() {
    employeeBoxController.clearForm();
    locationBoxController.clearForm();
    urgencyBoxController.clearForm();
    this.problemTextField.clear();
    this.addSecurityNode.setSelected(false);
    this.addRequestSecurityNode.setSelected(false);
  }

  @Override
  public void clearTransportForms() {}

  public boolean submit() {
    if (checkFields()) {
      String typeOfRequest = "";
      if (addRequestSecurityNode.isSelected() && addSecurityNode.isSelected()) {
        typeOfRequest = "Add Security,Request Security";
      } else if (addSecurityNode.isSelected()) {
        typeOfRequest = "Add Security";
      } else if (addRequestSecurityNode.isSelected()) {
        typeOfRequest = "Request Security";
      }

      SecurityServiceRequest securityServiceRequest =
          new SecurityServiceRequest(
              typeOfRequest,
              employeeBoxController.getEmployee(),
              problemTextField.getText(),
              locationBoxController.getLocation(),
              urgencyBoxController.getUrgency());
      try {
        FDdb.getInstance().saveServiceRequest(securityServiceRequest);
        return true;
      } catch (Exception ex) {
        ex.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
  }

  private boolean checkFields() {
    if (employeeBoxController.getEmployeeName() != null
        && locationBoxController.getLocationLongName() != null
        && problemTextField.getText() != null
        && urgencyBoxController.getUrgency() != null
        && (addSecurityNode.isSelected() || addRequestSecurityNode.isSelected())) {
      return true;
    } else return false;
  }

  @Override
  public Node getVBox() {
    return null;
  }

  @Override
  public void setFieldsDisable(ServiceRequest serviceRequest) {

    employeeBoxController.setDisable(true);
    problemTextField.setDisable(true);
    locationBoxController.setDisable(true);
    urgencyBoxController.setDisable(true);
    addSecurityNode.setDisable(true);
    addRequestSecurityNode.setDisable(true);
    if (serviceRequest.getClass().equals(SecurityServiceRequest.class)) {
      problemTextField.setText(serviceRequest.getReason().toString());
      urgencyBoxController.setText(serviceRequest.getUrgency());
      locationBoxController.setText(serviceRequest.getLocation().getLongName());
      if (((SecurityServiceRequest) serviceRequest)
          .getTypeOfSecurityRequest()
          .equals("Add Security")) {
        addSecurityNode.setSelected(true);
        addRequestSecurityNode.setSelected(false);
      } else if (((SecurityServiceRequest) serviceRequest)
          .getTypeOfSecurityRequest()
          .equals("Request Security")) {
        addRequestSecurityNode.setSelected(true);
        addSecurityNode.setSelected(false);
      } else if (((SecurityServiceRequest) serviceRequest)
          .getTypeOfSecurityRequest()
          .equals("Add Security,Request Security")) {
        addRequestSecurityNode.setSelected(true);
        addSecurityNode.setSelected(true);
      } else {
        addRequestSecurityNode.setSelected(false);
        addSecurityNode.setSelected(false);
      }
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
    urgencyBoxController.setValue("Low");
    urgencyBoxController.setText("Low");
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
    problemTextField.setText(
        localDate
            + ";"
            + move.getLongName()
            + ";"
            + move.getNodeID()
            + ";"
            + "Please check security in  "
            + locationName.getLongName()
            + " in preperation for a move on "
            + formatter.format(localDate)
            + ".");
    addRequestSecurityNode.setSelected(true);
    problemTextField.setDisable(true);
    locationBoxController.setDisable(true);
  }

  public void autoSubmit(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, -1);

    String typeOfRequest = "";
    if (addRequestSecurityNode.isSelected() && addSecurityNode.isSelected()) {
      typeOfRequest = "Add Security,Request Security";
    } else if (addSecurityNode.isSelected()) {
      typeOfRequest = "Add Security";
    } else if (addRequestSecurityNode.isSelected()) {
      typeOfRequest = "Request Security";
    }

    SecurityServiceRequest requestData =
        new SecurityServiceRequest(
            typeOfRequest,
            employeeBoxController.getEmployee(),
            problemTextField.getText(),
            locationBoxController.getLocation(),
            urgencyBoxController.getUrgency());

    System.out.println(date.toString());
    System.out.println(calendar.getTime().toString());

    FDdb.getInstance().saveServiceRequest(requestData);
    requestData.setDateAndTime(calendar.getTime());
    FDdb.getInstance().updateServiceRequest(requestData);
  }
}
