package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class NodeComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  TreeMap<String, Node> nodeToRoomMap;

  public NodeComboBoxController() {
    nodeToRoomMap = new TreeMap<String, Node>();
    ArrayList<Node> nodeList = FDdb.getInstance().getAllNodes();
    for (Node n : nodeList) {
      String nodeID = n.getNodeID(); // long name
      nodeToRoomMap.put(nodeID, n);
    }
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
  }

  public Node getNode() {
    return nodeToRoomMap.get(mfxFilterComboBox.getValue());
  }

  public String getNodeID() {
    return mfxFilterComboBox.getValue();
  }

  public void setNodeID(String s) {
    mfxFilterComboBox.setValue(s);
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
