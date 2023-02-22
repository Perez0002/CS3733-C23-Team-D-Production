package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ComputerServiceRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.EmployeeDropdownComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
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

  public void initialize() {
    deviceTypeBox.setItems(FXCollections.observableArrayList(deviceType));
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

  // TODO: set the rest to clear
  public void clearComputerForms() {
    deviceTypeBox.clearSelection();
    urgencyBox.clearSelection();
    locationBoxController.clearForm();
    descriptionBox.clear();
    employeeBoxController.clearForm();
  }
}
