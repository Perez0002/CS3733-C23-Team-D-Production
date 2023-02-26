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
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.kurobako.gesturefx.GesturePane;

public class MapEditorPageController {

  boolean helpVisible = false;

  @FXML private BorderPane mapEditorPane;
  @FXML private BorderPane mapPlacement;
  @FXML private MFXButton nodeButton;
  @FXML private MFXButton edgeButton;
  @FXML private MFXButton multiNodeButton;
  @FXML private MFXButton multiEdgeButton;
  @FXML private HBox modeHolder;
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
  Rectangle selectArea = null;

  DoubleProperty rectStartX;
  DoubleProperty rectStartY;
  DoubleProperty rectEndX;
  DoubleProperty rectEndY;
  private BooleanProperty nodeMode;
  private BooleanProperty edgeMode;
  private BooleanProperty multiNodeMode;
  private ArrayList<MapNode> nodeList = new ArrayList<>();
  private ArrayList<MapEdge> edgeList = new ArrayList<>();
  private ArrayList<MapNode> selected = new ArrayList<>();
  private MFXButton[] floorButtons = new MFXButton[6];

  public MapEditorPageController() {
    nodeMode = new SimpleBooleanProperty();
    nodeMode.setValue(true);
    edgeMode = new SimpleBooleanProperty();
    edgeMode.setValue(false);
    multiNodeMode = new SimpleBooleanProperty();
    multiNodeMode.setValue(false);
    rectStartX = new SimpleDoubleProperty();
    rectStartY = new SimpleDoubleProperty();
    rectEndX = new SimpleDoubleProperty();
    rectEndY = new SimpleDoubleProperty();
  }

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

        gesturePane = MapFactory.startBuild().withNodes(nodeList).withEdges(edgeList).build(floor);

        ((AnchorPane) gesturePane.getContent())
            .setOnMouseClicked(
                e -> {
                  if (e.isStillSincePress() && multiNodeMode.getValue()) {
                    for (MapNode node : selected) {
                      node.getNodeRepresentation().setFill(MapNode.NO_SELECTION);
                    }
                    selected.clear();
                  }
                });

        ((AnchorPane) gesturePane.getContent())
            .setOnMouseDragged(
                e -> {
                  if (multiNodeMode.getValue()) {
                    gesturePane.setGestureEnabled(false);
                    if (selectArea == null) {
                      selectArea = new Rectangle();

                      rectStartX.setValue(e.getX());
                      rectStartY.setValue(e.getY());

                      selectArea.layoutYProperty().setValue(e.getY());
                      selectArea.layoutXProperty().setValue(e.getX());

                      selectArea
                          .heightProperty()
                          .bind(rectEndY.subtract(selectArea.layoutYProperty()));
                      selectArea
                          .widthProperty()
                          .bind(rectEndX.subtract(selectArea.layoutXProperty()));

                      selectArea.setFill(Color.rgb(0xCD, 0xDF, 0xF6, 0.5));
                      ((AnchorPane) gesturePane.getContent()).getChildren().add(selectArea);
                    }

                    if (rectStartX.getValue() > e.getX()) {
                      selectArea.layoutXProperty().setValue(e.getX());
                    } else {
                      rectEndX.setValue(e.getX());
                    }

                    if (rectStartY.getValue() > e.getY()) {
                      selectArea.layoutYProperty().setValue(e.getY());
                    } else {
                      rectEndY.setValue(e.getY());
                    }
                  }
                });

        ((AnchorPane) gesturePane.getContent())
            .setOnMouseReleased(
                e -> {
                  if (multiNodeMode.getValue()) {
                    gesturePane.setGestureEnabled(true);

                    for (MapNode node : nodeList) {
                      if (node.getNodeX().getValue() < rectEndX.getValue()
                          && node.getNodeX().getValue() > selectArea.layoutXProperty().getValue()
                          && node.getNodeY().getValue() < rectEndY.getValue()
                          && node.getNodeY().getValue() > selectArea.layoutYProperty().getValue()) {
                        selected.add(node);
                        node.getNodeRepresentation().setFill(MapNode.SELECTED);
                      }
                    }

                    if (selectArea != null) {
                      selectArea.widthProperty().unbind();
                      selectArea.heightProperty().unbind();
                      ((AnchorPane) gesturePane.getContent()).getChildren().remove(selectArea);
                      selectArea = null;
                      rectEndX.setValue(0);
                      rectEndY.setValue(0);
                    }
                  }
                });

