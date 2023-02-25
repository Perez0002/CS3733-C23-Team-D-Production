package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class StatusComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  private ArrayList<String> statusValues = new ArrayList<>();

  public StatusComboBoxController() {
    statusValues.add("BLANK");
    statusValues.add("PROCESSING");
    statusValues.add("DONE");
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(statusValues));
  }

  public ServiceRequest.Status getStatus() {
    String statusBoxVal = mfxFilterComboBox.getValue();
    System.out.println(statusBoxVal);
    ServiceRequest.Status status = ServiceRequest.Status.BLANK;
    if (statusBoxVal.equals("DONE")) status = ServiceRequest.Status.DONE;
    else if (statusBoxVal.equals("PROCESSING")) status = ServiceRequest.Status.PROCESSING;
    return status;
  }

  public void setStatus(String s) {
    mfxFilterComboBox.setValue(s);
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
