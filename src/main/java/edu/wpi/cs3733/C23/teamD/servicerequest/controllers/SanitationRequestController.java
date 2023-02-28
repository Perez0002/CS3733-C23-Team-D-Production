package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.SanitationRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class SanitationRequestController implements ServiceRequestVBoxController {

  @FXML private Text textHelp;
  @FXML private MFXRadioButton radioBSL1;
  @FXML private MFXRadioButton radioBSL2;
  @FXML private MFXRadioButton radioBSL3;
  @FXML private MFXRadioButton radioBSL4;
  @FXML private MFXTextField fieldReason;
  @FXML private Parent staffIDTextField;
  @FXML private EmployeeDropdownComboBoxController staffIDTextFieldController;
  @FXML private Parent fieldLocation;
  @FXML private LocationComboBoxController fieldLocationController;
  @FXML private MFXComboBox urgencyBox;
  private boolean helpDisplayed = false;

  public void initialize() {
    fieldLocationController
        .giveComboBox()
        .setOnAction(
            event ->
                ServiceRequestMapController.getMapSingleton().mapCenters(fieldLocationController));
  }

  @Override
  public void clearTransportForms() {}

  @FXML
  public boolean submit() {
    if (isFieldsSaturated()) {
      // System.out.print("Submit Success: ");
      int i = 0;
      if (radioBSL1.isSelected()) {
        i = 1;
      } else if (radioBSL2.isSelected()) {
        i = 2;
      } else if (radioBSL3.isSelected()) {
        i = 3;
      } else if (radioBSL4.isSelected()) {
        i = 4;
      }
      SanitationRequest requestData =
          new SanitationRequest(
              fieldReason.getText(),
              i,
              staffIDTextFieldController.getEmployee(),
              fieldLocationController.getLocation(),
              urgencyBox.getValue().toString());
      FDdb.getInstance().saveServiceRequest(requestData);
      return true;

    } else {
      return false;
    }
  }

  @Override
  public Node getVBox() {
    return null;
  }

  @Override
  public void setFieldsDisable(ServiceRequest serviceRequest) {
    fieldLocationController.setDisable(true);
    fieldReason.setDisable(true);
    staffIDTextFieldController.setDisable(true);
    radioBSL1.setDisable(true);
    radioBSL2.setDisable(true);
    radioBSL3.setDisable(true);
    radioBSL4.setDisable(true);
    urgencyBox.setDisable(true);

    // TODO set the radio buttions and check the employeebox
    if (serviceRequest.getClass().equals(SanitationRequest.class)) {
      fieldLocationController.setText(serviceRequest.getLocation().getLongName());
      fieldReason.setText(serviceRequest.getReason());
      staffIDTextFieldController.setText(
          serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
      SanitationRequest sRequest = (SanitationRequest) serviceRequest;
      staffIDTextFieldController.setEmployeeName(
          serviceRequest.getAssociatedStaff().getFirstName());
      radioBSL1.setSelected(sRequest.getBioLevel() == 1);
      radioBSL2.setSelected(sRequest.getBioLevel() == 2);
      radioBSL3.setSelected(sRequest.getBioLevel() == 3);
      radioBSL4.setSelected(sRequest.getBioLevel() == 4);

      urgencyBox.setText(serviceRequest.getUrgency());
    }
  }

  @FXML
  public boolean clearSanitationForms() {
    fieldLocationController.clearForm();
    fieldReason.clear();
    staffIDTextFieldController.clearForm();
    radioBSL1.setSelected(false);
    radioBSL2.setSelected(false);
    radioBSL3.setSelected(false);
    radioBSL4.setSelected(false);
    urgencyBox.clearSelection();
    return true;
    // System.out.print("Fields Cleared\n");
  }

  private boolean isFieldsSaturated() {
    // System.out.print("Submit Success2: ");
    return ((fieldLocationController.getLocation() != null)
        && (staffIDTextFieldController.getEmployeeName() != null)
        && (radioBSL1.isSelected()
            || radioBSL2.isSelected()
            || radioBSL3.isSelected()
            || radioBSL4.isSelected())
        && urgencyBox.getValue() != null);
  }

  public void fillFields(Move move) {
    radioBSL1.setSelected(true);
    fieldLocationController.setLocationName(move.getLongName());
    fieldLocationController.setText(move.getLongName());
    Employee e = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    staffIDTextFieldController.setEmployeeName(e.getFirstName() + " " + e.getLastName());
    staffIDTextFieldController.setText(e.getFirstName() + " " + e.getLastName());
    urgencyBox.setValue("Low");
    urgencyBox.setText("Low");
    LocalDate localDate =
        Instant.ofEpochMilli(move.getMoveDate().getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    ArrayList<Move> moves = FDdb.getInstance().getAllCurrentMoves(new Date());
    LocationName locationName = null;
    for (Move m : moves) {
      if (m.getNodeID().equals(move.getNodeID())) {
        locationName = move.getLocation();
      }
    }
    fieldReason.setText(
        localDate
            + ";"
            + move.getLongName()
            + ";"
            + move.getNodeID()
            + "; Please clean "
            + locationName.getLongName()
            + " in preperation for a move on the "
            + localDate
            + ".");
    fieldReason.setDisable(true);
    fieldLocationController.setDisable(true);
  }

  public void autoSubmit(Date date) {
    int i = 0;
    if (radioBSL1.isSelected()) {
      i = 1;
    } else if (radioBSL2.isSelected()) {
      i = 2;
    } else if (radioBSL3.isSelected()) {
      i = 3;
    } else if (radioBSL4.isSelected()) {
      i = 4;
    }
    SanitationRequest requestData =
        new SanitationRequest(
            fieldReason.getText(),
            i,
            staffIDTextFieldController.getEmployee(),
            fieldLocationController.getLocation(),
            urgencyBox.getValue().toString());

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
