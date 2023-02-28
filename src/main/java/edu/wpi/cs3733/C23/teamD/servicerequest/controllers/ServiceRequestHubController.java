package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import static edu.wpi.cs3733.C23.teamD.servicerequest.controllers.ServiceRequests.*;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ConfettiController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;

public class ServiceRequestHubController {

  @FXML private MFXButton transportButton;
  @FXML private MFXButton sanitationButton;
  @FXML private MFXButton computerButton;
  @FXML private MFXButton securityButton;
  @FXML private MFXButton avButton;

  @FXML private Pane requestFormHubPane;

  @FXML private MFXButton clearButton;

  @FXML private MFXButton submitButton;

  @FXML private MFXButton helpButton;

  @FXML private BorderPane theContainerForMap;

  @FXML private Label requiredFieldsText;

  private ServiceRequestVBoxController currentController; // tracks current VBox pane

  private MFXButton currentTab;

  private boolean hubMapCreated = false;

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
    System.out.println("changeFloor CALL");
  }

  // DO NOT TOUCH THIS FUNCTION. JUST CALL IN INITIALIZE
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
      submission = currentController.submit();
    } else if (currentController instanceof ComputerServiceRequestController) {
      submission = currentController.submit();
    } else if (currentController instanceof SecurityServiceRequestController) {
      submission = currentController.submit();
    } else if (currentController instanceof SanitationRequestController) {
      submission = currentController.submit();
    } else if (currentController instanceof AVRequestController) {
      submission = currentController.submit();
    } else {
      submission = currentController.submit();
    }

    if (submission) {
      clearFields();
      ToastController.makeText("Your form has been submitted!", 1500, 50, 100);
      if (CurrentUserEnum._CURRENTUSER.getSetting().getConfetti() == 1) {
        ConfettiController.makeConfetti(1500, 50, 100);
      }
      requiredFieldsText.setText("* Required fields.");
      requiredFieldsText.setTextFill(Color.rgb(1, 45, 90));
    } else {
      requiredFieldsText.setText("* Please fill out the required fields.");
      requiredFieldsText.setTextFill(Color.color(1, 0, 0));
    }

    // TODO: add your submit function here in the exact same format as the PatientVBoxController
    // same as ClearFields. This function calls the controller class function for submission.

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
