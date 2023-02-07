package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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

  private ArrayList<Node> nodeList;
  private ArrayList<LocationName> locList;

  private MapDrawController mapDrawer;

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

  private EventHandler<MouseEvent> paneFunction(Node node) {
    return new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (!node.equals(currentNodeEdit)) {
          currentNodeEdit = node;
          nodeInformationText.setText(currentNodeEdit.getNodeID());
          longNameTextField.setText(currentNodeEdit.getLocation().getLongName());
          shortNameTextField.setText(currentNodeEdit.getLocation().getShortName());
          roomTypeTextField.setText(currentNodeEdit.getLocation().getLocationType());

          GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
          AnchorPane anchor = (AnchorPane) gesturePane.getContent();

          for (javafx.scene.Node n : anchor.getChildren()) {
            if ((node.getNodeID() + "_pane").equals(n.getId())) {
              n.setStyle("-fx-background-color: '#CC2222';");
            } else {
              n.setStyle("-fx-background-color: '#013A75';");
            }
          }
        } else {
          currentNodeEdit = null;
          nodeInformationText.setText("");
          longNameTextField.setText("");
          shortNameTextField.setText("");
          roomTypeTextField.setText("");

          GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
          AnchorPane anchor = (AnchorPane) gesturePane.getContent();

          for (javafx.scene.Node n : anchor.getChildren()) {
            n.setStyle("-fx-background-color: '#013A75';");
          }
        }
      }
    };
  }

  @FXML
  void submit() {
    // For now, this just does basic changes. Will be edited when the changes required are more
    // defined
    Node newNode = new Node();
    newNode.setLocation(
        new LocationName(
            longNameTextField.getText(),
            shortNameTextField.getText(),
            roomTypeTextField.getText()));
    newNode.setNodeID(currentNodeEdit.getNodeID());
    newNode.setXcoord(currentNodeEdit.getXcoord());
    newNode.setYcoord(currentNodeEdit.getYcoord());
    newNode.setBuilding(currentNodeEdit.getBuilding());
    newNode.setFloor(currentNodeEdit.getFloor());

    for (Edge edge : currentNodeEdit.getNodeEdges()) {
      for (Edge e : edge.getToNode().getNodeEdges()) {
        if (e.getToNode().equals(currentNodeEdit)) {
          edge.getToNode().getNodeEdges().remove(e);
          e.setToNode(null);
          e.setFromNode(null);
        }
      }
      edge.setToNode(null);
      edge.setFromNode(null);
    }

    newNode.setNodeEdges(new ArrayList<Edge>());

    nodeList.remove(currentNodeEdit);
    nodeList.add(newNode);

    GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
    AnchorPane anchor = (AnchorPane) gesturePane.getContent();

    for (javafx.scene.Node node : anchor.getChildren()) {
      if ((currentNodeEdit.getNodeID() + "_pane").equals(node.getId())) {
        anchor.getChildren().remove(node);
        break;
      }
    }

    // TODO should make a PaneFactory for this
    final Pane tempPane = new Pane();
    tempPane.setPrefSize(MapDrawController.NODE_WIDTH, MapDrawController.NODE_WIDTH);
    tempPane.setLayoutX(newNode.getXcoord() - MapDrawController.NODE_WIDTH / 2);
    tempPane.setLayoutY(newNode.getYcoord() - MapDrawController.NODE_WIDTH / 2);
    tempPane.setStyle("-fx-background-color: '#013A75';");
    tempPane.setOnMouseClicked(paneFunction(newNode));
    tempPane.setId(newNode.getNodeID() + "_pane");
    anchor.getChildren().add(tempPane);

    currentNodeEdit = newNode;

    // TODO update database to match
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
    mapDrawer = new MapDrawController();

    nodeList = createJavaNodes();
    connectNodestoLocations(nodeList);
    double totalX = 0;
    double totalY = 0;
    int total = 0;

    for (Node node : nodeList) {
      totalX += node.getXcoord();
      totalY += node.getYcoord();
      total++;
    }

    GesturePane gesturePane =
        mapDrawer.genMapFromNodes(
            nodeList,
            node -> {
              return paneFunction(node);
            });
    mapEditorPane.setCenter(gesturePane);
    gesturePane.zoomTo(0, new Point2D(totalX / total, totalY / total));
    gesturePane
        .animate(Duration.millis(500))
        .centreOn(
            new Point2D(
                (totalX / total) - App.getPrimaryStage().getScene().getWidth() / 2,
                (totalY / total) - App.getPrimaryStage().getScene().getHeight() / 2));
  }
}
