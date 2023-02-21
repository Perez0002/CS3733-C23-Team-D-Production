package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import static edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequests.*;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ConfettiController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.PopOver;

public class ServiceRequestHubController {

  @FXML private MFXButton hubButton;
  @FXML private MFXButton transportButton;
  @FXML private MFXButton sanitationButton;
  @FXML private MFXButton computerButton;
  @FXML private MFXButton securityButton;
  @FXML private MFXButton avButton;

  @FXML private Pane requestFormHubPane;
  @FXML private BorderPane mapPaneContainer;

  @FXML private MFXButton clearButton;

  @FXML private MFXButton submitButton;

  @FXML private MFXButton helpButton;

  @FXML private Label successfulSubmissionText;

  @FXML private MFXButton floor1Button;
  @FXML private MFXButton floor2Button;
  @FXML private MFXButton floor3Button;
  @FXML private MFXButton floor4Button;
  @FXML private MFXButton floor5Button;

  private int currentFloor = 0;

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
    switchVBox(PATIENT_TRANSPORT, transportButton);
    // TODO: set BUTTON functionality here. Add your buton. Set the onMouseClick to switchVBox(HUB,
    // hubButton);

    transportButton.setOnMouseClicked(event -> switchVBox(PATIENT_TRANSPORT, transportButton));
    computerButton.setOnMouseClicked(event -> switchVBox(COMPUTER_REQUEST, computerButton));
    sanitationButton.setOnMouseClicked(event -> switchVBox(SANITATION_REQUEST, sanitationButton));
    securityButton.setOnMouseClicked(event -> switchVBox(SECURITY_REQUEST, securityButton));
    avButton.setOnMouseClicked(event -> switchVBox(AV_REQUEST, avButton));

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

    floor1Button.setOnMouseClicked(event -> changeFloor(0));
    floor2Button.setOnMouseClicked(event -> changeFloor(1));
    floor3Button.setOnMouseClicked(event -> changeFloor(2));
    floor4Button.setOnMouseClicked(event -> changeFloor(3));
    floor5Button.setOnMouseClicked(event -> changeFloor(4));
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
    } else if (currentController instanceof ComputerServiceRequestController) {
      ((ComputerServiceRequestController) currentController).clearComputerForms();
    } else if (currentController instanceof SecurityServiceRequestController) {
      ((SecurityServiceRequestController) currentController).clearFields();
    } else {
      currentController.clearTransportForms();
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
    } else if (currentController instanceof SecurityServiceRequestController) {
      System.out.println("Submitting");
      submission = ((SecurityServiceRequestController) currentController).submit();
    } else {
      submission = currentController.submit();
      System.out.println("Submitting");
    }

    if (submission) {
      clearFields();
      ToastController.makeText("Your form has been submitted!", 1500, 50, 100, 225, 740);
      ConfettiController.makeConfetti(1500, 50, 100);
    }

    // TODO: add your submit function here in the exact same format as the PatientVBoxController
    // same as ClearFields. This function calls the controller class function for submission.

  }

  void createHubMap() {
    mapPaneContainer.setCenter(ServiceRequestMap.getMap());
  }

  public void changeFloor(int floor) {

    switch (currentFloor) {
      case 0:
        floor1Button.setStyle("-fx-background-color: #C9E0F8");
        break;
      case 1:
        floor2Button.setStyle("-fx-background-color: #C9E0F8");
        break;
      case 2:
        floor3Button.setStyle("-fx-background-color: #C9E0F8");
        break;
      case 3:
        floor4Button.setStyle("-fx-background-color: #C9E0F8");
        break;
      case 4:
        floor5Button.setStyle("-fx-background-color: #C9E0F8");
        break;
    }

    switch (floor) {
      case 0:
        floor1Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
        currentFloor = 0;
        ServiceRequestMap.getMapSingleton().setFloor(0);
        mapPaneContainer.setCenter(ServiceRequestMap.getMap());
        break;
      case 1:
        floor2Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
        currentFloor = 1;
        ServiceRequestMap.getMapSingleton().setFloor(1);
        mapPaneContainer.setCenter(ServiceRequestMap.getMap());
        break;
      case 2:
        floor3Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
        currentFloor = 2;
        ServiceRequestMap.getMapSingleton().setFloor(2);
        mapPaneContainer.setCenter(ServiceRequestMap.getMap());
        break;
      case 3:
        floor4Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
        currentFloor = 3;
        ServiceRequestMap.getMapSingleton().setFloor(3);
        mapPaneContainer.setCenter(ServiceRequestMap.getMap());
        break;
      case 4:
        floor5Button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #012D5A");
        currentFloor = 4;
        ServiceRequestMap.getMapSingleton().setFloor(4);
        mapPaneContainer.setCenter(ServiceRequestMap.getMap());
        break;
    }

    /* TODO: what this does currently is just handles the button color changes.
    What do I need to do now? I need to handle the map changes in the actual ServiceRequestMap class.
     */

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
