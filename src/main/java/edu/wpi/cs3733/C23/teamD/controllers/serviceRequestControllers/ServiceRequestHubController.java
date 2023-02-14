package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import static edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers.ServiceRequests.*;

import edu.wpi.cs3733.C23.teamD.controllers.pathfinding.MapFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import net.kurobako.gesturefx.GesturePane;

public class ServiceRequestHubController<DrawAreaController> {

  @FXML private MFXButton hubButton;
  @FXML private MFXButton transportButton;
  @FXML private MFXButton avButton;
  @FXML private Pane requestFormHubPane;
  @FXML private Parent patientTransportVBox;
  @FXML private PatientTransportVBoxController patientTransportVBoxController;
  @FXML private Parent hubVBox;
  @FXML private HubBoxController hubVBoxController;
  @FXML private BorderPane mapPaneContainer;
  @FXML private BorderPane requestFormHubBorderPane;
  private ServiceRequestController serviceRequestController;
  private ServiceRequestVBoxController currentController; // tracks current VBox pane

  Pane getRequestFormHubPane() {
    return requestFormHubPane;
  }

  public void initialize() {

    createHubMap();

    currentController = hubVBoxController;

    hubButton.setOnMouseClicked(
        event -> {
          try {
            switchVBox(HUB);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    transportButton.setOnMouseClicked(
        event -> {
          try {
            switchVBox(PATIENT_TRANSPORT);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  @FXML
  public void openAVRequestForm() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(
            getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/AVRequestForm.fxml"));
    VBox box = loader.load();
    requestFormHubPane.getChildren().clear();
    requestFormHubPane.getChildren().add(box);
    serviceRequestController = loader.getController();
  }

  //  void switchVBox(ServiceRequestVBoxController switchTo) {
  //    currentController.setVisible();
  //    switchTo.setVisible();
  //    currentController = switchTo;
  //  }

  void switchVBox(ServiceRequests switchTo) throws IOException {

    NavigationServiceRequests.navigate(switchTo, getRequestFormHubPane());
  }

  @FXML
  public void clearFields() {
    serviceRequestController.clearFields();
  }

  void createHubMap() {
    GesturePane map = MapFactory.startBuild().build(0);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);
    mapPaneContainer.setCenter(map);
  }
}
