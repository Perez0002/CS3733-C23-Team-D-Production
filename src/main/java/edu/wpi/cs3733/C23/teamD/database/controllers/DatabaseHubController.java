package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ConfettiController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;

public class DatabaseHubController {
  @FXML private MFXButton serviceTableButton;
  @FXML private MFXButton nodeTableButton;
  @FXML private MFXButton edgeTableButton;
  @FXML private MFXButton moveTableButton;
  @FXML private MFXButton locationTableButton;
  @FXML private MFXButton employeeTableButton;

  @FXML private MFXButton analyticsButton;
  @FXML private MFXButton downloadButton;
  @FXML private MFXButton uploadButton;
  @FXML private ServiceRequestTableController ServiceRequestTableBorderPane;
  @FXML private BorderPane requestFormHubPane;
  @FXML private Parent patientTransportVBox;
  @FXML private BorderPane mapPaneContainer;
  @FXML private BorderPane requestFormHubBorderPane;
  @Setter @Getter private DatabaseController currentController; // tracks current VBox pane
  @Setter private AddFormController addFormController;

  @Getter private MFXButton currentTab;

  BorderPane getRequestFormHubPane() {
    return requestFormHubPane;
  }

  BorderPane getMapPaneContainer() {
    return mapPaneContainer;
  }

  public void initialize() {
    currentController = ServiceRequestTableBorderPane;
    switchVBox(
        DatabasesFXML.SERVICE_REQUEST, serviceTableButton, DatabasesFXML.CHANGE_SERVICE_REQUEST);
    serviceTableButton.setOnMouseClicked(
        event ->
            switchVBox(
                DatabasesFXML.SERVICE_REQUEST,
                serviceTableButton,
                DatabasesFXML.CHANGE_SERVICE_REQUEST));
    nodeTableButton.setOnMouseClicked(
        event -> switchVBox(DatabasesFXML.NODE_TABLE, nodeTableButton, DatabasesFXML.ADD_NODE));
    edgeTableButton.setOnMouseClicked(
        event -> switchVBox(DatabasesFXML.EDGES_TABLE, edgeTableButton, DatabasesFXML.ADD_EDGE));
    moveTableButton.setOnMouseClicked(
        event -> switchVBox(DatabasesFXML.MOVE_TABLE, moveTableButton, DatabasesFXML.MOVE_REQUEST));
    locationTableButton.setOnMouseClicked(
        event ->
            switchVBox(
                DatabasesFXML.LOCATION_TABLE, locationTableButton, DatabasesFXML.ADD_LOCATION));
    employeeTableButton.setOnMouseClicked(
        event ->
            switchVBox(
                DatabasesFXML.EMPLOYEE_TABLE, employeeTableButton, DatabasesFXML.ADD_EMPLOYEE));
    analyticsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ANALYTICS_PAGE));
  }

  public void switchVBox(DatabasesFXML switchTo, MFXButton button, DatabasesFXML addPage) {
    if (currentTab != null) {
      currentTab.getStyleClass().clear();
      currentTab.getStyleClass().add("tabButton");
    }
    currentTab = button;
    currentTab.getStyleClass().clear();
    currentTab.getStyleClass().add("tabButtonSelected");
    NavigationDatabases.navigate(
        switchTo, getRequestFormHubPane(), addPage, getMapPaneContainer(), this);
  }

  @FXML
  void downloadData() throws IOException {
    FDdb.getInstance().downloadCSV();
    if (CurrentUserEnum._CURRENTUSER.getSetting().getConfetti() == 1) {
      ConfettiController.makeConfetti(1500, 50, 100);
    }
    ToastController.makeText("Your data has been downloaded!", 1000, 50, 50);
  }

  @FXML
  void uploadData() throws IOException {
    FDdb.getInstance().uploadCSV();
    if (CurrentUserEnum._CURRENTUSER.getSetting().getConfetti() == 1) {
      ConfettiController.makeConfetti(1500, 50, 100);
    }
    ToastController.makeText("Your data has been uploaded!", 1000, 50, 50);
  }

  @FXML
  public void dataToChange() {
    addFormController.dataToChange(null);
    currentController.deselect();
  }
}
