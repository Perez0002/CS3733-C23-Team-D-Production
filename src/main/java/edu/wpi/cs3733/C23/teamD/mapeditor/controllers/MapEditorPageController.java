package edu.wpi.cs3733.C23.teamD.mapeditor.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
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
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
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
  @FXML private MFXButton toggleLabelsButton;

  private GesturePane gesturePane;
  private int currentFloor = -1;
  private boolean edgesShown = true;

  private boolean labelsShown = true;
  private boolean labelsVisible = true;
  private ArrayList<MapNode> nodeList = new ArrayList<>();
  private ArrayList<MapEdge> edgeList = new ArrayList<>();

  @Getter public static ArrayList<MapNode> nodeList = new ArrayList<>();
  @Getter public static ArrayList<MapEdge> edgeList = new ArrayList<>();

  private MFXButton[] floorButtons = new MFXButton[6];

  @FXML
  void openHomepage() {
    // Navigates to home page
    Navigation.navigate(Screen.HOME);
  }

  public EventHandler<ActionEvent> toggleEdges() {
    return event -> {
      edgesShown = !edgesShown;

      if (edgesShown) {
        toggleEdgesButton.getStyleClass().add("mapEditorFloorButtonSelected");
        toggleEdgesButton.getStyleClass().remove("mapEditorFloorButton");
      } else {
        toggleEdgesButton.getStyleClass().remove("mapEditorFloorButtonSelected");
        toggleEdgesButton.getStyleClass().add("mapEditorFloorButton");
      }

      for (MapEdge edge : edgeList) {
        edge.getEdgeRepresentation().setVisible(edgesShown);
      }
    };
  }

  public EventHandler<ActionEvent> toggleLabels() {
    return event -> {
      labelsShown = !labelsShown;
      labelsVisible = !labelsVisible;
      if (labelsShown) {
        toggleLabelsButton.getStyleClass().add("mapEditorFloorButtonSelected");
        toggleLabelsButton.getStyleClass().remove("mapEditorFloorButton");
      } else {
        toggleLabelsButton.getStyleClass().remove("mapEditorFloorButtonSelected");
        toggleLabelsButton.getStyleClass().add("mapEditorFloorButton");
      }

      AnchorPane holder = (AnchorPane) ((GesturePane) mapPlacement.getCenter()).getContent();
      for (Node node : holder.getChildren()) {
        if (node instanceof TextArea) {
          node.setVisible(labelsShown);
        }
      }
    };
  }

  public EventHandler<ActionEvent> changeFloor(int floor) {

    return event -> {
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
            MapFactory.startBuild()
                .withNodes(nodeList)
                .setLabelsVisible(labelsVisible)
                .withEdges(edgeList)
                .build(floor);
        mapPlacement.setCenter(gesturePane);
        currentFloor = floor;
      }
    };
  }

  @FXML
  public void initialize() {

    nodeList.clear();
    edgeList.clear();

    ArrayList<Edge> baseEdgeList = FDdb.getInstance().getAllEdges();
    ArrayList<Move> baseMoveList = FDdb.getInstance().getAllMoves();

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
      PathEdge edge2 =
          new PathEdge(
              pathNodes.get(edge.getToNode().getNodeID()),
              pathNodes.get(edge.getFromNode().getNodeID()));
      if (pathNodes.get(edge.getFromNode().getNodeID()) != null) {
        pathNodes.get(edge.getFromNode().getNodeID()).getEdgeList().add(edge1);
      }

      if (pathNodes.get(edge.getToNode().getNodeID()) != null) {
        pathNodes.get(edge.getToNode().getNodeID()).getEdgeList().add(edge2);
      }

      MapEdge tempMapEdge = new MapEdge(edge1);
      tempMapEdge.getEdge().setEdge(edge);
      edgeList.add(tempMapEdge);

      tempMapEdge.setNodes(
          mapNodes.get(edge.getFromNode().getNodeID()), mapNodes.get(edge.getToNode().getNodeID()));
    }

    mapPlacement.getStyleClass().add("mapEditorMapHolder");
    toggleEdgesButton.getStyleClass().add("mapEditorFloorButtonSelected");
    toggleLabelsButton.getStyleClass().add("mapEditorFloorButtonSelected");

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
    toggleLabelsButton.setOnAction(toggleLabels());

    // Creating GesturePane to show
    this.changeFloor(1).handle(null);
    this.toggleLabels().handle(null);
  }
}
