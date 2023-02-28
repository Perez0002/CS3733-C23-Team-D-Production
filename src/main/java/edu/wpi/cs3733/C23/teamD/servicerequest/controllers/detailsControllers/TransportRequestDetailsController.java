package edu.wpi.cs3733.C23.teamD.servicerequest.controllers.detailsControllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.entities.PatientTransportRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class TransportRequestDetailsController implements RequestDetailsController {

  // replace additional field
  @FXML private Label additionalField;
  @FXML private Label urgencyBox;
  @FXML private Label employeeBox;
  @FXML private Label descriptionBox;
  @FXML private Label location1;
  @FXML private Label assignedBy;
  @FXML private Label date;
  @FXML private Label requestID;

  public TransportRequestDetailsController() {}

  @Override
  public void setFields(ServiceRequest serviceRequest) {

    // replace type of request checker
    if (serviceRequest.getClass().equals(PatientTransportRequest.class)) {
      descriptionBox.setText("Additional Details: \n" + serviceRequest.getReason());
      employeeBox.setText(
          "Assigned Staff: "
              + serviceRequest.getAssociatedStaff().getFirstName()
              + " "
              + serviceRequest.getAssociatedStaff().getLastName());
      urgencyBox.setText("Urgency: " + serviceRequest.getUrgency());
      // set additional field text
      additionalField.setText(
          "End Location: " + ((PatientTransportRequest) serviceRequest).getEndRoom());
      location1.setText("Start Location: " + serviceRequest.getLocation().getShortName());
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
