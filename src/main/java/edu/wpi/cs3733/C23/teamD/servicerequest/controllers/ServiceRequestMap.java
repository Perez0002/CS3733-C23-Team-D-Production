package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class ServiceRequestMap {

  private static ServiceRequestMap mapSingleton;
  private static GesturePane map;

  int floor = 0;

  private ServiceRequestMap() {
    this.createMap();
    this.mapSingleton = this;
  }

  private void createMap() {

    ArrayList<Move> baseMoveList = FDdb.getInstance().getAllCurrentMoves(new Date());
    ArrayList<MapNode> nodeList = new ArrayList<>();

    HashMap<String, PathNode> pathNodes = new HashMap<>();
    for (Move move : baseMoveList) {
      pathNodes.put(move.getNodeID(), new PathNode(move.getNode(), move.getLocation()));
    }

    int sumX = 0;
    int sumY = 0;
    int totalNodes = 0;

    HashMap<String, MapNode> mapNodes = new HashMap<>();
    for (String node : pathNodes.keySet().stream().toList()) {
      MapNode tempMapNode = new MapNode(pathNodes.get(node));
      mapNodes.put(node, tempMapNode);
      nodeList.add(tempMapNode);
      if (makeFloorIntoInt(tempMapNode.getNodeFloor().getValue()) == floor) {
        sumX += tempMapNode.getNodeX().getValue();
        sumY += tempMapNode.getNodeY().getValue();
        totalNodes++;
      }
    }

    int AverageX = sumX / totalNodes;
    int AverageY = sumY / totalNodes;

    map = MapFactory.startBuild().withNodes(nodeList).build(floor);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);

    centerOnNode(AverageX, AverageY);
  }

  private int makeFloorIntoInt(String floorString) {
    switch (floorString) {
      case "L1":
        return 0;
      case "L2":
        return 1;
      case "1":
        return 2;
      case "2":
        return 3;
      case "3":
        return 4;
      case "G":
        System.out.println("You broke me. Find where I broke in Service Request Map. Despair.");
        break;
    }
    return 100; // error if this is hit
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
      new ServiceRequestMap();
    }
    return mapSingleton;
  }

  public static GesturePane getMap() {
    if (mapSingleton == null) {
      new ServiceRequestMap();
    }
    return map;
  }

  void mapCenters(LocationComboBoxController locationController) {
    if (locationController.getLocation() != null) {
      edu.wpi.cs3733.C23.teamD.database.entities.Node node =
          FDdb.getInstance().getAssociatedNode(locationController.getLocation());
      int x = node.getXcoord();
      int y = node.getYcoord();
      String floor = node.getFloor();

      switch (floor) {
        case "L1":
          setFloor(0);
          break;
        case "L2":
          setFloor(1);
          break;
        case "1":
          setFloor(2);
          break;
        case "2":
          setFloor(3);
          break;
        case "3":
          setFloor(4);
          break;
        case "G":
          System.out.println("You broke me. Find where I broke in Service Request Map. Despair.");
          break;
      }

      mapSingleton.centerOnNode(x, y);
    } else {
      System.out.println("DEV Error: The node was null in ServiceRequestMap");
    }
  }

  void centerOnNode(int x, int y) {
    Platform.runLater(
        () -> {
          map.animate(Duration.millis(50)).centreOn(new Point2D(x, y));
        });
  }
}
