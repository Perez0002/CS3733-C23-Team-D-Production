package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import lombok.Setter;
import org.controlsfx.control.PopOver;

public class AddServiceRequestsController extends PopOver {

  @Setter private MoveRequestTableController moveRequestTableController;

  @Setter private Move move;

  @FXML
  private void no() {
    moveRequestTableController.closePopOver();
  }

  @FXML
  private void yes() {
    moveRequestTableController.closePopOver();
    try {
      moveRequestTableController.generateAutomaticServiceRequestPopup(move);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void generateRequestsPopup() throws IOException {
    final var resource = App.class.getResource("views/AutoGeneratePopup.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    PopOver popover = new PopOver(loader.load());
    popover.setArrowSize(0);
    popover.setCornerRadius(32);
    popover.setTitle("Generated Service Request Editor");
    popover.show(App.getPrimaryStage());
  }
}
