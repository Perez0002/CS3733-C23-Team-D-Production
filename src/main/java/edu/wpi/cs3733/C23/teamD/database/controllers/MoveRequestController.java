package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.RoomPickComboBoxController;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class MoveRequestController {
  @FXML private MFXDatePicker datePicker;
  @FXML private Parent nodeBox;
  @FXML private RoomPickComboBoxController nodeBoxController;
  @FXML private Parent locationBox;
  @FXML private RoomPickComboBoxController locationBoxController;
  @FXML private Text errorText;
  @FXML private MFXTextField messageTextField;
  @Getter @Setter private DatabaseHubController databaseHubController;

  @FXML
  public void addMove() {
    if (checkFields()) {
      errorText.setVisible(false);
      ZoneId defaultZoneId = ZoneId.systemDefault();
      Date date = Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());
      ArrayList<Move> moveList = FDdb.getInstance().getAllMoves();
      LocationName locationName = null;
      for (Move m : moveList) {
        if (m.getLocation().getLongName() == locationBoxController.getLocationName()) {
          locationName = m.getLocation();
          break;
        }
      }
      Move move =
          new Move(
              FDdb.getInstance().getNode(nodeBoxController.getNodeValue()), locationName, date);
      FDdb.getInstance().saveMove(move);
      databaseHubController.refresh();
    } else {
      errorText.setVisible(true);
    }
  }

  private boolean checkFields() {
    return !(datePicker.getValue() == null
        || nodeBoxController.getNodeValue().isEmpty()
        || locationBoxController.getLocationName().isEmpty()
        || messageTextField.getText().isEmpty());
  }

  @FXML
  public void clearFields() {
    datePicker.clear();
    locationBoxController.clearForm();
    nodeBoxController.clearForm();
    messageTextField.clear();
  }
}
