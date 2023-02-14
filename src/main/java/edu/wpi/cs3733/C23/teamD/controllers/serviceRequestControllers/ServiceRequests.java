package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

public enum ServiceRequests {
  HUB("views/VBoxInjections/hubVBox.fxml"),
  PATIENT_TRANSPORT("views/VBoxInjections/PatientTransportVBox.fxml"),
  SANITATION_REQUEST("views/VBoxInjections/SanitationRequestForm.fxml"),
  COMPUTER_REQUEST("views/VBoxInjections/ComputerServiceRequest.fxml");

  private final String filename;

  ServiceRequests(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
