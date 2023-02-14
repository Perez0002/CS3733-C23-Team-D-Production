package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import edu.wpi.cs3733.C23.teamD.controllers.pathfinding.MapFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.kurobako.gesturefx.GesturePane;

import static edu.wpi.cs3733.C23.teamD.controllers.databaseControllers.DatabasesFXML.*;

public class DatabaseHubController {

  @FXML private MFXButton hubButton;
  @FXML private MFXButton serviceTableButton;
  @FXML private MFXButton nodeTableButton;
  @FXML private MFXButton edgeTableButton;
  @FXML private MFXButton moveTableButton;
  @FXML private MFXButton locationTableButton;
  @FXML private Pane requestFormHubPane;
  @FXML private Parent patientTransportVBox;
  @FXML private ServiceRequestTableController serviceRequestTable;
  @FXML private Parent hubVBox;
  @FXML private HubBoxController hubVBoxController;
  @FXML private BorderPane mapPaneContainer;
  @FXML private BorderPane requestFormHubBorderPane;
  private DatabaseVBoxController currentController; // tracks current VBox pane

  Pane getRequestFormHubPane() {
    return requestFormHubPane;
  }

  public void initialize() {

    createHubMap();

    currentController = hubVBoxController;

    hubButton.setOnMouseClicked(event -> switchVBox(HUB));
    serviceTableButton.setOnMouseClicked(event -> switchVBox(SERVICE_REQUEST));
    nodeTableButton.setOnMouseClicked(event -> switchVBox(NODE_TABLE));
    edgeTableButton.setOnMouseClicked(event -> switchVBox(EDGES_TABLE));
    moveTableButton.setOnMouseClicked(event -> switchVBox(MOVE_TABLE));
  }

  void switchVBox(DatabasesFXML switchTo) {
    NavigationDatabases.navigate(switchTo, getRequestFormHubPane());
  }

  void createHubMap() {
    GesturePane map = MapFactory.startBuild().build(0);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);
    mapPaneContainer.setCenter(map);
  }
}
