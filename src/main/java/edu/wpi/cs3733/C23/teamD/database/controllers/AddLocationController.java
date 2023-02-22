package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
  void clearFields() {
    errorText.setVisible(false);
    shortNameTextField.clear();
    longNameTextField.clear();
    nodeTypeTextField.clear();
  }

  @FXML
  void submit() {
    if (checkFields()) {
      if (currentLocation == null) {
        errorText.setVisible(false);
        FDdb.getInstance()
            .saveLocationName(
                new LocationName(
                    longNameTextField.getText(),
                    shortNameTextField.getText(),
                    nodeTypeTextField.getText()));
        databaseController.refresh();
        ToastController.makeText(
            "Your location has been added!",
            1500,
            50,
            100,
            (int) Screen.getPrimary().getBounds().getWidth() - 375,
            (int) Screen.getPrimary().getBounds().getHeight() - 275);
        clearFields();
      } else {
        LocationName newLocation = new LocationName();
        newLocation.setLocationType(nodeTypeTextField.getText());
        newLocation.setLongName(longNameTextField.getText());
        newLocation.setShortName(shortNameTextField.getText());

        System.out.println(currentLocation.getLongName());
        System.out.println(newLocation.getLongName());

        FDdb.getInstance().updateLocationNamePK(currentLocation, newLocation);

        databaseController.refresh();
        dataToChange(null);
        ToastController.makeText(
            "Your location has been changed!",
            1500,
            50,
            100,
            (int) Screen.getPrimary().getBounds().getWidth() - 375,
            (int) Screen.getPrimary().getBounds().getHeight() - 275);
      }
    } else {
      errorText.setVisible(true);
    }
  }

  public boolean checkFields() {
    return !(longNameTextField.getText().isEmpty()
        || shortNameTextField.getText().isEmpty()
        || nodeTypeTextField.getText().isEmpty());
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
