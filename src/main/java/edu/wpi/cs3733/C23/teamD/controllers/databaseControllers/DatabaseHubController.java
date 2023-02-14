package edu.wpi.cs3733.C23.teamD.controllers.databaseControllers;

import static edu.wpi.cs3733.C23.teamD.controllers.databaseControllers.DatabasesFXML.HUB;
import static edu.wpi.cs3733.C23.teamD.controllers.databaseControllers.DatabasesFXML.SERVICE_REQUEST;

import edu.wpi.cs3733.C23.teamD.controllers.pathfinding.MapFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.kurobako.gesturefx.GesturePane;

public class DatabaseHubController {

  @FXML private MFXButton hubButton;
  @FXML private MFXButton serviceTableButton;
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
  }

  //  void switchVBox(ServiceRequestVBoxController switchTo) {
  //    currentController.setVisible();
  //    switchTo.setVisible();
  //    currentController = switchTo;
  //  }

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
