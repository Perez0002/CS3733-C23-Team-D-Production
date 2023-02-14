package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUser;
import edu.wpi.cs3733.C23.teamD.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;

public class RootController {

  @FXML private MFXButton dbButton;

  @FXML private MFXButton helpPageButton;

  @FXML private MFXButton homeButton;

  @FXML private MFXButton infoButton;

  @FXML private MFXButton logOutButton;

  @FXML private MFXButton mapEditorButton;

  @FXML private MFXButton pathfindingButton;

  @FXML private MFXButton profileButton;

  @FXML private MFXButton serviceRequestFormsButton;

  @FXML
  public void initialize() throws IOException {
    checkAccessLevel();
    setButtons();
  }

  public void checkAccessLevel() {

    CurrentUser currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();

    if (currentUser.getAccessLevel() == 2) {
      dbButton.setDisable(false);
      serviceRequestFormsButton.setDisable(false);
    } else if (currentUser.getAccessLevel() == 1) {
      dbButton.setDisable(true);
      serviceRequestFormsButton.setDisable(false);
    } else {
      dbButton.setDisable(true);
      serviceRequestFormsButton.setDisable(true);
    }
  }

  @FXML
  public void openLoginPage() throws IOException {
    App.getRootPane().setTop(null);
    Navigation.navigate(Screen.LOGIN_PAGE);
  }

  @FXML
  public void setButtons() {
    homeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    homeButton.setTooltip(new Tooltip("Home"));

    profileButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PROFILE_PAGE));
    profileButton.setTooltip(new Tooltip("Profile Page"));

    serviceRequestFormsButton.setOnMouseClicked(
        event -> Navigation.navigate(Screen.REQUEST_FORM_HUB));
    serviceRequestFormsButton.setTooltip(new Tooltip("Service Request Forms"));

    pathfindingButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING_REQUEST));
    pathfindingButton.setTooltip(new Tooltip("Get Directions"));

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
}
