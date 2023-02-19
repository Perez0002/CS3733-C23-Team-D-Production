package edu.wpi.cs3733.C23.teamD.userinterface.components.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class LocationComboBoxController {

  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  TreeMap<String, LocationName> nodeToRoomMap;

  public LocationComboBoxController() {
    nodeToRoomMap = new TreeMap<>();
    ArrayList<LocationName> locationList = FDdb.getInstance().getAllLocationNames();
    for (LocationName l : locationList) {
      String locName = l.getLongName(); // long name
      // TODO: need to figure out how to grab newest record by date
      // most likely will use ORDER-BY in SQL
      nodeToRoomMap.put(locName, l);
    }
  }

  public void initialize() {
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
  }

  public LocationName getLocation() {
    return nodeToRoomMap.get(mfxFilterComboBox.getValue());
  }

  public String getLocationLongName() {
    return mfxFilterComboBox.getValue();
  }

  public void clearForm() {
    mfxFilterComboBox.setValue(null);
  }
}
