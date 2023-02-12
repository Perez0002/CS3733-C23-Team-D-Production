package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.controllers.pathfinding.MapFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.kurobako.gesturefx.GesturePane;

public class ServiceRequestHubController {

  @FXML private MFXButton clickableTest;

  @FXML private Pane requestFormHubPane;

  @FXML private Parent hubVBox;

  @FXML private HubBoxController hubVBoxController;

  @FXML private BorderPane mapPaneContainer;

  @FXML private BorderPane requestFormHubBorderPane;

  public void initialize() {

    createHubMap();

    clickableTest.setOnMouseClicked(event -> hubVBoxController.setVisible());
  }

  void createHubMap() {
    GesturePane map = MapFactory.startBuild().build(0);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);
    mapPaneContainer.setCenter(map);
  }
}
