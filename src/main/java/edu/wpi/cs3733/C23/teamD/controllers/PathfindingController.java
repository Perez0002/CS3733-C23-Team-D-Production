package edu.wpi.cs3733.C23.teamD.controllers;
import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.PathNode;
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

  @FXML private MFXTextField endRoom;

  @FXML private MFXTextField startRoom;

  @FXML private Text pathResultText;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void clearFields() {}

  @FXML
  void displayHelp() {}

  @FXML
  void submit() {
    GraphMap mainMap = new GraphMap();
    System.out.println("hi");

    Pathfinder PathfinderAStar = new Pathfinder(mainMap);
    ArrayList<PathNode> Path = new ArrayList<PathNode>();
    System.out.println(startRoom.getText());
    System.out.println(endRoom.getText());

    Path =
        PathfinderAStar.aStarSearch(
            mainMap.getNode(startRoom.getText()), mainMap.getNode(endRoom.getText()));
    System.out.println(Path.toString());
    pathResultText.setText(Path.toString());
  }
}
