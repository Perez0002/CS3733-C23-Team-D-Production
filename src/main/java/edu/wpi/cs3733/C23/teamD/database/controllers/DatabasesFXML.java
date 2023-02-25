package edu.wpi.cs3733.C23.teamD.database.controllers;

public enum DatabasesFXML {
  ROOT("views/Root.fxml"),

  HUB("views/VBoxInjections/hubVBox.fxml"),

  NODE_TABLE("views/DatabaseTableInjections/NodeTable.fxml"),

  MOVE_TABLE("views/DatabaseTableInjections/MoveTable.fxml"),

  EDGES_TABLE("views/DatabaseTableInjections/EdgesTable.fxml"),

  LOCATION_TABLE("views/DatabaseTableInjections/LocationTable.fxml"),

  ANALYTICS_PAGE("views/DatabaseTableInjections/AnalyticsPage.fxml"),

  SERVICE_REQUEST("views/DatabaseTableInjections/ServiceRequestTable.fxml"),

  MOVE_REQUEST("views/DatabaseTableInjections/AddMoveVBox.fxml"),
  ADD_NODE("views/DatabaseTableInjections/AddNodeVBox.fxml"),
  ADD_LOCATION("views/DatabaseTableInjections/AddLocationVBox.fxml"),
  CHANGE_SERVICE_REQUEST("views/DatabaseTableInjections/AddServiceRequestVBox.fxml"),
  ADD_EDGE("views/DatabaseTableInjections/AddEdgeVBox.fxml");

  private final String filename;

  DatabasesFXML(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
