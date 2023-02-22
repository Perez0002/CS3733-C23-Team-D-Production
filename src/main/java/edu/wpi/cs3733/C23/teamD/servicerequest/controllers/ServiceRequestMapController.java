package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class ServiceRequestMapController {

  @FXML private BorderPane mapPaneContainer;
  @FXML private MFXButton floor1Button;
  @FXML private MFXButton floor2Button;
  @FXML private MFXButton floor3Button;
  @FXML private MFXButton floor4Button;
  @FXML private MFXButton floor5Button;

  static ServiceRequestMapController mapSingleton;
  GesturePane map;

  int currentFloor = 0;

  public void initialize() {
    changeFloor(1);
    floor1Button.setOnMouseClicked(event -> changeFloor(0));
    floor2Button.setOnMouseClicked(event -> changeFloor(1));
    floor3Button.setOnMouseClicked(event -> changeFloor(2));
    floor4Button.setOnMouseClicked(event -> changeFloor(3));
    floor5Button.setOnMouseClicked(event -> changeFloor(4));
  }

  public ServiceRequestMapController() {
    this.createMap();
    mapSingleton = this;
  }

  public void createMap() {

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
      if (makeFloorIntoInt(tempMapNode.getNodeFloor().getValue()) == currentFloor) {
        sumX += tempMapNode.getNodeX().getValue();
        sumY += tempMapNode.getNodeY().getValue();
        totalNodes++;
      }
    }

    int AverageX = sumX / totalNodes;
    int AverageY = sumY / totalNodes;

    map = MapFactory.startBuild().withNodes(nodeList).build(currentFloor);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);
    mapPaneContainer.setCenter(map);

    centerOnNode(AverageX, AverageY);
  }

  public int makeFloorIntoInt(String floorString) {
    switch (floorString) {
      case "L1":
        {
          return 0;
        }
      case "L2":
        {
          return 1;
        }
      case "1":
        {
          return 2;
        }
      case "2":
        {
          return 3;
        }
      case "3":
        {
          return 4;
        }
      case "G":
        System.out.println("You broke me. Find where I broke in Service Request Map. Despair.");
    }
    return 100; // error if this is hit
  }

  public void setFloor(int f) {
    currentFloor = f;
    createMap();
  }

  public static ServiceRequestMapController getMapSingleton() {
    if (mapSingleton == null) {
      new ServiceRequestMapController();
    }
    return mapSingleton;
  }

  public void mapCenters(LocationComboBoxController locationController) {
    if (locationController.getLocation() != null) {
      edu.wpi.cs3733.C23.teamD.database.entities.Node node =
          FDdb.getInstance().getAssociatedNode(locationController.getLocation());
      int x = node.getXcoord();
      int y = node.getYcoord();
      String floor = node.getFloor();

      switch (floor) {
        case "L1":
          setFloor(0);
        case "L2":
          setFloor(1);
        case "1":
          setFloor(2);
        case "2":
          setFloor(3);
        case "3":
          setFloor(4);
        case "G":
          System.out.println("You broke me. Find where I broke in Service Request Map. Despair.");
      }
      centerOnNode(x, y);
    } else {
      System.out.println("DEV Error: The node was null in ServiceRequestMap");
    }
  }

  public void centerOnNode(int x, int y) {
    Platform.runLater(() -> map.animate(Duration.millis(50)).centreOn(new Point2D(x, y)));
  }

  public void changeFloor(int floor) {

    switch (floor) {
      case 0:
        floor1Button.setStyle("-fx-background-color: #C9E0F8");
      case 1:
        floor2Button.setStyle("-fx-background-color: #C9E0F8");
      case 2:
        floor3Button.setStyle("-fx-background-color: #C9E0F8");
      case 3:
        floor4Button.setStyle("-fx-background-color: #C9E0F8");
      case 4:
        floor5Button.setStyle("-fx-background-color: #C9E0F8");
    }

    switch (floor) {
      case 0:
        {
          floor1Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
          currentFloor = 0;
          setFloor(0);
        }
      case 1:
        {
          floor2Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
          currentFloor = 1;
          setFloor(1);
        }
      case 2:
        {
          floor3Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
          currentFloor = 2;
          setFloor(2);
        }
      case 3:
        {
          floor4Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
          currentFloor = 3;
          setFloor(3);
        }
      case 4:
        {
          floor5Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
          currentFloor = 4;
          setFloor(4);
        }
    }
  }
}
