package edu.wpi.cs3733.C23.teamD.user.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfilePageChangePasswordController {
  @FXML private MFXTextField originalPassword;

  @FXML private MFXTextField newPassword;

  @FXML private MFXTextField confirmNewPassword;

  @FXML private Label help;

  @FXML
  private void submitChanges() {
    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();
    if (validSubmission()) {
      currentUser.setPassword(newPassword.getText());
      FDdb.getInstance().updateEmployee(currentUser);
      resetChanges();
      ToastController.makeText("Changes Saved.", 1500, 50, 50, 675, 750);
    } else {
    }
  }

  @FXML
  private void resetChanges() {
    originalPassword.setText("");
    newPassword.setText("");
    confirmNewPassword.setText("");
  }

  private boolean validSubmission() {
    Employee currentUser = CurrentUserEnum._CURRENTUSER.getCurrentUser();

    String originalP = originalPassword.getText();
    String newP = newPassword.getText();
    String confirmNewP = confirmNewPassword.getText();

    // checks to see if fields are filled and confirm and new pass match
    if (originalP.equals("") || (newP.equals("")) || (confirmNewP.equals(""))) {
      help.setText("Fill all fields.");
      return false;
    } else if (!originalP.equals(currentUser.getPassword())) {
      help.setText("Original password is incorrect. -" + originalP);
      return false;
    } else if (!confirmNewP.equals(newP)) {
      help.setText("New and Confirmed passwords don't match.");
      return false;
    } else {
      return true;
    }
  }
}
