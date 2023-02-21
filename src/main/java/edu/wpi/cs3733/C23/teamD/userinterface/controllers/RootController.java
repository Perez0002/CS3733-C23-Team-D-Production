package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tooltip;
import org.controlsfx.control.PopOver;

public class RootController {

  @FXML
  private MFXButton homeButton,
      profileButton,
      serviceRequestFormsButton,
      pathfindingButton,
      dbButton,
      moveTableButton,
      mapEditorButton,
      helpPageButton,
      infoButton,
      logOutButton;

  @FXML
  public void initialize() {
    checkAccessLevel(CurrentUserEnum._CURRENTUSER.getCurrentUser());
    setButtons();
  }

  public void checkAccessLevel(Employee currentUser) {
    String type = currentUser.getEmployeeType();
    if (type != null && (type.equals("ADMIN") || type.equals("STAFF"))) {
      dbButton.setDisable(false);
      serviceRequestFormsButton.setDisable(false);
      mapEditorButton.setDisable(false);
    } else {
      dbButton.setDisable(true);
      serviceRequestFormsButton.setDisable(true);
      mapEditorButton.setDisable(true);
    }
  }

  @FXML
  public void openLoginPage() {
    App.getRootPane().setLeft(null);
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

    dbButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_HUB));
    dbButton.setTooltip(new Tooltip("Database Editors"));

    moveTableButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MOVES_TABLE));
    moveTableButton.setTooltip(new Tooltip("Office Moves"));

    mapEditorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_EDITOR));
    mapEditorButton.setTooltip(new Tooltip("Map Editor"));

    helpPageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HELP_PAGE));
    helpPageButton.setTooltip(new Tooltip("Help"));

    infoButton.setTooltip(new Tooltip("Information"));
    infoButton.setOnMouseClicked(event -> showCredits());

    logOutButton.setOnMouseClicked(event -> openLoginPage());
    logOutButton.setTooltip(new Tooltip("Sign Out"));
  }

  void showCredits() {
    try {
      final var resource = App.class.getResource("views/credits.fxml");
      final FXMLLoader loader = new FXMLLoader(resource);
      PopOver popover = new PopOver(loader.load());
      popover.setArrowSize(0);
      popover.setTitle("Credits");
      popover.show(App.getPrimaryStage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
