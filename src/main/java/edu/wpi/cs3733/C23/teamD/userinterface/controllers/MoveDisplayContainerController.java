package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

public class MoveDisplayContainerController {
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  @FXML private VBox move;
  private Node currentNode;
  @FXML BorderPane borderPane;
  @FXML MFXDatePicker datePicker;

  @Setter @Getter private MoveDisplayStackController moveDisplayStackController;

  TreeMap<String, Move> nodeToRoomMap;

  private ArrayList<Move> moves = new ArrayList<>();
  private ArrayList<Move> locationMoves = new ArrayList<>();

  @FXML
  public void initialize() throws IOException {
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
    FXMLLoader loader =
        new FXMLLoader(
            App.class.getResource("/edu/wpi/cs3733/C23/teamD/views/MoveDisplayStack.fxml"));

    borderPane.setCenter(loader.load());
    moveDisplayStackController = loader.getController();
    moveDisplayStackController.getMoveDisplayController().setMoveDisplayContainerController(this);

    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
    mfxFilterComboBox.setOnAction(setLocation);
    datePicker.setOnAction(setDate);
  }

  EventHandler<ActionEvent> setLocation =
      new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          moveDisplayStackController.setMove(nodeToRoomMap.get(mfxFilterComboBox.getValue()));
          locationMoves =
              FDdb.getInstance()
                  .getFutureMoves(
                      nodeToRoomMap.get(mfxFilterComboBox.getValue()).getLocation(), new Date());
        }
      };

  EventHandler<ActionEvent> setDate =
      new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {

          for (Move m : locationMoves) {

            LocalDate localDate =
                Instant.ofEpochMilli(m.getMoveDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (localDate.equals(datePicker.getValue())) {
              moveDisplayStackController.setFutureMove(m);
            }
          }
        }
      };

  @FXML
  public void logout() {
    display();
    moveDisplayStackController.logout();
  }

  @FXML
  public void display() {
    borderPane.setPadding(new Insets(0, 0, 0, 0));
    move.setManaged(false);
    moveDisplayStackController.display();
  }

  @FXML
  public void viewServiceRequests() {}

  public void back() {
    move.setManaged(true);
    borderPane.setPadding(new Insets(32, 32, 32, 0));
  }
}
