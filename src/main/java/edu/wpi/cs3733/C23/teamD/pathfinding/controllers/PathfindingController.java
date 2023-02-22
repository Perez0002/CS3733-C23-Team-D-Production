package edu.wpi.cs3733.C23.teamD.pathfinding.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEdge;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.PathfindingMapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.Pathfinder;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.RoomPickComboBoxController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class PathfindingController {

  @FXML private Parent roomPicker;
  @FXML private RoomPickComboBoxController roomPickerController;
  @FXML private Text endRoomHelpText;

  @FXML private Text startRoomHelpText;

  @FXML private Parent startRoomComboBox;

  @FXML private RoomPickComboBoxController startRoomComboBoxController;

  @FXML private Parent endRoomComboBox;

  @FXML private RoomPickComboBoxController endRoomComboBoxController;

  @FXML private Label pathResultText;

  @FXML private BorderPane pathfindingBorderPane;

  @FXML private MFXButton floor1Button;

  @FXML private MFXButton floor2Button;

  @FXML private MFXButton floor3Button;

  @FXML private MFXButton floor4Button;

  @FXML private MFXButton floor5Button;

  private MFXButton[] floorButtons = new MFXButton[5];

  @FXML private MFXButton aStarButton;

  @FXML private MFXButton BFSButton;

  @FXML private MFXButton DFSButton;
  @FXML private MFXToggleButton serviceRequestLocationToggle;

  private RoomPickComboBoxController comboBox;

  private boolean helpVisible = false;
  HashMap<String, Integer> converter = new HashMap<String, Integer>();

  private String algorithm = "AStar";
  private ArrayList<PathNode> path = new ArrayList<>();
  private ArrayList<MapNode> mapNodes = new ArrayList<>();
  private ArrayList<MapEdge> mapEdges = new ArrayList<>();

  public PathfindingController() {}

  @FXML
  public void setAStar() {
    algorithm = "AStar";
    DFSButton.setStyle("-fx-background-color: #C9E0F8");
    BFSButton.setStyle("-fx-background-color: #C9E0F8");
    aStarButton.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
  }

  @FXML
  public void setBFS() {
    algorithm = "BFS";
    DFSButton.setStyle("-fx-background-color: #C9E0F8");
    BFSButton.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
    aStarButton.setStyle("-fx-background-color: #C9E0F8");
  }

  @FXML
  public void setDFS() {
    algorithm = "DFS";
    DFSButton.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
    BFSButton.setStyle("-fx-background-color: #C9E0F8");
    aStarButton.setStyle("-fx-background-color: #C9E0F8");
  }

  public EventHandler<ActionEvent> changeFloor(int floor) {

    return new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        for (int i = 0; i < 5; i++) {
          if (i == floor) {
            floorButtons[i].setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
          } else {
            floorButtons[i].setStyle("-fx-background-color: #C9E0F8");
          }
        }
        pathfindingBorderPane.setCenter(
            MapFactory.startBuild().withNodes(mapNodes).withEdges(mapEdges).build(floor));
      }
    };
  }

  @FXML
  public void initialize() {
    converter.put("L1", 0);
    converter.put("L2", 1);
    converter.put("1", 2);
    converter.put("2", 3);
    converter.put("3", 4);
    floor1Button.setOnAction(changeFloor(0));
    floor2Button.setOnAction(changeFloor(1));
    floor3Button.setOnAction(changeFloor(2));
    floor4Button.setOnAction(changeFloor(3));
    floor5Button.setOnAction(changeFloor(4));

    floorButtons[0] = floor1Button;
    floorButtons[1] = floor2Button;
    floorButtons[2] = floor3Button;
    floorButtons[3] = floor4Button;
    floorButtons[4] = floor5Button;

    pathfindingBorderPane.setCenter(MapFactory.startBuild().build(0));
    setAStar();
    floor1Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
  }

  private void switchFloor(int fl) {}

  @FXML
  void displayHelp() {
    helpVisible = !helpVisible;
    endRoomHelpText.setVisible(helpVisible);
    startRoomHelpText.setVisible((helpVisible));
  }

  @FXML
  void submit() {
    Pathfinder pathfinder = new Pathfinder();

    ArrayList<Edge> baseEdgeList = FDdb.getInstance().getAllEdges();
    ArrayList<Move> baseMoveList = FDdb.getInstance().getAllMoves();

    HashMap<String, PathNode> pathNodes = new HashMap<>();
    for (Move move : baseMoveList) {
      pathNodes.put(move.getNodeID(), new PathNode(move.getNode(), move.getLocation()));
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
    }

    String startNode = startRoomComboBoxController.getNodeValue();
    String endNode = endRoomComboBoxController.getNodeValue();

    if (startNode != null && endNode != null) {
      path = pathfinder.pathfind(pathNodes.get(startNode), pathNodes.get(endNode), algorithm);
      if (path.size() == 1) {
        pathResultText.setText("The Chosen Start and End Locations are Identical");
      } else if (path.size() == 0) {
        pathResultText.setText("There is no Valid Path Between These Two Locations");
      } else {
        ArrayList<MapNode> mapNodes = new ArrayList<>();
        ArrayList<MapEdge> mapEdges = new ArrayList<>();
        MapNode lastNode = null;
        for (PathNode node : path) {
          PathfindingMapNode pathNode = new PathfindingMapNode(node);
          pathNode.setFloorSwitchEvent(changeFloor(converter.get(node.getNode().getFloor())));
          mapNodes.add(pathNode);
          if (lastNode != null) {
            MapEdge edge = new MapEdge(new PathEdge(lastNode.getNode(), node));
            edge.setNodes(lastNode, pathNode);
            mapEdges.add(edge);
          }

          lastNode = pathNode;
        }
        for (int i = 0; i < mapNodes.size(); i++) {
          if (i - 1 >= 0) {
            ((PathfindingMapNode) mapNodes.get(i))
                .addPrevNode((PathfindingMapNode) mapNodes.get(i - 1));
          }
          if (i + 1 < mapNodes.size()) {
            ((PathfindingMapNode) mapNodes.get(i))
                .addNextNode((PathfindingMapNode) mapNodes.get(i + 1));
          }
        }
        this.mapEdges.clear();
        this.mapNodes.clear();
        this.mapEdges.addAll(mapEdges);
        this.mapNodes.addAll(mapNodes);

        changeFloor(converter.get(pathNodes.get(startNode).getNode().getFloor())).handle(null);
      }
    } else {
      pathResultText.setText("Incorrect Node Data Entered");
    }
  }
}
