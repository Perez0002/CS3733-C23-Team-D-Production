package edu.wpi.cs3733.C23.teamD.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  PATIENT_TRANSPORT_REQUEST("views/InternalPatientTransportationRequestForm.fxml"),
  SANITATION_FORM("views/SanitationRequestForm.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
