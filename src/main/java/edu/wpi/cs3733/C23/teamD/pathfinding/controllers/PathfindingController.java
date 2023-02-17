package edu.wpi.cs3733.C23.teamD.pathfinding.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.Pathfinder;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.RoomPickComboBoxController;
import io.github.palexdev.materialfx.controls.MFXButton;
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

  private RoomPickComboBoxController comboBox;

  private boolean helpVisible = false;

  private GraphMap mainMap;

  private String algorithm = "AStar";
  private ArrayList<Node> path = new ArrayList<Node>();

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
            MapFactory.startBuild().withNodes(path).withEdges().build(floor));
      }
    };
  }

  @FXML
  public void initialize() {

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

    this.mainMap = new GraphMap();
    mainMap.initFromDB();
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
      System.out.println(path.size());
      if (path.size() == 1) {
        pathResultText.setText("The Chosen Start and End Locations are Identical");
      } else if (path.size() == 0) {
        pathResultText.setText("There is no Valid Path Between These Two Locations");
      } else {
        changeFloor(converter.get(mainMap.getNode(startNode).getFloor())).handle(null);
      }
    } else {
      pathResultText.setText("Incorrect Node Data Entered");
    }
  }
}
