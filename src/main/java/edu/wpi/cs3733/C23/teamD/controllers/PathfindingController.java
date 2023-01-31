package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PathfindingController {
  @FXML private MFXButton cancelButton;

  @FXML private Text endRoomHelpText;

  @FXML private Text startRoomHelpText;

  @FXML private MFXTextField endRoom;

  @FXML private MFXTextField startRoom;

  @FXML private Text pathResultText;

  private boolean helpVisible = false;

  private GraphMap mainMap;

  @FXML
  public void initialize() {
    this.mainMap = new GraphMap();
    mainMap.initFromDB();

    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
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
    Pathfinder PathfinderAStar = new Pathfinder(mainMap);
    ArrayList<Node> Path = new ArrayList<Node>();

    if (mainMap.getNode(startRoom.getText()) != null
        && mainMap.getNode(endRoom.getText()) != null) {
      Path =
          PathfinderAStar.aStarSearch(
              mainMap.getNode(startRoom.getText()), mainMap.getNode(endRoom.getText()));
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
