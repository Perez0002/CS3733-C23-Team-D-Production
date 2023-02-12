package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.LocationNameIDaoImpl;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.MoveIDaoImpl;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.NodeIDaoImpl;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
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

  @FXML private MFXTextField longNameTextField;

  @FXML private BorderPane mapEditorPane;

  @FXML private Text helpText;

  @FXML private MFXButton clearButton;

  @FXML private MFXButton addLocationButton;

  @FXML private MFXTextField xCoordTextField;

  @FXML private MFXTextField yCoordTextField;

  @FXML private MFXTextField locationTypeTextField;

  @FXML private MFXTextField shortNameTextField;

  @FXML private MFXTextField floorTextField;

  @FXML private MFXTextField buildingTextField;

  @FXML private MFXButton submitButton;
  @FXML private MFXButton addNodeButton;
  @FXML private MFXButton deleteNodeButton;

  private Node currentNodeEdit;

  private ArrayList<Node> nodeList;
  private MapDrawController mapDrawer;

  private int mode = 0;

  @FXML
  void clearFields() {
    // Clears all Text Fields
    currentNodeEdit = null;
    GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
    AnchorPane anchor = (AnchorPane) gesturePane.getContent();

    for (javafx.scene.Node n : anchor.getChildren()) {
      n.setStyle("-fx-background-color: '#013A75';"); // Setting all Panes to default color
    }
    updateButtonsForNode(0);
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
    NodeIDaoImpl nodeDao = new NodeIDaoImpl();
    if (currentNodeEdit != null) {
      nodeDao.delete(currentNodeEdit);

      GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
      AnchorPane anchor = (AnchorPane) gesturePane.getContent();

      // Find the old Pane bound to old Node
      for (javafx.scene.Node node : anchor.getChildren()) {
        if ((currentNodeEdit.getNodeID() + "_pane").equals(node.getId())) {
          anchor.getChildren().remove(node); // Remove it
          break;
        }
      }

      currentNodeEdit = null;
      clearFields();
    }
  }

  @FXML
  void addNode() {
    updateButtonsForNode(2);
  }

  /**
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
          updateButtonsForNode(1);
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
          updateButtonsForNode(0);
          clearFields();
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
    LocationNameIDaoImpl locDao = new LocationNameIDaoImpl();
    NodeIDaoImpl nodeDao = new NodeIDaoImpl();
    MoveIDaoImpl moveDao = new MoveIDaoImpl();

    if (mode == 1) {
      // Node Selected, updating
      newNode =
          new Node(
              Integer.parseInt(xCoordTextField.getText()),
              Integer.parseInt(yCoordTextField.getText()),
              currentNodeEdit.getFloor(),
              currentNodeEdit.getBuilding());
      LocationName loc =
          new LocationName(
              longNameTextField.getText(),
              currentNodeEdit.getShortName(),
              currentNodeEdit.getLocationType());
      newNode.setLocation(loc);

      nodeDao.nodeEdgeSwap(currentNodeEdit, newNode);

      if (currentNodeEdit.getLongName() != newNode.getLongName()
          || newNode.getLongName().isEmpty()) {
        System.out.println(
            currentNodeEdit.getLocation().getLongName()
                + currentNodeEdit.getLocation().getShortName()
                + currentNodeEdit.getLocation().getLocationType());
        System.out.println(loc.getLongName() + loc.getShortName() + loc.getLocationType());

        Move move = new Move(newNode, loc);

        locDao.updatePK(currentNodeEdit.getLocation(), loc);
        moveDao.save(move);
      }

    } else if (mode == 2) {
      // Add node
      newNode =
          new Node(
              Integer.parseInt(xCoordTextField.getText()),
              Integer.parseInt(yCoordTextField.getText()),
              floorTextField.getText(),
              buildingTextField.getText());

      LocationName loc = new LocationName(newNode.getNodeID() + "_personal", "", "");
      newNode.setLocation(loc);
      Move move = new Move(newNode, loc);
      locDao.save(loc);
      nodeDao.save(newNode);
      moveDao.save(move);
    } else if (mode == 3) {
      // Add Location
      LocationName loc =
          new LocationName(
              longNameTextField.getText(),
              shortNameTextField.getText(),
              locationTypeTextField.getText());
      locDao.save(loc);
      return;
    }

    // Remove current Node being edited from the list of active Nodes
    nodeList.remove(currentNodeEdit);
    // Add new Node to the list of active Nodes
    nodeList.add(newNode);

    // Get GesturePane and AnchorPane
    GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
    AnchorPane anchor = (AnchorPane) gesturePane.getContent();

    // Find the old Pane bound to old Node
    if (mode != 2) {
      for (javafx.scene.Node node : anchor.getChildren()) {
        if ((currentNodeEdit.getNodeID() + "_pane").equals(node.getId())) {
          anchor.getChildren().remove(node); // Remove it
          break;
        }
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
    shortNameTextField.setText("");
    buildingTextField.setText("");
    floorTextField.setText("");
    locationTypeTextField.setText("");

    currentNodeEdit = null; // set currentNodeEdit to null
  }

  @FXML
  public void addLocation() {
    updateButtonsForNode(3);
  }

  private void updateButtonsForNode(int level) {
    mode = level;
    if (level == 0) { // Nothing selected
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
      buildingTextField.setVisible(false);
      buildingTextField.setManaged(false);
      floorTextField.setVisible(false);
      floorTextField.setManaged(false);
      addLocationButton.setVisible(true);
      addLocationButton.setManaged(true);
      shortNameTextField.setVisible(false);
      shortNameTextField.setManaged(false);
      locationTypeTextField.setVisible(false);
      locationTypeTextField.setManaged(false);
    } else if (level == 1) { // Node selected
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
      buildingTextField.setVisible(false);
      buildingTextField.setManaged(false);
      floorTextField.setVisible(false);
      floorTextField.setManaged(false);
      addLocationButton.setVisible(false);
      addLocationButton.setManaged(false);
      shortNameTextField.setVisible(false);
      shortNameTextField.setManaged(false);
      locationTypeTextField.setVisible(false);
      locationTypeTextField.setManaged(false);
    } else if (level == 2) { // Add Node Selected
      submitButton.setVisible(true);
      submitButton.setManaged(true);
      addNodeButton.setVisible(false);
      addNodeButton.setManaged(false);
      deleteNodeButton.setVisible(false);
      deleteNodeButton.setManaged(false);
      longNameTextField.setVisible(false);
      longNameTextField.setManaged(false);
      xCoordTextField.setVisible(true);
      xCoordTextField.setManaged(true);
      yCoordTextField.setVisible(true);
      yCoordTextField.setManaged(true);
      clearButton.setVisible(true);
      clearButton.setManaged(true);
      buildingTextField.setVisible(true);
      buildingTextField.setManaged(true);
      floorTextField.setVisible(true);
      floorTextField.setManaged(true);
      addLocationButton.setVisible(false);
      addLocationButton.setManaged(false);
      shortNameTextField.setVisible(false);
      shortNameTextField.setManaged(false);
      locationTypeTextField.setVisible(false);
      locationTypeTextField.setManaged(false);
    } else { // Add Location Selected
      submitButton.setVisible(true);
      submitButton.setManaged(true);
      addNodeButton.setVisible(false);
      addNodeButton.setManaged(false);
      deleteNodeButton.setVisible(false);
      deleteNodeButton.setManaged(false);
      longNameTextField.setVisible(true);
      longNameTextField.setManaged(true);
      xCoordTextField.setVisible(false);
      xCoordTextField.setManaged(false);
      yCoordTextField.setVisible(false);
      yCoordTextField.setManaged(false);
      clearButton.setVisible(true);
      clearButton.setManaged(true);
      buildingTextField.setVisible(false);
      buildingTextField.setManaged(false);
      floorTextField.setVisible(false);
      floorTextField.setManaged(false);
      addLocationButton.setVisible(false);
      addLocationButton.setManaged(false);
      shortNameTextField.setVisible(true);
      shortNameTextField.setManaged(true);
      locationTypeTextField.setVisible(true);
      locationTypeTextField.setManaged(true);
    }
  }

  @FXML
  void displayHelp() {
    helpVisible = !helpVisible;
    helpText.setVisible(helpVisible);
  }

  @FXML
  public void initialize() {
    updateButtonsForNode(0);

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
