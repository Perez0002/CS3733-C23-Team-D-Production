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
      creditsButton,
      logOutButton;

  @FXML
  public void initialize() {
    checkAccessLevel(CurrentUserEnum._CURRENTUSER.getCurrentUser());
    setButtons();
  }

  public void checkAccessLevel(Employee currentUser) {
    String type = currentUser.getEmployeeType();
    if (type != null && type.equals("ADMIN")) {
      dbButton.setDisable(false);
      moveTableButton.setDisable(false);
      mapEditorButton.setDisable(false);
      dbButton.setManaged(true);
      moveTableButton.setManaged(true);
      mapEditorButton.setManaged(true);
    } else {
      dbButton.setDisable(true);
      moveTableButton.setDisable(true);
      mapEditorButton.setDisable(true);
      dbButton.setManaged(false);
      moveTableButton.setManaged(false);
      mapEditorButton.setManaged(false);
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

    helpPageButton.setOnMouseClicked(event -> showHelp());
    helpPageButton.setTooltip(new Tooltip("Help"));

    infoButton.setOnMouseClicked(event -> showAbout());
    infoButton.setTooltip(new Tooltip("Information"));

    creditsButton.setOnMouseClicked(event -> showCredits()); /* <-- uncomment when finished */
    creditsButton.setTooltip(new Tooltip("Credits"));

    logOutButton.setOnMouseClicked(event -> openLoginPage());
    logOutButton.setTooltip(new Tooltip("Sign Out"));
  }

  private void showHelp() {
    try {
      final var resource = App.class.getResource("views/ApplicationHelpPage.fxml");
      final FXMLLoader loader = new FXMLLoader(resource);
      PopOver popover = new PopOver(loader.load());
      popover.setArrowSize(0);
      popover.setTitle("Help");
      popover.show(App.getPrimaryStage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void showAbout() {
    try {
      final var resource = App.class.getResource("views/about.fxml");
      final FXMLLoader loader = new FXMLLoader(resource);
      PopOver popover = new PopOver(loader.load());
      popover.setArrowSize(0);
      popover.setTitle("About");
      popover.show(App.getPrimaryStage());
    } catch (IOException e) {
      e.printStackTrace();
    }
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
