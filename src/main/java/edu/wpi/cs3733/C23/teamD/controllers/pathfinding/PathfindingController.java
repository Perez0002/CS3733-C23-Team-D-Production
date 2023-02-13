package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.controllers.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
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

  private MFXButton[] floorButtons = new MFXButton[5];

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
    aStarButton.setDisable(true);
    BFSButton.setDisable(false);
    DFSButton.setDisable(false);
  }

  @FXML
  public void setBFS() {
    algorithm = "BFS";
    aStarButton.setDisable(false);
    BFSButton.setDisable(true);
    DFSButton.setDisable(false);
  }

  @FXML
  public void setDFS() {
    algorithm = "DFS";
    aStarButton.setDisable(false);
    BFSButton.setDisable(false);
    DFSButton.setDisable(true);
  }

  public EventHandler<ActionEvent> changeFloor(int floor) {

    return new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        for (int i = 0; i < 5; i++) {
          if (i == floor) {
            floorButtons[i].setDisable(true);
          } else {
            floorButtons[i].setDisable(false);
          }
        }
        pathfindingBorderPane.setCenter(
            MapFactory.startBuild().withNodes(path).withEdges().build(floor));
      }
    };
  }

  @FXML
  public void initialize() {
    floorButtons[0] = floor1Button;
    floorButtons[1] = floor2Button;
    floorButtons[2] = floor3Button;
    floorButtons[3] = floor4Button;
    floorButtons[4] = floor5Button;
    this.mainMap = new GraphMap();
    mainMap.initFromDB();
    pathfindingBorderPane.setCenter(MapFactory.startBuild().build(0));

    floor1Button.setOnAction(changeFloor(0));
    floor2Button.setOnAction(changeFloor(1));
    floor3Button.setOnAction(changeFloor(2));
    floor4Button.setOnAction(changeFloor(3));
    floor5Button.setOnAction(changeFloor(4));
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
    Pathfinder pathfinder = new Pathfinder(mainMap);

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
