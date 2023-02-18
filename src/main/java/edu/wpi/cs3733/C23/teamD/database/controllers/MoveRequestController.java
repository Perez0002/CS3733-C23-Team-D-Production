package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.RoomPickComboBoxController;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class MoveRequestController {
  @FXML private MFXDatePicker datePicker;
  @FXML private Parent nodeBox;
  @FXML private RoomPickComboBoxController nodeBoxController;
  @FXML private Parent locationBox;
  @FXML private RoomPickComboBoxController locationBoxController;
  @FXML private Text errorText;
  @FXML private MFXTextField messageTextField;

  @FXML
  public void addMove() {}

  @FXML
  public void clearFields() {}
}
