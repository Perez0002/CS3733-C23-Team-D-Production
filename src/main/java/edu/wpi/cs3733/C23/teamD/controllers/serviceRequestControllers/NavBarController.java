package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;

public class NavBarController {

  @FXML private MFXButton dbButton;

  @FXML private MFXButton helpPageButton;

  @FXML private MFXButton homeButton;

  @FXML private MFXButton infoButton;

  @FXML private MFXButton logOutButton;

  @FXML private MFXButton mapEditorButton;

  @FXML private MFXButton profileButton;

  @FXML private MFXButton serviceRequestFormsButton;

  @FXML
  public void initialize() {
    homeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    homeButton.setTooltip(new Tooltip("Home"));

    profileButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    profileButton.setTooltip(new Tooltip("Profile Page"));

    serviceRequestFormsButton.setOnMouseClicked(
        event -> Navigation.navigate(Screen.REQUEST_FORM_HUB));
    serviceRequestFormsButton.setTooltip(new Tooltip("Service Request Forms"));

    dbButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_EDITOR));
    dbButton.setTooltip(new Tooltip("Database Editors"));

    mapEditorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_EDITOR));
    mapEditorButton.setTooltip(new Tooltip("Map Editor"));

    helpPageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HELP_PAGE));
    helpPageButton.setTooltip(new Tooltip("Help"));

    infoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    infoButton.setTooltip(new Tooltip("Information"));

    logOutButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOGIN_PAGE));
    logOutButton.setTooltip(new Tooltip("Sign Out"));
  }

  @FXML
  void openDBHub(ActionEvent event) {
    Navigation.navigate(Screen.DATABASE_EDITOR);
  }

  @FXML
  void openHelpPage(ActionEvent event) {
    Navigation.navigate(Screen.HELP_PAGE);
  }

  @FXML
  void openHomepage(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void openInfoPage(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void openLoginPage(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN_PAGE);
  }

  @FXML
  void openMapEditor(ActionEvent event) {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  @FXML
  void openProfilePage(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void openServiceRequestFormHub(ActionEvent event) {
    Navigation.navigate(Screen.REQUEST_FORM_HUB);
  }
}
