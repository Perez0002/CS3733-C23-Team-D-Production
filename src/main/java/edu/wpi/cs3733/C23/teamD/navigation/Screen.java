package edu.wpi.cs3733.C23.teamD.navigation;

public enum Screen {
  LOADING_PAGE("views/loadingPage.fxml"),
  ROOT("views/Root.fxml"),
  PATHFINDING_REQUEST_PAGE("views/PathfindingRequestForm.fxml"),
  HOME("views/Homepage.fxml"),
  DATABASE_EDIT("views/DBApp.fxml"),
  MAP_EDITOR("views/MapEditor.fxml"),
  PATIENT_TRANSPORT_REQUEST("views/InternalPatientTransportationRequestForm.fxml"),
  SANITATION_FORM("views/SanitationRequestVBox.fxml"),
  PATHFINDING_REQUEST("views/PathfindingRequestForm.fxml"),
  PATIENT_TRANSPORT_TABLE("views/PatientTransportTable.fxml"),
  SANITATION_TABLE("views/SanitationRequestTable.fxml"),
  LOGIN_PAGE("views/LoginPage.fxml"),
  DATABASE_EDITOR("views/DatabaseLandingPage.fxml"),

  DATABASE_HUB("views/DatabaseHub.fxml"),
  REQUEST_FORM_HUB("views/RequestFormHub.fxml"),
  SERVICE_TABLE("views/ServiceRequestTable.fxml"),
  MOVES_TABLE("views/MoveRequestTable.fxml"),
  PROFILE_PAGE("views/ProfilePage.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
