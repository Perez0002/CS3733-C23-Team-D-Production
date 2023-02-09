package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.controllers.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import java.util.ArrayList;
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

  private RoomPickComboBoxController comboBox;

  private boolean helpVisible = false;

  private GraphMap mainMap;
  private MapDrawController pathDrawController;

  public PathfindingController() {}

  @FXML
  public void initialize() {
    this.mainMap = new GraphMap();
    mainMap.initFromDB();
    pathDrawController = new MapDrawController();
    pathfindingBorderPane.setCenter(
        pathDrawController.genMapFromNodesWithEdges(new ArrayList<Node>()));
  }

  @FXML
  void clearFields() {
    startRoomComboBoxController.clearForm();
    endRoomComboBoxController.clearForm();
  }

  @FXML
  void displayHelp() {
    helpVisible = !helpVisible;
    endRoomHelpText.setVisible(helpVisible);
    startRoomHelpText.setVisible((helpVisible));
  }

  @FXML
  void submit() {
    Pathfinder PathfinderAStar = new Pathfinder(mainMap);
    ArrayList<Node> path = new ArrayList<Node>();

    String startNode = startRoomComboBoxController.getNodeValue();
    String endNode = endRoomComboBoxController.getNodeValue();

    if (startNode != null && endNode != null) {
      path = PathfinderAStar.aStarSearch(mainMap.getNode(startNode), mainMap.getNode(endNode));
      if (path.size() == 1) {
        pathResultText.setText("The Chosen Start and End Locations are Identical");
      } else if (path.size() == 0) {
        pathResultText.setText("There is no Valid Path Between These Two Locations");
      } else {
        GesturePane sceneNode = MapFactory.startBuild().withNodes(path).withEdges().build();
        sceneNode
            .animate(Duration.millis(200))
            .centreOn(
                new Point2D(
                    mainMap.getNode(startNode).getXcoord() - App.getPrimaryStage().getWidth() / 2,
                    mainMap.getNode(endNode).getYcoord() - App.getPrimaryStage().getHeight() / 2));
        pathfindingBorderPane.setCenter(sceneNode);
      }
    } else {
      pathResultText.setText("Incorrect Node Data Entered");
    }
  }
}
