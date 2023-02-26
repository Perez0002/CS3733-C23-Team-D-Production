package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

public enum ServiceRequests {
  HUB("views/VBoxInjections/hubVBox.fxml"),
  PATIENT_TRANSPORT("views/VBoxInjections/PatientTransportVBox.fxml"),
  SANITATION_REQUEST("views/VBoxInjections/SanitationRequestForm.fxml"),
  COMPUTER_REQUEST("views/VBoxInjections/ComputerServiceRequest.fxml"),
  SECURITY_REQUEST("views/VBoxInjections/SecurityServiceRequest.fxml"),
  AV_REQUEST("views/VBoxInjections/AVRequestForm.fxml"),

  PATIENT_TRANSPORT_DETAILS("views/VBoxInjections/TransportRequestDetails.fxml"),
  SANITATION_REQUEST_DETAILS("views/VBoxInjections/SanitationRequestDetails.fxml"),
  SECURITY_REQUEST_DETAILS("views/VBoxInjections/SecurityRequestDetails.fxml"),
  AV_REQUEST_DETAILS("views/VBoxInjections/AVRequestDetails.fxml"),
  COMPUTER_REQUEST_DETAILS("views/VBoxInjections/ComputerRequestDetails.fxml"),
  TEMPLATE_REQUEST_DETAILS("views/VBoxInjections/TemplateRequestDetails.fxml");

  private final String filename;

  ServiceRequests(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
