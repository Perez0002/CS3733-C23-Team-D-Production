package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

public enum DatabasesFXML {
  ROOT("views/Root.fxml"),

  HUB("views/VBoxInjections/hubVBox.fxml"),

  NODE_TABLE("views/DatabaseTableInjections/NodeTable.fxml"),

  MOVE_TABLE("views/DatabaseTableInjections/MoveTable.fxml"),

  EDGES_TABLE("views/DatabaseTableInjections/EdgesTable.fxml"),

  SERVICE_REQUEST("views/DatabaseTableInjections/ServiceRequestTable.fxml");

  private final String filename;

  DatabasesFXML(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
