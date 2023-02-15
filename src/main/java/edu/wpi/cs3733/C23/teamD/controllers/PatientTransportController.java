package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.controllers.components.RoomPickComboBoxController;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.PatientTransportRequest;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class PatientTransportController {

  // Attributes of class--all from SceneBuilder objects
  private boolean helpVisible = false;

  @FXML private Text submittedFormText;
  @FXML private Text incompleteForm;
  @FXML private Text endRoomHelpText;
  @FXML private Text patientIDHelpText;
  @FXML private Text reasonHelpText;
  @FXML private Text sendToHelpText;
  @FXML private MFXTextField patientID;
  @FXML private MFXTextField reason;
  @FXML private MFXTextField sendTo;
  @FXML private MFXCheckbox testCheck;
  @FXML private MFXCheckbox testCheck2;
  @FXML private MFXCheckbox testCheck3;
  @FXML private MFXCheckbox testCheck4;
  // end attributes
  @FXML private MFXCheckbox fieldStaffIDPatientTransportRequest;
  @FXML private MFXButton submitButton;

  @FXML private Parent endRoom;

  @FXML private RoomPickComboBoxController endRoomController;

  @FXML
  /*
  submit()
  @param void
  @return void
  linked to "submit" button on SceneBuilder page, when selected
  submits information filled out in forms
  */
  public void submitPatientTransport() {
    if (checkFields()) {
      // System.out.println(patientID.getText() + " will be moved to " + endRoom.getText()); // for
      // debugging purposes
      PatientTransportRequest patientInformation =
          new PatientTransportRequest(
              // startNode???
              "L1X2255Y0849",
              endRoomController.getNodeValue(),
              checkSelectedEquipment(),
              reason.getText(), // sendTo.getText().split(";"),
              sendTo.getText()); // creates PatientTransportData object
      FDdb.getInstance().saveServiceRequest(patientInformation);
      // patientInformation.printInformation(); // for debeugging purposes
      submittedFormText.setVisible(true);
    }
  } // end submit()

  /*
  checkFields
  @param void
  @return boolean
  helper function for submit() function,
  ensures all necessary fields are filled before submission
  */
  private boolean checkFields() {
    if (checkPatientID() && checkEndRoom()) {
      return true;
    } else {
      if (!helpVisible) {
        displayHelp();
      }
      incompleteForm.setVisible(true);
      submittedFormText.setVisible(false);
      return false;
    }
  } // end checkFields()

  private String checkSelectedEquipment() {
    String checkedEquipment = "";

    // each if statement identifies whether MFXCheckbox is selected
    if (testCheck.isSelected()) {
      checkedEquipment += (testCheck.getText());
    }
    if (testCheck2.isSelected()) {
      checkedEquipment += (testCheck2.getText());
      checkedEquipment += ", ";
    }
    if (testCheck3.isSelected()) {
      checkedEquipment += (testCheck3.getText());
      checkedEquipment += ", ";
    }
    if (testCheck4.isSelected()) {
      checkedEquipment += (testCheck4.getText());
      checkedEquipment += ", ";
    }

    return checkedEquipment;
  } // end checkSelectedEquipment()

  /*
    checkSelectedEquipment()
    @param void
    @return ArrayList<String>, contains text of checked boxes
    interacts with MFXCheckboxes in SceneBuilder, identifies selected boxes and
    creates an ArrayList of the text associated with selected boxes

  private ArrayList<String> checkSelectedEquipment() {
    ArrayList<String> checkedEquipment =
        new ArrayList<String>(); // creates ArrayList<String> object

    // each if statement identifies whether MFXCheckbox is selected
    if (testCheck.isSelected()) {
      checkedEquipment.add(testCheck.getText());
    }
    if (testCheck2.isSelected()) {
      checkedEquipment.add(testCheck2.getText());
    }
    if (testCheck3.isSelected()) {
      checkedEquipment.add(testCheck3.getText());
    }
    if (testCheck4.isSelected()) {
      checkedEquipment.add(testCheck4.getText());
    }

    return checkedEquipment;
  } // end checkSelectedEquipment()
  */

  @FXML
  /*
  clearFields()
  @param void
  @return void
  linked to "Clear" button on SceneBuilder page
  */
  public void clearFields() {

    patientID.clear();
    endRoomController.clearForm();
    reason.clear();
    sendTo.clear();
    testCheck.setSelected(false);
    testCheck2.setSelected(false);
    testCheck3.setSelected(false);
    testCheck4.setSelected(false);
    submittedFormText.setVisible(false);
    // System.out.println("clearFields"); // for debugging purposes
  } // end clearFields()

  @FXML
  /*
  displayHelp()
  @param void
  @return void
  linked to "Help" button on SceneBuilder page, when selected
  reverts to "Help" functions (undetermined)
  */
  public void displayHelp() {
    helpVisible = !helpVisible;

    endRoomHelpText.setVisible(helpVisible);
    patientIDHelpText.setVisible(helpVisible);
    reasonHelpText.setVisible(helpVisible);
    sendToHelpText.setVisible(helpVisible);
  } // end displayHelp()

  // These 2 functions are to better define the requirements for patientID and endRoom. they are not
  // yet implemented.

  /*
  checkPatientID
  @param void
  @return boolean
  checks conditions of patientID
  patientID must pass two conditions:
    (1) ID must equal 8
    (2) ID must not contain a space
  */
  private boolean checkPatientID() {
    final String ID = patientID.getText();
    if (ID.matches("\\d\\d\\d\\d\\d\\d\\d\\d")) {
      return true;
    }
    return false;
  } // end checkPatientID()
  /*
    checkEndRoom
    @param void
    @return boolean
    checks conditions of endRoom
    endRoom must pass two conditions:
    (1) endRoom must equal 10
    (2) endRoom must not contain a space
  */
  private boolean checkEndRoom() {
    if (endRoomController.getNodeValue() == null) {
      return false;
    }
    return true;
  } // end checkEndRoom()
} // end class
