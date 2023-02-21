package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class ServiceRequestMap {

  private static ServiceRequestMap mapSingleton;
  private static GesturePane map;

  int floor = 0;

  private ServiceRequestMap() {
    if (mapSingleton == null) {
      createMap();
      this.mapSingleton = this;
    } else {
      System.out.println("DEV ERROR: ServiceRequestMap already created. Can not create another.");
    }
  }

  private void createMap() {
    map = MapFactory.startBuild().build(floor);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);
    centerOnNode(1400, 800);
  }

  public void setFloor(int f) {
    floor = f;
    createMap();
  }

  int getFloor() {
    return floor;
  }

  public static ServiceRequestMap getMapSingleton() {
    if (mapSingleton == null) {
      ServiceRequestMap mapSingleton = new ServiceRequestMap();
    }
    return mapSingleton;
  }

  public static GesturePane getMap() {
    if (mapSingleton == null) {
      ServiceRequestMap mapSingleton = new ServiceRequestMap();
    }
    return map;
  }

  void mapCenters(LocationComboBoxController locationController) {
    if (locationController.getLocation() != null) {
      edu.wpi.cs3733.C23.teamD.database.entities.Node node =
          FDdb.getInstance().getAssociatedNode(locationController.getLocation());
      int x = node.getXcoord();
      int y = node.getYcoord();
      mapSingleton.centerOnNode(x, y);
    } else {
      System.out.println("DEV Error: The node was null in ServiceRequestMap");
    }
  }

  void centerOnNode(int x, int y) {
    map.animate(Duration.millis(50)).centreOn(new Point2D(x, y));
  }
}
