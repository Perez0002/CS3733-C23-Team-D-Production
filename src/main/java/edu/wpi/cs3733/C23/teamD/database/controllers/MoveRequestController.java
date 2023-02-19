package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.LocationComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.NodeComboBoxController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.ZoneId;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Setter;

public class MoveRequestController implements AddFormController<Move> {
  @FXML private MFXDatePicker datePicker;
  @FXML private Parent nodeBox;
  @FXML private NodeComboBoxController nodeBoxController;
  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;
  @FXML private Text errorText;
  @FXML private MFXTextField messageTextField;
  @Setter private DatabaseController databaseController;
  @FXML private MFXButton submitButton;
  @FXML private Label titleLabel;

  private Move currentMove;

  @FXML
  public void addMove() {
    if (checkFields()) {
      if (currentMove == null) {
        errorText.setVisible(false);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());

        Move move =
            new Move(nodeBoxController.getNode(), locationBoxController.getLocation(), date);
        FDdb.getInstance().saveMove(move);
        databaseController.refresh();
      } else {
        currentMove.setLocation(locationBoxController.getLocation());
        currentMove.setMoveDate(
            Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        currentMove.setNode(nodeBoxController.getNode());
        FDdb.getInstance().updateMove(currentMove);
        databaseController.refresh();
      }
    } else {
      errorText.setVisible(true);
    }
  }

  private boolean checkFields() {
    return !(datePicker.getValue() == null
        || nodeBoxController.getNodeID().isEmpty()
        || locationBoxController.getLocationLongName().isEmpty());
  }

  @FXML
  public void clearFields() {
    datePicker.setValue(null);
    locationBoxController.clearForm();
    nodeBoxController.clearForm();
    messageTextField.clear();
  }

  @Override
  public void dataToChange(Move move) {
    currentMove = move;
    if (move == null) {
      submitButton.setText("Add Move");
      titleLabel.setText("Add a Move");
      clearFields();
    } else {
      submitButton.setText("Submit Changes");
      titleLabel.setText("Change a Move");
      locationBoxController.setLocationName(move.getLongName());
      datePicker.setValue(
          move.getMoveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      nodeBoxController.setNodeID(move.getNodeID());
    }
  }
}
