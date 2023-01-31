package edu.wpi.cs3733.C23.teamD.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  PATHFINDING_REQUEST_PAGE("views/PathfindingRequestForm.fxml"),
  HOME("views/Homepage.fxml"),
  DATABASE_EDIT("views/DBApp.fxml"),
  PATIENT_TRANSPORT_REQUEST("views/InternalPatientTransportationRequestForm.fxml"),
  SANITATION_FORM("views/SanitationRequestForm.fxml"),
  PATHFINDING_REQUEST("views/PathfindingRequestForm.fxml"),
  PATIENT_TRANSPORT_TABLE("views/SanitationRequestTable.fxml"),
  SANITATION_TABLE("views/PatientTransportTable.fxml"),
  SERVICE_REQUEST("views/Example - ServiceRequest.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
