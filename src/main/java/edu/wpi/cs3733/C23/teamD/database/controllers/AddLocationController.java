package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Setter;

public class AddLocationController implements AddFormController<LocationName> {
  @Setter DatabaseController databaseController;

  private LocationName currentLocation;
  @FXML private Text errorText;

  @FXML private MFXTextField longNameTextField;

  @FXML private MFXTextField nodeTypeTextField;

  @FXML private MFXTextField shortNameTextField;

  @FXML private MFXButton submitButton;

  @FXML private Label titleLabel;

  @FXML
  void clearFields() {}

  @FXML
  void submit() {
    if (currentLocation == null) {
      errorText.setVisible(false);
      FDdb.getInstance()
          .saveLocationName(
              new LocationName(
                  longNameTextField.getText(),
                  shortNameTextField.getText(),
                  nodeTypeTextField.getText()));
      databaseController.refresh();
    } else {
      currentLocation.setLocationType(nodeTypeTextField.getText());
      currentLocation.setLongName(longNameTextField.getText());
      currentLocation.setShortName(shortNameTextField.getText());

      databaseController.refresh();
    }
  }

  @Override
  public void dataToChange(LocationName locationName) {
    currentLocation = locationName;
    if (locationName == null) {
      submitButton.setText("Add Location");
      titleLabel.setText("Add a Location");
      clearFields();
    } else {
      submitButton.setText("Submit Changes");
      titleLabel.setText("Change a Location");
      longNameTextField.setText(currentLocation.getLongName());
      shortNameTextField.setText(currentLocation.getShortName());
      nodeTypeTextField.setText(currentLocation.getLocationType());
    }
  }
}
