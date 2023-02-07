package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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

  @FXML private Text pathResultText;

  private RoomPickComboBoxController comboBox;

  private boolean helpVisible = false;

  private GraphMap mainMap;

  private Pathfinder PathfinderAStar;

  public PathfindingController() {}

  @FXML
  public void initialize() {
    this.mainMap = new GraphMap();
    mainMap.initFromDB();

    PathfinderAStar = new Pathfinder(mainMap);
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
    ArrayList<Node> Path = new ArrayList<Node>();

    String startNode = startRoomComboBoxController.getNodeValue();
    String endNode = endRoomComboBoxController.getNodeValue();
    if (startNode != null && endNode != null) {
      Path = PathfinderAStar.aStarSearch(mainMap.getNode(startNode), mainMap.getNode(endNode));
      String out = "";
      out += "[";
      for (Node n : Path) {
        out += " " + n.getNodeID() + ",";
      }
      out = out.substring(0, out.length() - 2) + " ]";
      pathResultText.setText(out);

    } else {
      pathResultText.setText("Incorrect Node Data Entered");
    }
  }
}
