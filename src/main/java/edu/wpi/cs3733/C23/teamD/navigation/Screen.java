package edu.wpi.cs3733.C23.teamD.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  PATHFINDING_REQUEST_PAGE("views/PathfindingRequestForm.fxml"),
  HOME("views/Homepage.fxml"),
  DATABASE_EDIT("views/DBApp.fxml"),
  MAP_EDITOR("views/MapEditor.fxml"),
  PATIENT_TRANSPORT_REQUEST("views/InternalPatientTransportationRequestForm.fxml"),
  SANITATION_FORM("views/SanitationRequestForm.fxml"),
  PATHFINDING_REQUEST("views/PathfindingRequestForm.fxml"),
  PATIENT_TRANSPORT_TABLE("views/PatientTransportTable.fxml"),
  SANITATION_TABLE("views/SanitationRequestTable.fxml"),
  SERVICE_REQUEST("views/LandingPage.fxml"),
  DATABASE_EDITOR("views/DatabaseLandingPage.fxml"),
  HELP_PAGE("views/ApplicationHelpPage.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
