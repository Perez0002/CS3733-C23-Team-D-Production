package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

public enum DatabasesFXML {
  ROOT("views/Root.fxml"),

  HUB("views/VBoxInjections/hubVBox.fxml"),
  SERVICE_REQUEST("views/DatabaseTableInjections/ServiceRequestTable.fxml");

  private final String filename;

  DatabasesFXML(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
