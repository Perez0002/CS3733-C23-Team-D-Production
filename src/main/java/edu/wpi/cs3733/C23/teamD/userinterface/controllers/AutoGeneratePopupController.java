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
  void resetChanges() {}

  @FXML
  void submitChanges() throws IOException {
    //        boolean submission = false;
    //
    //        if (currentController instanceof HubBoxController) {
    //        } else if (currentController instanceof PatientTransportVBoxController) {
    //            submission = ((PatientTransportVBoxController) currentController).submit();
    //        } else if (currentController instanceof ComputerServiceRequestController) {
    //            submission = ((ComputerServiceRequestController) currentController).submit();
    //        } else if (currentController instanceof SecurityServiceRequestController) {
    //            submission = ((SecurityServiceRequestController) currentController).submit();
    //        } else if (currentController instanceof SanitationRequestController) {
    //            submission = ((SanitationRequestController) currentController).submit();
    //        } else if (currentController instanceof AVRequestController) {
    //            submission = ((AVRequestController) currentController).submit();
    //        } else {
    //            currentController.submit();
    //        }
    //
    //        if (submission) {
    //            clearFields();
    //            ToastController.makeText("Your form has been submitted!", 1500, 50, 100, 225,
    // 740);
    //            if (CurrentUserEnum._CURRENTUSER.getSetting().getConfetti() == 1) {
    //                ConfettiController.makeConfetti(1500, 50, 100);
    //            }
    //            requiredFieldsText.setText("* Required fields.");
    //            requiredFieldsText.setTextFill(Color.rgb(1, 45, 90));
    //        } else {
    //            requiredFieldsText.setText("* Please fill out the required fields.");
    //            requiredFieldsText.setTextFill(Color.color(1, 0, 0));
    //        }
  }
}
