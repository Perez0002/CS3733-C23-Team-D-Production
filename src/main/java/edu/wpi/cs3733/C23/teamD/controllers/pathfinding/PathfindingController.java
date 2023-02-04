package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.controllers.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class PathfindingController {
  @FXML private MFXButton cancelButton;

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

  public PathfindingController() {}

  @FXML
  public void initialize() {
    this.mainMap = new GraphMap();
    mainMap.initFromDB();

    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
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
    ArrayList<Node> Path = new ArrayList<Node>();

    String startNode = startRoomComboBoxController.getNodeValue();
    String endNode = endRoomComboBoxController.getNodeValue();

    if (startNode != null && endNode != null) {
      Path = PathfinderAStar.aStarSearch(mainMap.getNode(startNode), mainMap.getNode(endNode));
      if(Path.size()==1){
        pathResultText.setText("The Chosen Start and End Locations are Identical");
      }
      else if (Path.size()==0) {
        pathResultText.setText("There is no Valid Path Between These Two Locations");
      }
      else{
        MapDrawController pathDrawController= new MapDrawController();
        // Add point to navigate too here
        //Navigation.navigate();
      }
    }
    else {
      pathResultText.setText("Incorrect Node Data Entered");
    }
  }
}
