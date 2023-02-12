package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.controllers.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import io.github.palexdev.materialfx.controls.MFXButton;
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

  @FXML
  public void initialize() {
    this.mainMap = new GraphMap();
    mainMap.initFromDB();
    pathfindingBorderPane.setCenter(MapFactory.startBuild().build(0));
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
    ArrayList<Node> path = new ArrayList<Node>();

    String startNode = startRoomComboBoxController.getNodeValue();
    String endNode = endRoomComboBoxController.getNodeValue();

    if (startNode != null && endNode != null) {
      path = pathfinder.pathfind(mainMap.getNode(startNode), mainMap.getNode(endNode), algorithm);
      if (path.size() == 1) {
        pathResultText.setText("The Chosen Start and End Locations are Identical");
      } else if (path.size() == 0) {
        pathResultText.setText("There is no Valid Path Between These Two Locations");
      } else {
        GesturePane sceneNode = MapFactory.startBuild().withNodes(path).withEdges().build(0);
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
