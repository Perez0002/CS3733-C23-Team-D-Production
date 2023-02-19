package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.StatusComboBoxController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import lombok.Setter;

public class ChangeServiceRequestController implements AddFormController<ServiceRequest> {

  @Setter DatabaseController databaseController;
  @FXML private MFXButton submitButton;
  @FXML private MFXTextField reasonTextField;
  @FXML private Parent roomBox;
  private RoomPickComboBoxController roomBoxController;
  @FXML private Parent statusBox;

  private StatusComboBoxController statusBoxController;

  @FXML
  public void submit() {}

  @FXML
  public void clearFields() {}

  @Override
  public void dataToChange(ServiceRequest serviceRequest) {}
}
