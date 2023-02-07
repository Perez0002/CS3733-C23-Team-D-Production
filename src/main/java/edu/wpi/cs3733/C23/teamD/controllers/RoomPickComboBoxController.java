package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class RoomPickComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  private HashMap<String, String> nodeToRoomMap;

  public RoomPickComboBoxController() {
    nodeToRoomMap = new HashMap<>();
    ArrayList<Move> moveList = Ddb.createJavaMoves();
    for (Move m : moveList) {
      String locName = m.getLocation().getLongName();
      String nodeID = m.getNode().getNodeID();
      // TODO: need to figure out how to grab newest record by date
      // most likely will use ORDER-BY in SQL
      nodeToRoomMap.put(locName, nodeID);
    }
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
  }

  public String getNodeValue() {
    return nodeToRoomMap.get(mfxFilterComboBox.getValue());
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
