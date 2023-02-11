package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.LocationNameDaoImpl;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.MoveDaoImpl;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.NodeDaoImpl;
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

  private UIManager uiManager = new UIManager();
  private SubmitMode mode = SubmitMode.NO_SELECTION;

  private enum SubmitMode {
    NO_SELECTION,
    EDIT_NODE,
    ADD_NODE,
    ADD_LOCATION
  }

  @FXML
  void clearFields() {
    // Clears all Text Fields
    currentNodeEdit = null;

    GesturePane gesturePane = (GesturePane) mapEditorPane.getCenter();
    AnchorPane anchor = (AnchorPane) gesturePane.getContent();

    for (javafx.scene.Node n : anchor.getChildren()) {
      n.setStyle("-fx-background-color: '#013A75';"); // Setting all Panes to default color
    }

    updateButtonsForNode(SubmitMode.NO_SELECTION);
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
    NodeDaoImpl nodeDao = new NodeDaoImpl();
    if (currentNodeEdit != null) {
      nodeDao.delete(currentNodeEdit);

      GesturePane gesturePane = (GesturePane) this.mapEditorPane.getCenter();
      AnchorPane anchor = (AnchorPane) gesturePane.getContent();

      // Find the old Pane bound to old Node
      for (javafx.scene.Node node : anchor.getChildren()) {
        if ((this.currentNodeEdit.getNodeID() + "_pane").equals(node.getId())) {
          anchor.getChildren().remove(node); // Remove it
          break;
        }
      }

      this.currentNodeEdit = null;
      this.clearFields();
    }
  }

  @FXML
  void addNode() {
    updateButtonsForNode(SubmitMode.ADD_NODE);
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
          updateButtonsForNode(SubmitMode.EDIT_NODE);
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
          updateButtonsForNode(SubmitMode.NO_SELECTION);
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
    LocationNameDaoImpl locDao = new LocationNameDaoImpl();
    NodeDaoImpl nodeDao = new NodeDaoImpl();
    MoveDaoImpl moveDao = new MoveDaoImpl();

    if (mode == SubmitMode.EDIT_NODE) {
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

    } else if (mode == SubmitMode.ADD_NODE) {
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
    } else if (mode == SubmitMode.ADD_LOCATION) {
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
    if (mode != SubmitMode.ADD_NODE) {
      for (javafx.scene.Node node : anchor.getChildren()) {
        if ((currentNodeEdit.getNodeID() + "_pane").equals(node.getId())) {
          anchor.getChildren().remove(node); // Remove it
          break;
        }
      }
    }

    // Add a new Pane for the new Node
    final Pane tempPane =
        MapPaneFactory.startBuild()
            .posX(newNode.getXcoord() - MapFactory.NODE_WIDTH / 2)
            .posY(newNode.getYcoord() - MapFactory.NODE_HEIGHT / 2)
            .onClick(paneFunction(newNode))
            .paneID(newNode.getNodeID() + "_pane")
            .build();

    anchor.getChildren().add(tempPane);

    // Reset Fields
    this.clearFields();

    currentNodeEdit = null; // set currentNodeEdit to null
  }

  @FXML
  public void addLocation() {
    updateButtonsForNode(SubmitMode.ADD_LOCATION);
  }

  private void updateButtonsForNode(SubmitMode level) {
    mode = level;
    if (level == SubmitMode.NO_SELECTION) { // Nothing selected
      uiManager.enableOnly(addNodeButton, addLocationButton);
    } else if (level == SubmitMode.EDIT_NODE) { // Node selected
      uiManager.enableOnly(
          submitButton,
          deleteNodeButton,
          longNameTextField,
          xCoordTextField,
          yCoordTextField,
          clearButton);
    } else if (level == SubmitMode.ADD_NODE) { // Add Node Selected
      uiManager.enableOnly(
          submitButton,
          xCoordTextField,
          yCoordTextField,
          clearButton,
          buildingTextField,
          floorTextField);
    } else { // Add Location Selected
      uiManager.enableOnly(
          submitButton, longNameTextField, clearButton, shortNameTextField, locationTypeTextField);
    }
  }

  @FXML
  void displayHelp() {
    helpVisible = !helpVisible;
    helpText.setVisible(helpVisible);
  }

  @FXML
  public void initialize() {

    uiManager.setVariableComponents(
        submitButton,
        addNodeButton,
        deleteNodeButton,
        longNameTextField,
        xCoordTextField,
        yCoordTextField,
        clearButton,
        buildingTextField,
        floorTextField,
        addLocationButton,
        shortNameTextField,
        locationTypeTextField);

    updateButtonsForNode(SubmitMode.NO_SELECTION);

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
        MapFactory.startBuild()
            .withNodes(nodeList)
            .withNodeFunctions(
                node -> {
                  return paneFunction(node);
                })
            .withoutEdges()
            .build();
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
