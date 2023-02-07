package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
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

  @FXML private MFXButton clearButton;

  @FXML private Text roomTypeHelpText;

  @FXML private Text shortNameHelpText;

  @FXML private MFXTextField xCoordTextField;

  @FXML private MFXTextField yCoordTextField;

  @FXML private MFXButton submitButton;
  @FXML private MFXButton addNodeButton;
  @FXML private MFXButton deleteNodeButton;

  private Node currentNodeEdit;

  private ArrayList<Node> nodeList;
  private MapDrawController mapDrawer;

  @FXML
  void clearFields() {
    // Clears all Text Fields
    longNameTextField.clear();
    yCoordTextField.clear();
    xCoordTextField.clear();
  }

  @FXML
  void openHomepage() {
    // Navigates to home page
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void deleteNode() {
    // TODO make delete Node
  }

  @FXML
  void addNode() {
    // TODO make add Node
  }

  /**
   *
   * @param node Node to bind event to
   * @return EventHandler<MouseEvent> to handle on click events
   */
  private EventHandler<MouseEvent> paneFunction(Node node) {
    // Return a new EventHandler<MouseEvent> based on the passed Node
    return new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (!node.equals(currentNodeEdit)) { // If node and currentNode are different
          // Select Node
          updateButtonsForNode(true);
          currentNodeEdit = node;

          // Setting default fields for Node
          xCoordTextField.setText(Integer.toString(currentNodeEdit.getXcoord()));
          yCoordTextField.setText(Integer.toString(currentNodeEdit.getYcoord()));
          longNameTextField.setText(currentNodeEdit.getLocation().getLongName());

          GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
          AnchorPane anchor = (AnchorPane) gesturePane.getContent();

          for (javafx.scene.Node n : anchor.getChildren()) {
            if ((node.getNodeID() + "_pane").equals(n.getId())) {
              n.setStyle("-fx-background-color: '#CC2222';"); // Turn this Pane to red
            } else {
              n.setStyle("-fx-background-color: '#013A75';"); // Turn other Pane's to default
            }
          }
        } else {
          // Deselect Node
          updateButtonsForNode(false);
          currentNodeEdit = null;
          clearFields();

          GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
          AnchorPane anchor = (AnchorPane) gesturePane.getContent();

          for (javafx.scene.Node n : anchor.getChildren()) {
            n.setStyle("-fx-background-color: '#013A75';"); // Setting all Panes to default color
          }
        }
      }
    };
  }

  @FXML
  void submit() {
    // For now, this just does basic changes. Will be edited when the changes required are more
    // defined
    Node newNode = new Node(); // New Node

    // Set Node Fields // TODO set these correctly
    newNode.setLocation(currentNodeEdit.getLocation());
    newNode.setNodeID(currentNodeEdit.getNodeID());
    newNode.setXcoord(currentNodeEdit.getXcoord());
    newNode.setYcoord(currentNodeEdit.getYcoord());
    newNode.setBuilding(currentNodeEdit.getBuilding());
    newNode.setFloor(currentNodeEdit.getFloor());

    // Breaks all Edges connected to this Node
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

    // Make new list of Edges
    newNode.setNodeEdges(new ArrayList<Edge>());

    // Remove current Node being edited from the list of active Nodes
    nodeList.remove(currentNodeEdit);
    // Add new Node to the list of active Nodes
    nodeList.add(newNode);

    // Get GesturePane and AnchorPane
    GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
    AnchorPane anchor = (AnchorPane) gesturePane.getContent();

    // Find the old Pane bound to old Node
    for (javafx.scene.Node node : anchor.getChildren()) {
      if ((currentNodeEdit.getNodeID() + "_pane").equals(node.getId())) {
        anchor.getChildren().remove(node); // Remove it
        break;
      }
    }

    // TODO should make a PaneFactory for this

    // Add a new Pane for the new Node
    final Pane tempPane = new Pane();
    tempPane.setPrefSize(MapDrawController.NODE_WIDTH, MapDrawController.NODE_WIDTH);
    tempPane.setLayoutX(newNode.getXcoord() - MapDrawController.NODE_WIDTH / 2);
    tempPane.setLayoutY(newNode.getYcoord() - MapDrawController.NODE_WIDTH / 2);
    tempPane.setStyle("-fx-background-color: '#013A75';");
    tempPane.setOnMouseClicked(paneFunction(newNode));
    tempPane.setId(newNode.getNodeID() + "_pane");
    anchor.getChildren().add(tempPane);

    // Reset Fields
    longNameTextField.setText("");
    xCoordTextField.setText("");
    yCoordTextField.setText("");

    // TODO update database to match

    currentNodeEdit = null; // set currentNodeEdit to null
  }

  private void updateButtonsForNode(boolean nodeSelected) {
    if (nodeSelected == true) {
      submitButton.setVisible(true);
      submitButton.setManaged(true);
      addNodeButton.setVisible(false);
      addNodeButton.setManaged(false);
      deleteNodeButton.setVisible(true);
      deleteNodeButton.setManaged(true);
      longNameTextField.setVisible(true);
      longNameTextField.setManaged(true);
      xCoordTextField.setVisible(true);
      xCoordTextField.setManaged(true);
      yCoordTextField.setVisible(true);
      yCoordTextField.setManaged(true);
      clearButton.setVisible(true);
      clearButton.setManaged(true);
    } else {
      submitButton.setVisible(false);
      submitButton.setManaged(false);
      addNodeButton.setVisible(true);
      addNodeButton.setManaged(true);
      deleteNodeButton.setVisible(false);
      deleteNodeButton.setManaged(false);
      longNameTextField.setVisible(false);
      longNameTextField.setManaged(false);
      xCoordTextField.setVisible(false);
      xCoordTextField.setManaged(false);
      yCoordTextField.setVisible(false);
      yCoordTextField.setManaged(false);
      clearButton.setVisible(false);
      clearButton.setManaged(false);
    }
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
    updateButtonsForNode(false);

    mapDrawer = new MapDrawController(); // Create a way to draw the Nodes

    nodeList = createJavaNodes(); // Fetch Nodes
    connectNodestoLocations(nodeList); // Connect Nodes to Locations

    // Setup for calculating average x and y
    double totalX = 0;
    double totalY = 0;
    int total = 0;

    // Calculating average x and y
    for (Node node : nodeList) {
      totalX += node.getXcoord();
      totalY += node.getYcoord();
      total++;
    }

    // Creating GesturePane to show
    GesturePane gesturePane =
        mapDrawer.genMapFromNodes(
            nodeList,
            node -> {
              return paneFunction(node);
            });
    // Setting center of BorderPane to the GesturePane
    mapEditorPane.setCenter(gesturePane);
    // Setting zoom to 0
    gesturePane.zoomTo(0, new Point2D(totalX / total, totalY / total));
    // Centering on the average x-y of all Nodes
    gesturePane
        .animate(Duration.millis(500))
        .centreOn(
            new Point2D(
                (totalX / total) - App.getPrimaryStage().getScene().getWidth() / 2,
                (totalY / total) - App.getPrimaryStage().getScene().getHeight() / 2));
  }
}