        mapPlacement.setCenter(gesturePane);
        mapPlacement.requestFocus();
        currentFloor = floor;
      }
    };
  }

  @FXML
  public void initialize() {
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
      MapNode tempMapNode =
          new MapEditorMapNode(pathNodes.get(node), nodeMode, edgeMode, multiNodeMode);
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
      pathNodes.get(edge.getFromNode().getNodeID()).getEdgeList().add(edge1);
      pathNodes.get(edge.getToNode().getNodeID()).getEdgeList().add(edge2);

      MapEdge tempMapEdge = new MapEdge(edge1, edgeMode);
      edgeList.add(tempMapEdge);

      tempMapEdge.setNodes(
          mapNodes.get(edge.getFromNode().getNodeID()), mapNodes.get(edge.getToNode().getNodeID()));
    }

    nodeButton.setDisable(true);
    edgeButton.setDisable(false);
    multiNodeButton.setDisable(false);
    nodeButton.getStyleClass().add("mapEditorFloorButtonSelected");
    edgeButton.getStyleClass().add("mapEditorFloorButton");
    multiNodeButton.getStyleClass().add("mapEditorFloorButton");

    nodeButton.setOnAction(
        event -> {
          nodeMode.setValue(true);
          edgeMode.setValue(false);
          multiNodeMode.setValue(false);

          nodeButton.setDisable(true);
          edgeButton.setDisable(false);
          multiNodeButton.setDisable(false);

          nodeButton.getStyleClass().add("mapEditorFloorButtonSelected");
          nodeButton.getStyleClass().remove("mapEditorFloorButton");

          edgeButton.getStyleClass().remove("mapEditorFloorButtonSelected");
          edgeButton.getStyleClass().add("mapEditorFloorButton");

          multiNodeButton.getStyleClass().remove("mapEditorFloorButtonSelected");
          multiNodeButton.getStyleClass().add("mapEditorFloorButton");
        });

    edgeButton.setOnAction(
        event -> {
          nodeMode.setValue(false);
          edgeMode.setValue(true);
          multiNodeMode.setValue(false);

          nodeButton.setDisable(false);
          edgeButton.setDisable(true);
          multiNodeButton.setDisable(false);

          nodeButton.getStyleClass().remove("mapEditorFloorButtonSelected");
          nodeButton.getStyleClass().add("mapEditorFloorButton");

          edgeButton.getStyleClass().add("mapEditorFloorButtonSelected");
          edgeButton.getStyleClass().remove("mapEditorFloorButton");

          multiNodeButton.getStyleClass().remove("mapEditorFloorButtonSelected");
          multiNodeButton.getStyleClass().add("mapEditorFloorButton");
        });

    multiNodeButton.setOnAction(
        event -> {
          nodeMode.setValue(false);
          edgeMode.setValue(false);
          multiNodeMode.setValue(true);

          nodeButton.setDisable(false);
          edgeButton.setDisable(false);
          multiNodeButton.setDisable(true);

          nodeButton.getStyleClass().remove("mapEditorFloorButtonSelected");
          nodeButton.getStyleClass().add("mapEditorFloorButton");

          edgeButton.getStyleClass().remove("mapEditorFloorButtonSelected");
          edgeButton.getStyleClass().add("mapEditorFloorButton");

          multiNodeButton.getStyleClass().add("mapEditorFloorButtonSelected");
          multiNodeButton.getStyleClass().remove("mapEditorFloorButton");
        });

    mapPlacement.getStyleClass().add("mapEditorMapHolder");
    toggleEdgesButton.getStyleClass().add("mapEditorFloorButtonSelected");

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

    Platform.runLater(
        () -> {
          modeHolder.setMaxHeight(nodeButton.getHeight());
          modeHolder.setMaxWidth(
              5
                  + nodeButton.getWidth()
                  + 10
                  + edgeButton.getWidth()
                  + 10
                  + multiNodeButton.getWidth()
                  + 10
                  + multiEdgeButton.getWidth()
                  + 5);
        });

    // Creating GesturePane to show
    this.changeFloor(1).handle(null);
  }
}
