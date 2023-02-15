package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import static edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers.ServiceRequests.*;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.controllers.ConfettiController;
import edu.wpi.cs3733.C23.teamD.controllers.ToastController;
import edu.wpi.cs3733.C23.teamD.controllers.pathfinding.MapFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.PopOver;

public class ServiceRequestHubController {

  @FXML private MFXButton hubButton;
  @FXML private MFXButton transportButton;
  @FXML private MFXButton sanitationButton;
  @FXML private MFXButton computerButton;

  @FXML private Pane requestFormHubPane;
  @FXML private BorderPane mapPaneContainer;

  @FXML private MFXButton clearButton;

  @FXML private MFXButton submitButton;

  @FXML private MFXButton helpButton;

  @FXML private Label successfulSubmissionText;
  private ServiceRequestVBoxController currentController; // tracks current VBox pane

  private MFXButton currentTab;

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
    switchVBox(HUB, hubButton);
    // TODO: set BUTTON functionality here. Add your buton. Set the onMouseClick to switchVBox(HUB,
    // hubButton);

    hubButton.setOnMouseClicked(event -> switchVBox(HUB, hubButton));
    transportButton.setOnMouseClicked(event -> switchVBox(PATIENT_TRANSPORT, transportButton));
    computerButton.setOnMouseClicked(event -> switchVBox(COMPUTER_REQUEST, computerButton));
    sanitationButton.setOnMouseClicked(event -> switchVBox(SANITATION_REQUEST, sanitationButton));

    // TODO: set BUTTON functionality here. Add your button. Set the onMouseClick to
    // switchVBox(YOUR_REQUEST)
    // you need to add your vbox fxml file to the ENUM ServiceRequests

    helpButton.setOnMouseClicked(
        event -> {
          try {
            help();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    submitButton.setOnMouseClicked(
        event -> {
          try {
            submit();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    clearButton.setOnMouseClicked(event -> clearFields());
  }

  // DO NOT TOUCH THIS FUNCTION. JUST CALL IN INITIALZE.
  void switchVBox(ServiceRequests switchTo, MFXButton button) {

    if (currentTab != null) {
      currentTab.getStyleClass().clear();
      currentTab.getStyleClass().add("tabButton");
    }
    currentTab = button;
    currentTab.getStyleClass().clear();
    currentTab.getStyleClass().add("tabButtonSelected");
    currentController = NavigationServiceRequests.navigate(switchTo, getRequestFormHubPane());
  }

  void clearFields() {
    if (currentController instanceof HubBoxController) {

    } else if (currentController instanceof PatientTransportVBoxController) {
      ((PatientTransportVBoxController) currentController).clearTransportForms();
    } else if (currentController instanceof SanitationRequestController) {
      ((SanitationRequestController) currentController).clearFields();
    } else if (currentController instanceof ComputerServiceRequestController) {
      ((ComputerServiceRequestController) currentController).clearComputerForms();
    }

    // TODO: add your ClearFields here. Follow the exact same format as the
    // PatientTransportVBoxController but with your variables
    // What should your ClearFields do? It should clear ALL the fields in YOUR form. Do this in your
    // own controller class.
    // This function SIMPLY calls that controller class function.

  }

  void submit() throws IOException {
    System.out.println("Submit Pressed");
    boolean submission = false;
    if (currentController instanceof PatientTransportVBoxController) {
      submission = ((PatientTransportVBoxController) currentController).submit();

    } else if (currentController instanceof ComputerServiceRequestController) {
      System.out.println("Submitting");
      submission = ((ComputerServiceRequestController) currentController).submit();
    }

    if (submission) {
      clearFields();
      ToastController.makeText("Your form has been submitted!", 1500, 50, 100);
      ConfettiController.makeConfetti(1500, 50, 100);
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

  void help() throws IOException {
    final var resource = App.class.getResource("views/VBoxInjections/ServiceRequestHubHelp.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    PopOver popover = new PopOver(loader.load());
    popover.setArrowSize(0);
    popover.setTitle("Help");
    popover.show(App.getPrimaryStage());
  }
}
