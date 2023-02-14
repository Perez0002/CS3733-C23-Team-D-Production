package edu.wpi.cs3733.C23.teamD.controllers.components;

<<<<<<< HEAD
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
=======
import io.github.palexdev.materialfx.controls.MFXComboBox;
>>>>>>> development
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class UrgencySelectorBoxController {

  @FXML private MFXComboBox mfxComboBox;
  @FXML private ArrayList<String> urgency;

  public UrgencySelectorBoxController() {
    urgency = new ArrayList<String>();
    urgency = new ArrayList<>();
    urgency.add("High");
    urgency.add("Medium");
    urgency.add("Low");
  }

  public void initialize() {
    mfxComboBox.setItems(FXCollections.observableArrayList(urgency));
  }

  public String getEmployeeName() {
    return mfxComboBox.getText();
  }

  public void clearForm() {
    mfxComboBox.setValue(null);
  }
}
