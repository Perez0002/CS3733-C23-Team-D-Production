package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import static edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers.ServiceRequests.HUB;
import static edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers.ServiceRequests.PATIENT_TRANSPORT;

import edu.wpi.cs3733.C23.teamD.controllers.pathfinding.MapFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.kurobako.gesturefx.GesturePane;

public class ServiceRequestHubController {

  @FXML private MFXButton hubButton;
  @FXML private MFXButton transportButton;
  @FXML private Pane requestFormHubPane;
  @FXML private Parent patientTransportVBox;
  @FXML private PatientTransportVBoxController patientTransportVBoxController;
  @FXML private Parent hubVBox;
  @FXML private HubBoxController hubVBoxController;
  @FXML private BorderPane mapPaneContainer;
  @FXML private BorderPane requestFormHubBorderPane;

  @FXML private MFXButton submitButton;

  @FXML private MFXButton clearButton;
  private ServiceRequestVBoxController currentController; // tracks current VBox pane

  Pane getRequestFormHubPane() {
    return requestFormHubPane;
  }

  public void initialize() {

    createHubMap();

    currentController = hubVBoxController;

    hubButton.setOnMouseClicked(event -> switchVBox(HUB));
    transportButton.setOnMouseClicked(event -> switchVBox(PATIENT_TRANSPORT));
    // submitButton.setOnMouseClicked(event -> System.out.println());
    clearButton.setOnMouseClicked(event -> clearFields());
  }

  void switchVBox(ServiceRequests switchTo) {
    currentController = NavigationServiceRequests.navigate(switchTo, getRequestFormHubPane());
  }

  void clearFields() {
    if (currentController instanceof HubBoxController) {

    } else if (currentController instanceof PatientTransportVBoxController) {
      ((PatientTransportVBoxController) currentController).clearTransportForms();
    }
  }

  void createHubMap() {
    GesturePane map = MapFactory.startBuild().build(0);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);
    mapPaneContainer.setCenter(map);
  }
}
