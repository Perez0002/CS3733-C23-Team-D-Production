package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.createJavaNodes;
import static edu.wpi.cs3733.C23.teamD.Ddb.makeConnection;

import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

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

  private Node currentNodeEdit;

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
  void submit() {

  }

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
    double sumX = 0;
    double sumY = 0;
    double total = nodeList.size();
    for (Node node : nodeList) {
      sumX += node.getXcoord();
      sumY += node.getYcoord();
    }
    double avgX = sumX / total;
    double avgY = sumY / total;
    GesturePane mainPane =
        mapDrawer.genMapFromNodes(
            nodeList,
            node -> {
              return new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                  currentNodeEdit = node;
                  nodeInformationText.setText(currentNodeEdit.getNodeID());
                  longNameTextField.setText("Can't get node name from Node");
                  shortNameTextField.setText("Can't get node name from Node");
                  roomTypeTextField.setText("Can't get node room type from Node");
                }
              };
            });
    mapEditorPane.setCenter(mainPane);
    mainPane.zoomTo(mainPane.getMinScale(), new Point2D(avgX, avgY));
    // TODO wwwwwwwhhhhhhhyyyyyyyy can't I get the size of the fluffing GesturePane ;-;
    mainPane.animate(Duration.millis(1500)).centreOn(new Point2D(avgX - 750, avgY - 750));
  }
}
