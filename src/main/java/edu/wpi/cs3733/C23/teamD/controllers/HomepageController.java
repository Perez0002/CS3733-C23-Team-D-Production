package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class HomepageController {

  @FXML private MFXButton DBEditorButton;

  @FXML private Label aboutLabel;

  @FXML private Label bottomHelpText;

  @FXML private HBox bottomVbox;

  @FXML private MenuItem exitButton;

  @FXML private MenuButton exitButtonMenu;

  @FXML private MFXButton mapEditorButton;

  @FXML private BorderPane homepageBorderPane;

  @FXML private MFXButton homepageHelpButton;

  @FXML private MFXButton serviceRequestFormButton;

  @FXML private Label serviceRequestHelpText;

  @FXML private Label titleLabel;

  @FXML
  public void initialize() {
    serviceRequestFormButton.setOnMouseClicked(
        event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    DBEditorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_EDITOR));
    mapEditorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_EDITOR));
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
