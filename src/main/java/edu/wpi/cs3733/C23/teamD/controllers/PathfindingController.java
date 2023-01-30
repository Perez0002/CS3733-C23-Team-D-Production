package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PathfindingController {
  @FXML private MFXButton cancelButton;

  @FXML private MFXComboBox<String> startRoomComboBox;
  @FXML private Text endRoomHelpText;

  @FXML private Text startRoomHelpText;

  @FXML private MFXTextField endRoom;

  @FXML private MFXTextField startRoom;

  @FXML private Text pathResultText;

  private boolean helpVisible = false;

  private HashMap<String, String> hTable = new HashMap<>();

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    hTable.put("some room", "CCONF001L1");
    hTable.put("some other room", "CCONF002L1");

    startRoomComboBox.setItems(FXCollections.observableArrayList(hTable.values()));
  }

  @FXML
  void clearFields() {
    endRoom.clear();
    startRoom.clear();
  }

  @FXML
  void displayHelp() {
    helpVisible = !helpVisible;
    endRoomHelpText.setVisible(helpVisible);
    startRoomHelpText.setVisible((helpVisible));
  }

  @FXML
  void submit() {
    GraphMap mainMap = new GraphMap();
    System.out.println("hi");
    mainMap.initFromCSV("data/L1Nodes.csv", "data/L1Edges.csv");
    System.out.println("hi");
    Pathfinder PathfinderAStar = new Pathfinder(mainMap);
    ArrayList<PathNode> Path = new ArrayList<PathNode>();
    System.out.println(startRoom.getText());
    System.out.println(endRoom.getText());
    System.out.println(this.hTable.get(startRoomComboBox.getValue()));

    Path =
        PathfinderAStar.aStarSearch(
            mainMap.getNode(startRoom.getText()), mainMap.getNode(endRoom.getText()));
    String out = "";
    out += "[";
    for (PathNode n : Path) {
      out += " " + n.getShortName() + ",";
    }
    pathResultText.setText(out);
  }
}
