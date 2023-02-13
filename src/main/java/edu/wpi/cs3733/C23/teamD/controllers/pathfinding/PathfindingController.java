package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.controllers.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

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

  @FXML private MFXButton aStarButton;

  @FXML private MFXButton BFSButton;

  @FXML private MFXButton DFSButton;

  private RoomPickComboBoxController comboBox;

  private boolean helpVisible = false;

  private GraphMap mainMap;

  private String algorithm = "AStar";
  private ArrayList<Node> path = new ArrayList<Node>();

  public PathfindingController() {}

  @FXML
  public void setAStar() {
    algorithm = "AStar";
  }

  @FXML
  public void setBFS() {
    algorithm = "BFS";
  }

  @FXML
  public void setDFS() {
    algorithm = "DFS";
  }

  public EventHandler<ActionEvent> changeFloor(int floor) {
    return new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        pathfindingBorderPane.setCenter(
            MapFactory.startBuild().withNodes(path).withEdges().build(floor));
      }
    };
  }

  @FXML
  public void initialize() {
    this.mainMap = new GraphMap();
    mainMap.initFromDB();
    pathfindingBorderPane.setCenter(MapFactory.startBuild().build(0));

    floor1Button.setOnAction(changeFloor(0));
    floor2Button.setOnAction(changeFloor(1));
    floor3Button.setOnAction(changeFloor(2));
    floor4Button.setOnAction(changeFloor(3));
    floor5Button.setOnAction(changeFloor(4));
  }

  @FXML
  void displayHelp() {
    helpVisible = !helpVisible;
    endRoomHelpText.setVisible(helpVisible);
    startRoomHelpText.setVisible((helpVisible));
  }

  @FXML
  void submit() {
    Pathfinder pathfinder = new Pathfinder(mainMap);

    String startNode = startRoomComboBoxController.getNodeValue();
    String endNode = endRoomComboBoxController.getNodeValue();

    HashMap<String, Integer> converter = new HashMap<String, Integer>();

    converter.put("L1", 0);
    converter.put("L2", 1);
    converter.put("1", 2);
    converter.put("2", 3);
    converter.put("3", 4);

    if (startNode != null && endNode != null) {
      path = pathfinder.pathfind(mainMap.getNode(startNode), mainMap.getNode(endNode), algorithm);
      if (path.size() == 1) {
        pathResultText.setText("The Chosen Start and End Locations are Identical");
      } else if (path.size() == 0) {
        pathResultText.setText("There is no Valid Path Between These Two Locations");
      } else {
        GesturePane sceneNode =
            MapFactory.startBuild()
                .withNodes(path)
                .withEdges()
                .build(converter.get(mainMap.getNode(startNode).getFloor()));
        sceneNode
            .animate(Duration.millis(200))
            .centreOn(
                new Point2D(
                    mainMap.getNode(startNode).getXcoord() - sceneNode.getViewportWidth(),
                    mainMap.getNode(endNode).getYcoord() - sceneNode.getViewportHeight()));

        pathfindingBorderPane.setCenter(sceneNode);
      }
    } else {
      pathResultText.setText("Incorrect Node Data Entered");
    }
  }
}
