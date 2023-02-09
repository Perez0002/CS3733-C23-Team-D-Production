package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

public class PaneFactories {

  public static MapPaneFactory getMapPaneFactory() {
    return (new MapPaneFactory())
        .size(MapDrawController.NODE_WIDTH, MapDrawController.NODE_HEIGHT)
        .style("-fx-background-color: '#013A75';");
  }
}
