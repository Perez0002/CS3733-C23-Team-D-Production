package edu.wpi.cs3733.C23.teamD.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  PATHFINDING_REQUEST_PAGE("views/PathfindingRequestForm.fxml"),
  HOME("views/Homepage.fxml"),
  PATIENT_TRANSPORT_REQUEST("views/InternalPatientTransportationRequestForm.fxml"),
  SANITATION_FORM("views/SanitationRequestForm.fxml"),
  PATHFINDING_REQUEST("views/PathfindingRequestForm.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
