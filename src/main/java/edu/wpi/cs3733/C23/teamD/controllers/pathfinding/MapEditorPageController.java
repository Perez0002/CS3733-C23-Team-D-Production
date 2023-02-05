package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.createJavaNodes;
import static edu.wpi.cs3733.C23.teamD.Ddb.makeConnection;

import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class MapEditorPageController {

  boolean helpVisible = false;

  @FXML private Text longNameHelpText;

  @FXML private MFXTextField longNameTextField;

  @FXML private BorderPane mapEditorPane;

  @FXML private Text nodeInformationText;

  @FXML private Text roomTypeHelpText;

  @FXML private MFXTextField roomTypeTextField;

  @FXML private Text shortNameHelpText;

  @FXML private MFXTextField shortNameTextField;

  @FXML
  void clearFields() {
    longNameTextField.clear();
    shortNameTextField.clear();
    roomTypeTextField.clear();
  }

  @FXML
  void delete() {}

  @FXML
  void openHomepage() {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void submit() {}

  @FXML
  void displayHelp() {
    helpVisible = !helpVisible;
    shortNameHelpText.setVisible(helpVisible);
    longNameHelpText.setVisible(helpVisible);
    roomTypeHelpText.setVisible(helpVisible);
  }

  @FXML
  public void initialize() {
    MapDrawController mapDrawer = new MapDrawController();
    Connection conn = makeConnection();
    ArrayList<Node> nodeList = createJavaNodes(conn);
    mapEditorPane.setCenter(mapDrawer.genMapFromNodes(nodeList));
  }
}
