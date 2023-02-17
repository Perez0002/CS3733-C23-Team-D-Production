package edu.wpi.cs3733.C23.teamD.mapeditor.controllers;

import static edu.wpi.cs3733.C23.teamD.database.util.Ddb.*;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapNodeFactory;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
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

  private GesturePane gesturePane;
  private int currentFloor = -1;
  private ArrayList<Node> nodeList = new ArrayList<>();

  private MFXButton[] floorButtons = new MFXButton[5];

  @FXML
  void openHomepage() {
    // Navigates to home page
    Navigation.navigate(Screen.HOME);
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
              MapFactory.startBuild()
                  .withNodes(nodeList)
                  .withEdges()
                  .build(floor);

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

    ArrayList<Edge> edgeList = FDdb.getInstance().getAllEdges();
    ArrayList<Move> moveList = FDdb.getInstance().getAllMoves();

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
