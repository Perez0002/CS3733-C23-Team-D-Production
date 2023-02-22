package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class RoomPickComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  TreeMap<String, String> nodeToRoomMap;

  public RoomPickComboBoxController() {
    nodeToRoomMap = new TreeMap<>();
    ArrayList<Move> moveList = FDdb.getInstance().getAllCurrentMoves(new Date());
    for (Move m : moveList) {
      if (m.getNode() != null) {
        String locName = m.getLocation().getLongName(); // long name
        String nodeID = m.getNode().getNodeID(); // nodeID
        // TODO: need to figure out how to grab newest record by date
        // most likely will use ORDER-BY in SQL
        nodeToRoomMap.put(locName, nodeID);
      }
    }
  }

  public void updateMapping(Date d) {
    nodeToRoomMap = new TreeMap<>();
    ArrayList<Move> moveList = FDdb.getInstance().getAllCurrentMoves(d);
    for (Move m : moveList) {
      if (m.getNode() != null) {
        String locName = m.getLocation().getLongName(); // long name
        String nodeID = m.getNode().getNodeID(); // nodeID
        // TODO: need to figure out how to grab newest record by date
        // most likely will use ORDER-BY in SQL
        nodeToRoomMap.put(locName, nodeID);
      }
    }
    this.initialize();
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
  }

  public String getNodeValue() {
    return nodeToRoomMap.get(mfxFilterComboBox.getValue());
  }

  public String getLocationName() {
    return mfxFilterComboBox.getValue();
  }

  public void setLocationName(String s) {
    this.mfxFilterComboBox.setValue(s);
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }

  public void setDisable(boolean b) {
    mfxFilterComboBox.setDisable(b);
  }

  public void setText(String s) {
    mfxFilterComboBox.setText(s);
  }
}
