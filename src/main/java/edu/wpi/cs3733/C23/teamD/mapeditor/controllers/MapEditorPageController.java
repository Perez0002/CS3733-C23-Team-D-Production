package edu.wpi.cs3733.C23.teamD.mapeditor.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEdge;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEditorMapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import net.kurobako.gesturefx.GesturePane;

public class MapEditorPageController {

  boolean helpVisible = false;

  @FXML private BorderPane mapEditorPane;
  @FXML private BorderPane mapPlacement;

  @FXML private MFXButton floorGButton;
  @FXML private MFXButton floorL1Button;
  @FXML private MFXButton floorL2Button;
  @FXML private MFXButton floor1Button;
  @FXML private MFXButton floor2Button;
  @FXML private MFXButton floor3Button;
  @FXML private MFXButton toggleEdgesButton;
  private GesturePane gesturePane;
  private int currentFloor = -1;
  private boolean edgesShown = true;
  private ArrayList<MapNode> nodeList = new ArrayList<>();
  private ArrayList<MapEdge> edgeList = new ArrayList<>();

  private MFXButton[] floorButtons = new MFXButton[6];

  @FXML
  void openHomepage() {
    // Navigates to home page
    Navigation.navigate(Screen.HOME);
  }

  public EventHandler<ActionEvent> toggleEdges() {
    return new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        edgesShown = !edgesShown;

        if (edgesShown) {
          toggleEdgesButton.getStyleClass().add("mapEditorFloorButtonSelected");
          toggleEdgesButton.getStyleClass().remove("mapEditorFloorButton");
        } else {
          toggleEdgesButton.getStyleClass().remove("mapEditorFloorButtonSelected");
          toggleEdgesButton.getStyleClass().add("mapEditorFloorButton");
        }

        for (Node line :
            ((AnchorPane) ((GesturePane) mapPlacement.getCenter()).getContent()).getChildren()) {
          if (line instanceof Line) {
            line.setVisible(edgesShown);
          }
        }
      }
    };
  }

  public EventHandler<ActionEvent> changeFloor(int floor) {

    return new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        if (floor != currentFloor) {
          for (int i = 0; i < 6; i++) {
            if (i == floor) {
              // floorButtons[i].setDisable(true);
              floorButtons[i].getStyleClass().remove("mapEditorFloorButton");
              floorButtons[i].getStyleClass().add("mapEditorFloorButtonSelected");
            } else {
              floorButtons[i].setDisable(false);
              floorButtons[i].getStyleClass().remove("mapEditorFloorButtonSelected");
              floorButtons[i].getStyleClass().add("mapEditorFloorButton");
            }
          }

          gesturePane =
              MapFactory.startBuild().withNodes(nodeList).withEdges(edgeList).build(floor);
          gesturePane
              .getContent()
              .setOnMouseClicked(
                  e -> {
                    if (e.getClickCount() == 2) {
                      String nodeFloor = "";
                      switch (floor) {
                        case 0:
                          nodeFloor = "L1";
                          break;
                        case 1:
                          nodeFloor = "L2";
                          break;
                        case 2:
                          nodeFloor = "1";
                          break;
                        case 3:
                          nodeFloor = "2";
                          break;
                        case 4:
                          nodeFloor = "3";
                          break;
                      }
                      edu.wpi.cs3733.C23.teamD.database.entities.Node newBaseNode =
                          new edu.wpi.cs3733.C23.teamD.database.entities.Node(
                              (int) e.getX(), (int) e.getY(), nodeFloor, "");
                      LocationName nodeLocation = new LocationName("", "", "");
                      PathNode newPathNode = new PathNode(newBaseNode, new LocationName());
                      MapEditorMapNode newMapNode = new MapEditorMapNode(newPathNode);

                      ((AnchorPane) gesturePane.getContent())
                          .getChildren()
                          .add(newMapNode.getNodeRepresentation());
                      newMapNode.MakePopup(true);
                    }
                  });
          mapPlacement.setCenter(gesturePane);
          currentFloor = floor;
        }
      }
    };
  }

  @FXML
  public void initialize() {
    nodeList = new ArrayList<>();
    edgeList = new ArrayList<>();

    ArrayList<Edge> baseEdgeList = FDdb.getInstance().getAllEdges();
    ArrayList<Move> baseMoveList = FDdb.getInstance().getAllCurrentMoves(new Date());

    HashMap<String, PathNode> pathNodes = new HashMap<>();
    for (Move move : baseMoveList) {
      if (move.getNode() != null) {
        pathNodes.put(move.getNodeID(), new PathNode(move.getNode(), move.getLocation()));
      }
    }

    HashMap<String, MapNode> mapNodes = new HashMap<>();
    for (String node : pathNodes.keySet().stream().toList()) {
      MapNode tempMapNode = new MapEditorMapNode(pathNodes.get(node));
      mapNodes.put(node, tempMapNode);
      nodeList.add(tempMapNode);
    }

    for (Edge edge : baseEdgeList) {
      PathEdge edge1 =
          new PathEdge(
              pathNodes.get(edge.getFromNode().getNodeID()),
              pathNodes.get(edge.getToNode().getNodeID()));
      edge1.setEdge(edge);
      PathEdge edge2 =
          new PathEdge(
              pathNodes.get(edge.getToNode().getNodeID()),
              pathNodes.get(edge.getFromNode().getNodeID()));
      try {
        pathNodes.get(edge.getFromNode().getNodeID()).getEdgeList().add(edge1);
        pathNodes.get(edge.getToNode().getNodeID()).getEdgeList().add(edge2);
        edge2.setEdge(edge);
        MapEdge tempMapEdge = new MapEdge(edge1);
        edgeList.add(tempMapEdge);

        tempMapEdge.setNodes(
            mapNodes.get(edge.getFromNode().getNodeID()),
            mapNodes.get(edge.getToNode().getNodeID()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    mapPlacement.getStyleClass().add("mapEditorMapHolder");
    toggleEdgesButton.getStyleClass().add("mapEditorFloorButtonSelected");

    // Setup for calculating average x and y
    double totalX = 0;
    double totalY = 0;
    int total = 0;

    floorButtons[0] = floorGButton;
    floorButtons[1] = floorL1Button;
    floorButtons[2] = floorL2Button;
    floorButtons[3] = floor1Button;
    floorButtons[4] = floor2Button;
    floorButtons[5] = floor3Button;

    floorGButton.setOnAction(changeFloor(0));
    floorL1Button.setOnAction(changeFloor(1));
    floorL2Button.setOnAction(changeFloor(2));
    floor1Button.setOnAction(changeFloor(3));
    floor2Button.setOnAction(changeFloor(4));
    floor3Button.setOnAction(changeFloor(5));
    toggleEdgesButton.setOnAction(toggleEdges());

    // Creating GesturePane to show
    this.changeFloor(1).handle(null);
  }
}
