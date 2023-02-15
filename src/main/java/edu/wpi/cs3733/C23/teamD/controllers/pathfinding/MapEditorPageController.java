package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

  private ArrayList<Node> nodeList = new ArrayList<>();
  private GesturePane gesturePane;
  private MapEditorNodeController tempPopup;
  private HashMap<Node, MapEditorNodeController> activePopups = new HashMap<>();
  private int currentFloor = -1;

  private MFXButton[] floorButtons = new MFXButton[5];

  @FXML
  void openHomepage() {
    // Navigates to home page
    Navigation.navigate(Screen.HOME);
  }

  /**
   * @param node Node to bind event to
   * @return EventHandler<MouseEvent> to handle on click events
   */
  private EventHandler<MouseEvent> paneClickFunction(Node node) {
    // Return a new EventHandler<MouseEvent> based on the passed Node
    return new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {

        GesturePane gesturePane = (GesturePane) mapPlacement.getCenter();
        AnchorPane anchor = (AnchorPane) gesturePane.getContent();

        javafx.scene.Node tempPane = null;
        for (javafx.scene.Node n : anchor.getChildren()) {
          if (!(n instanceof Circle)) {
            continue;
          }

          if ((node.getNodeID() + "_pane").equals(n.getId())) {
            // Turn this Pane to red
            tempPane = n;
          }
        }

        if (tempPane == null) {
          tempPane =
              MapNodeFactory.startPathBuild()
                  .posX(event.getX())
                  .posY(event.getY())
                  .nodeID(nodeList.get(nodeList.size() - 1).getNodeID() + "_pane")
                  .build();
          anchor.getChildren().add(tempPane);
        }

        if (!activePopups.containsKey(node)) {
          if (tempPopup != null) {
            tempPopup.makePopupDisappear();
            tempPopup = null;
          }

          MapEditorNodeController newPopup =
              new MapEditorNodeController(node, tempPane, event.getSceneX(), event.getSceneY());
          final Circle nodeCircle = (Circle) tempPane;
          activePopups.put(node, newPopup);
          newPopup.setOnClose(
              e -> {
                activePopups.get(node).makePopupDisappear();
                activePopups.remove(node, newPopup);
                nodeCircle.setFill(Color.rgb(1, 58, 117));
              });
          newPopup.setOnSubmit(activePopups);
          newPopup.setOnDelete(activePopups);
          newPopup.makePopupAppear();
          ((Circle) tempPane).setFill(Color.rgb(204, 34, 34));
        }
      }
    };
  }

  private EventHandler<MouseEvent> paneEnterFunction(Node node) {
    // Return a new EventHandler<MouseEvent> based on the passed Node
    return new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        GesturePane gesturePane = (GesturePane) mapPlacement.getCenter();
        AnchorPane anchor = (AnchorPane) gesturePane.getContent();

        if (tempPopup == null && !activePopups.containsKey(node)) {
          javafx.scene.Node tempNode = null;
          for (javafx.scene.Node n : anchor.getChildren()) {
            if (n instanceof Circle) {
              if (n.getId().equals(node.getNodeID() + "_pane")) {
                tempNode = n;
                break;
              }
            }
          }

          tempPopup =
              new MapEditorNodeController(node, tempNode, event.getSceneX(), event.getSceneY());
          tempPopup.setOnClose(
              e -> {
                tempPopup.makePopupDisappear();
                tempPopup = null;
              });

          tempPopup.makePopupAppear();
        }
      }
    };
  }

  private EventHandler<MouseEvent> paneExitFunction(Node node) {
    // Return a new EventHandler<MouseEvent> based on the passed Node
    return new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        GesturePane gesturePane = (GesturePane) mapPlacement.getCenter();
        AnchorPane anchor = (AnchorPane) gesturePane.getContent();

        if (tempPopup != null && !activePopups.containsKey(node)) {
          tempPopup.makePopupDisappear();
          tempPopup = null;
        }
      }
    };
  }

  public EventHandler<ActionEvent> changeFloor(int floor) {

    return new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        for (Node popupNode : activePopups.keySet()) {
          activePopups.get(popupNode).makePopupDisappear();
          activePopups.remove(popupNode);
        }

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
              MapFactory.startBuild()
                  .withNodes(nodeList)
                  .withNodeFunctions(
                      node -> {
                        return paneClickFunction(node);
                      })
                  .withNodeMouseEnterFunctions(
                      node -> {
                        return paneEnterFunction(node);
                      })
                  .withNodeMouseExitFunctions(
                      node -> {
                        return paneExitFunction(node);
                      })
                  .build(floor);
          gesturePane
              .getContent()
              .setOnMouseClicked(
                  e -> {
                    if (e.getClickCount() >= 2) {
                      Node tempNode = new Node();
                      tempNode.setXcoord((int) e.getX());
                      tempNode.setYcoord((int) e.getY());
                      tempNode.setLocation(new LocationName());
                      paneClickFunction(tempNode).handle(e);
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
    nodeList = FDdb.getInstance().getAllNodes(); // Fetch Nodes
    connectNodestoLocations(nodeList);

    mapPlacement.getStyleClass().add("mapEditorMapHolder");
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

    // Calculating average x and y
    for (Node node : nodeList) {
      if (node.getFloor().equals("L1")) {
        totalX += node.getXcoord();
        totalY += node.getYcoord();
        total++;
      }
    }

    // Creating GesturePane to show
    this.changeFloor(0).handle(null);
  }
}
