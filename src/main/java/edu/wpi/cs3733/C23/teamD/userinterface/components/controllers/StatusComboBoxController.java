package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class StatusComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  TreeMap<String, ServiceRequest.Status> nodeToRoomMap;

  public StatusComboBoxController() {
    nodeToRoomMap = new TreeMap<String, ServiceRequest.Status>();
    ArrayList<Node> nodeList = FDdb.getInstance().getAllNodes();

    nodeToRoomMap.put("BLANK", ServiceRequest.Status.BLANK);
    nodeToRoomMap.put("PROGRESSING", ServiceRequest.Status.PROCESSING);
    nodeToRoomMap.put("DONE", ServiceRequest.Status.DONE);
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
  }

  public ServiceRequest.Status getStatus() {
    return nodeToRoomMap.get(mfxFilterComboBox.getValue());
  }

  public void setStatus(String s) {}

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
