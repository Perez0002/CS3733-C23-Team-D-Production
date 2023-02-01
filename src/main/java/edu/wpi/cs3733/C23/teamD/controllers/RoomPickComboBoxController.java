package edu.wpi.cs3733.C23.teamD.controllers;


import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.Connection;
import java.util.ArrayList;

  private HashMap<String, String> nodeToRoomMap;

  public RoomPickComboBoxController() {
    nodeToRoomMap = new HashMap<>();
    Connection conn = Ddb.makeConnection();
    ArrayList<Move> moveList = Ddb.createJavaMoves(conn);
    for (Move m : moveList) {
      String locName = m.getLongName();
      String nodeID = m.getNodeID();
      // TODO: need to figure out how to grab newest record by date
      // most likely will use ORDER-BY in SQL
      nodeToRoomMap.put(nodeID, locName);
    }
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
  }

  public String getNodeValue() {
    System.out.println("node value method");
    System.out.println(nodeToRoomMap.get(mfxFilterComboBox.getValue()));
    return nodeToRoomMap.get(mfxFilterComboBox.getValue());
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
