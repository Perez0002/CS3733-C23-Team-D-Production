package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import static edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers.ServiceRequests.*;

import edu.wpi.cs3733.C23.teamD.controllers.pathfinding.MapFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.kurobako.gesturefx.GesturePane;

public class ServiceRequestHubController {

  @FXML private MFXButton hubButton;
  @FXML private MFXButton transportButton;
  @FXML private MFXButton sanitationButton;
  @FXML private Pane requestFormHubPane;
  @FXML private BorderPane mapPaneContainer;

  @FXML private MFXButton clearButton;

  @FXML private MFXButton submitButton;
  private ServiceRequestVBoxController currentController; // tracks current VBox pane

  Pane getRequestFormHubPane() {
    return requestFormHubPane;
  }

  // TODO: if you have a service request form, you need a submit and clearfields button.
  // YOUR service request form controller needs to implement ServiceRequestVBoxController
  // ie. It should look like this:
  // public class PatientTransportVBoxController implements ServiceRequestVBoxController
  // when you declare the class
  // If you are still confused, check the PatientTransportVBoxController class for an example

  public void initialize() {

    createHubMap();

    hubButton.setOnMouseClicked(event -> switchVBox(HUB));
    transportButton.setOnMouseClicked(event -> switchVBox(PATIENT_TRANSPORT));
    sanitationButton.setOnMouseClicked(event -> switchVBox(SANITATION));

    // TODO: set BUTTON functionality here. Add your buton. Set the onMouseClick to
    // switchVBox(YOUR_REQUEST)
    // you need to add your vbox fxml file to the ENUM ServiceRequests

    submitButton.setOnMouseClicked(event -> submit());
    clearButton.setOnMouseClicked(event -> clearFields());
  }

  // DO NOT TOUCH THIS FUNCTION. JUST CALL IN INITIALZE.
  void switchVBox(ServiceRequests switchTo) {
    currentController = NavigationServiceRequests.navigate(switchTo, getRequestFormHubPane());
  }

  void clearFields() {
    if (currentController instanceof HubBoxController) {

    } else if (currentController instanceof PatientTransportVBoxController) {
      ((PatientTransportVBoxController) currentController).clearTransportForms();
    } else if (currentController instanceof SanitationRequestController) {
      ((SanitationRequestController) currentController).clearFields();
    }

    // TODO: add your ClearFields here. Follow the exact same format as the
    // PatientTransportVBoxController but with your variables
    // What should your ClearFields do? It should clear ALL the fields in YOUR form. Do this in your
    // own controller class.
    // This function SIMPLY calls that controller class function.

  }

  void submit() {
    if (currentController instanceof PatientTransportVBoxController) {
      ((PatientTransportVBoxController) currentController).submit();
    } else if (currentController instanceof SanitationRequestController) {
      ((SanitationRequestController) currentController).submitSanitationRequest();
    }

    // TODO: add your submit function here in the exact same format as the PatientVBoxController
    // same as ClearFields. This function calls the controller class function for submission.

  }

  void createHubMap() {
    GesturePane map = MapFactory.startBuild().build(0);
    map.setStyle("-fx-border-color: #012D5A;");
    map.setMaxSize(700, 500);
    mapPaneContainer.setCenter(map);
  }
}
