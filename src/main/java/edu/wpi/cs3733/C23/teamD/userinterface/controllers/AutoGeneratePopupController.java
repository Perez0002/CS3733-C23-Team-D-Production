package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.servicerequest.controllers.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.Setter;

public class AutoGeneratePopupController {

  @FXML private Pane pane;

  @Setter private Move move;

  @Setter private MoveRequestTableController moveRequestTableController;
  private ServiceRequestVBoxController currentController;

  public void initialize() {
    switchVBox(ServiceRequests.SANITATION_REQUEST);
  }

  public void fillFields() {
    if (currentController.getClass() == SanitationRequestController.class) {
      SanitationRequestController sanitationRequestController =
          (SanitationRequestController) currentController;
      sanitationRequestController.fillFields(move);
    }
  }

  public Pane getPane() {
    return pane;
  }

  void switchVBox(ServiceRequests switchTo) {
    currentController = NavigationServiceRequests.navigate(switchTo, getPane());
  }

  @FXML
  void resetChanges() {
    moveRequestTableController.closePopOver();
  }

  @FXML
  void submitChanges() throws IOException {
    SanitationRequestController sanitationRequestController =
        (SanitationRequestController) currentController;
    sanitationRequestController.autoSubmit(move.getMoveDate());
    moveRequestTableController.closePopOver();
  }
}
