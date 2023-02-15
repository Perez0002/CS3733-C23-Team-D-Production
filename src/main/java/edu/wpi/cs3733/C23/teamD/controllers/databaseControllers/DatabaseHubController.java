package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import static edu.wpi.cs3733.C23.teamD.controllers.databaseControllers.DatabasesFXML.*;

import edu.wpi.cs3733.C23.teamD.controllers.ConfettiController;
import edu.wpi.cs3733.C23.teamD.controllers.ToastController;
import edu.wpi.cs3733.C23.teamD.controllers.pathfinding.MapFactory;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.kurobako.gesturefx.GesturePane;

public class DatabaseHubController {
  @FXML private MFXButton serviceTableButton;
  @FXML private MFXButton nodeTableButton;
  @FXML private MFXButton edgeTableButton;
  @FXML private MFXButton moveTableButton;
  @FXML private MFXButton locationTableButton;
  @FXML private MFXButton cancelButton;

  @FXML private MFXButton submitButton;

  @FXML private MFXButton downloadButton;

  @FXML private MFXButton uploadButton;

  @FXML private ServiceRequestTableController ServiceRequestTableBorderPane;
  @FXML private Pane requestFormHubPane;
  @FXML private Parent patientTransportVBox;
  @FXML private BorderPane mapPaneContainer;
  @FXML private BorderPane requestFormHubBorderPane;
  private DatabaseController currentController; // tracks current VBox pane

  private MFXButton currentTab;

  Pane getRequestFormHubPane() {
    return requestFormHubPane;
  }

  public void initialize() {
    createHubMap();
    currentController = ServiceRequestTableBorderPane;
    switchVBox(SERVICE_REQUEST, serviceTableButton);
    serviceTableButton.setOnMouseClicked(event -> switchVBox(SERVICE_REQUEST, serviceTableButton));
    nodeTableButton.setOnMouseClicked(event -> switchVBox(NODE_TABLE, nodeTableButton));
    edgeTableButton.setOnMouseClicked(event -> switchVBox(EDGES_TABLE, edgeTableButton));
    moveTableButton.setOnMouseClicked(event -> switchVBox(MOVE_TABLE, moveTableButton));
    locationTableButton.setOnMouseClicked(event -> switchVBox(LOCATION_TABLE, locationTableButton));
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  void switchVBox(DatabasesFXML switchTo) {
    NavigationDatabases.navigate(switchTo, getRequestFormHubPane());
  }

  @FXML
  void downloadData() throws IOException {
    FDdb.getInstance().downloadCSV();
    ToastController.makeText("Your data has been downloaded!", 1000, 50, 50);
    ConfettiController.makeConfetti(1000, 50, 50);
  }

  @FXML
  void uploadData() {
    FDdb.getInstance().uploadCSV();
    ToastController.makeText("Your data has been uploaded!", 1000, 50, 50);
  }

  void createHubMap() {
    GesturePane map = MapFactory.startBuild().build(0);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);
    mapPaneContainer.setCenter(map);
  }
}
