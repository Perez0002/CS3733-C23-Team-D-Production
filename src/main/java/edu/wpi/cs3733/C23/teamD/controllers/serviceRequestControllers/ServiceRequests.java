package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

public enum ServiceRequests {
  ROOT("views/Root.fxml"),

  HUB("views/VBoxInjections/hubVBox.fxml"),
  PATIENT_TRANSPORT("views/VBoxInjections/PatientTransportVBox.fxml"),
  AV_REQUEST("views/AVRequestForm.fxml");

  private final String filename;

  ServiceRequests(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
