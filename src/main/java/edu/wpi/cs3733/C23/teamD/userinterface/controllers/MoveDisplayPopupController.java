package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

public class MoveDisplayPopupController {
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  @FXML private VBox move;
  private Node currentNode;

  @Setter @Getter private MoveDisplayStackController moveDisplayStackController;

  TreeMap<String, Move> nodeToRoomMap;

  @FXML
  public void initialize() {
    nodeToRoomMap = new TreeMap<>();
    ArrayList<Move> moveList = FDdb.getInstance().getAllCurrentMoves(new Date());
    for (Move m : moveList) {
      if (m.getNode() != null) {
        String locName = m.getLocation().getLongName(); // long name
        // TODO: need to figure out how to grab newest record by date
        // most likely will use ORDER-BY in SQL
        nodeToRoomMap.put(locName, m);
      }
    }
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
    mfxFilterComboBox.setOnAction(setLocation);
  }

  EventHandler<ActionEvent> setLocation =
      new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          moveDisplayStackController.setMove(nodeToRoomMap.get(mfxFilterComboBox.getValue()));
        }
      };

  @FXML
  public void logout() {
    moveDisplayStackController.logout();
    /*
    move.setVisible(false);
    move.setManaged(false);
    loginButton.setVisible(true);
    loginButton.setManaged(true);
    App.getRootPane().setLeft(null);

     */
  }
}
