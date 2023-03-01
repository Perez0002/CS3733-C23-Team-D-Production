package edu.wpi.cs3733.C23.teamD.servicerequest.controllers.detailsControllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ComputerServiceRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class ComputerRequestDetailsController implements RequestDetailsController {

  // replace additional field
  @FXML private Label additionalField;
  @FXML private Label urgencyBox;
  @FXML private Label employeeBox;
  @FXML private Label descriptionBox;
  @FXML private Label location1;
  @FXML private Label assignedBy;
  @FXML private Label date;
  @FXML private Label requestID;

  public ComputerRequestDetailsController() {}

  @Override
  public void setFields(ServiceRequest serviceRequest) {

    // replace type of request checker
    if (serviceRequest.getClass().equals(ComputerServiceRequest.class)) {
      String[] reason = serviceRequest.getReason().split(";");
      if (reason.length == 4) {
        descriptionBox.setText("Additional Details: \n" + reason[3]);
      } else {
        descriptionBox.setText("Additional Details: \n" + serviceRequest.getReason());
      }
      employeeBox.setText(
          "Assigned Staff: "
              + serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
      urgencyBox.setText("Urgency: " + serviceRequest.getUrgency());
      // set additional field text
      additionalField.setText(
          "Device Type: " + ((ComputerServiceRequest) serviceRequest).getDeviceType());
      location1.setText("Location: " + serviceRequest.getLocation().getShortName());
      date.setText("Date: " + serviceRequest.getDateAndTime().toString());
      requestID.setText(
          "Request ID: " + ((String) Integer.toString(serviceRequest.getServiceRequestId())));
      assignedBy.setText(
          "Assigned By: "
              + serviceRequest.getStaffAssigning().getFirstName()
              + " "
              + serviceRequest.getStaffAssigning().getLastName());
    }
  }

  public void initialize() {}

  @Override
  public Node getVBox() {
    return null;
  }
}
