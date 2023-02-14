package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

public enum ServiceRequests {

  HUB("views/VBoxInjections/hubVBox.fxml"),
  PATIENT_TRANSPORT("views/VBoxInjections/PatientTransportVBox.fxml"),
  SANITATION("views/VBoxInjections/SanitationRequestForm.fxml");

  private final String filename;

  ServiceRequests(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
