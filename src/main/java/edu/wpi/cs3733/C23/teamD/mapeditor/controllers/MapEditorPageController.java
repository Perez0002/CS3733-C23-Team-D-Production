package edu.wpi.cs3733.C23.teamD.mapeditor.controllers;

import static edu.wpi.cs3733.C23.teamD.database.util.Ddb.*;

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
import java.util.Date;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import net.kurobako.gesturefx.GesturePane;

public class MapEditorPageController {

  boolean helpVisible = false;

  @FXML private BorderPane mapEditorPane;
  @FXML private BorderPane mapPlacement;

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

  private MFXButton[] floorButtons = new MFXButton[5];

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

        for (MapEdge edge : edgeList) {
          edge.getEdgeRepresentation().setVisible(edgesShown);
        }
      }
    };
  }

  public EventHandler<ActionEvent> changeFloor(int floor) {

    return new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        if (floor != currentFloor) {
          for (int i = 0; i < 5; i++) {
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
      pathNodes.put(move.getNodeID(), new PathNode(move.getNode(), move.getLocation()));
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
      pathNodes.get(edge.getFromNode().getNodeID()).getEdgeList().add(edge1);
      pathNodes.get(edge.getToNode().getNodeID()).getEdgeList().add(edge2);
      edge2.setEdge(edge);
      MapEdge tempMapEdge = new MapEdge(edge1);
      edgeList.add(tempMapEdge);

      tempMapEdge.setNodes(
          mapNodes.get(edge.getFromNode().getNodeID()), mapNodes.get(edge.getToNode().getNodeID()));
    }

    mapPlacement.getStyleClass().add("mapEditorMapHolder");
    toggleEdgesButton.getStyleClass().add("mapEditorFloorButtonSelected");

    // Setup for calculating average x and y
    double totalX = 0;
    double totalY = 0;
    int total = 0;

    floorButtons[0] = floorL1Button;
    floorButtons[1] = floorL2Button;
    floorButtons[2] = floor1Button;
    floorButtons[3] = floor2Button;
    floorButtons[4] = floor3Button;

    floorL1Button.setOnAction(changeFloor(0));
    floorL2Button.setOnAction(changeFloor(1));
    floor1Button.setOnAction(changeFloor(2));
    floor2Button.setOnAction(changeFloor(3));
    floor3Button.setOnAction(changeFloor(4));
    toggleEdgesButton.setOnAction(toggleEdges());

    // Creating GesturePane to show
    this.changeFloor(0).handle(null);
  }
}
