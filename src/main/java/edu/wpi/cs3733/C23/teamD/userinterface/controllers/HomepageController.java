package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.PopOver;

public class HomepageController {

  @FXML private BorderPane homepageBorderPane;

  @FXML private MFXButton profileButton;
  @FXML private MFXButton helpButton;

  @FXML
  public void initialize() {
    checkAccessLevel(CurrentUserEnum._CURRENTUSER.getCurrentUser());
    profileButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PROFILE_PAGE));
    helpButton.setOnMouseClicked(
        event -> {
          try {
            help();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();

    //    if (currentUser.getAccessLevel() == 0) {
    //      currentUserText.setText("please log in");
    //    } else {
    //      currentUserText.setText("You are logged in as: \n" + currentUser.getUsername());
    //    }

  }

  private void help() throws IOException {
    final var resource = App.class.getResource("views/VBoxInjections/ServiceRequestHubHelp.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    PopOver popover = new PopOver(loader.load());
    popover.setArrowSize(0);
    popover.setTitle("Help");
    popover.show(App.getPrimaryStage());
  }

  private void checkAccessLevel(Employee currentUser) {
    //    if (currentUser.getEmployeeType().equals("ADMIN")) {
    //      serviceRequestFormButton.setDisable(false);
    //      mapEditorButton.setDisable(false);
    //      DBEditorButton.setDisable(false);
    //    } else if (currentUser.equals("STAFF")) {
    //      serviceRequestFormButton.setDisable(false);
    //      mapEditorButton.setDisable(false);
    //      DBEditorButton.setDisable(true);
    //    } else {
    //      serviceRequestFormButton.setDisable(true);
    //      mapEditorButton.setDisable(true);
    //      DBEditorButton.setDisable(true);
    //    }
  }

  @FXML
  /** user open menubutton, clicks Exit, and it closes the window. */
  void closeApplication(ActionEvent event) {
    homepageBorderPane.getScene().getWindow().hide();
  }

  @FXML
  /**
   * checks label text. Sets label text to help text when button clicked, and sets label text to
   * empty when button clicked again
   */
  void toggleHelpText(ActionEvent event) {
    //    if (serviceRequestHelpText.getText().equals("") || bottomHelpText.getText().equals("")) {
    //      serviceRequestHelpText.setText("Click the buttons below to fill out service request
    // forms!");
    //      bottomHelpText.setText(
    //          "<-Use the leftmost button to exit the program  \n  Click the rightmost button to
    // remove the help text->");
    //    } else {
    //      serviceRequestHelpText.setText("");
    //      bottomHelpText.setText("");
    //    }
  }
}
