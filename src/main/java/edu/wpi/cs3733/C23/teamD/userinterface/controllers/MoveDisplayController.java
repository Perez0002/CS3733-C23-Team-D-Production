package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MoveDisplayController {
  @FXML private BorderPane mapBorderPane;
  @FXML private MFXButton loginButton;

  @FXML private VBox move;

  @FXML private BorderPane moveDisplayborderPane;
  @FXML private Text locationNameText;
  @FXML private Text messageText;
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  @FXML private Text rightRoomText;
  @FXML private Text leftRoomText;
  TreeMap<String, String> nodeToRoomMap;
  private Date currentDate;
  private Node currentNode;

  @FXML
  public void initialize() {
    mapBorderPane.setCenter(MapFactory.startBuild().build(1));
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
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
    mfxFilterComboBox.setOnAction(updateLocation);
    loginButton.setOnAction(
        event -> {
          try {
            login();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    loginButton.setVisible(false);
    loginButton.setManaged(false);
  }

  EventHandler<ActionEvent> updateLocation =
      new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          locationNameText.setText(mfxFilterComboBox.getValue() + " selected");
          currentNode = FDdb.getInstance().getNode(nodeToRoomMap.get(mfxFilterComboBox.getValue()));
        }
      };

  private void getAdjacentLocations() {
    // TODO
  }

  private void login() throws IOException {
    move.setVisible(true);
    move.setManaged(true);
    loginButton.setVisible(false);
    loginButton.setManaged(false);
    App.getRootPane()
        .setLeft(
            FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/NavBar.fxml")));
  }

  @FXML
  public void logout() {
    move.setVisible(false);
    move.setManaged(false);
    loginButton.setVisible(true);
    loginButton.setManaged(true);
    App.getRootPane().setLeft(null);
  }

  @FXML
  public void switchLocations() {
    String temp = rightRoomText.getText();
    rightRoomText.setText(leftRoomText.getText());
    leftRoomText.setText(temp);
  }
}
