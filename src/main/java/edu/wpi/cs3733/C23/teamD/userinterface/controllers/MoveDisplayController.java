package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class MoveDisplayController {
  @FXML private MFXButton LoginButton;
  @FXML private BorderPane moveDisplayborderPane;
  @FXML private Text locationNameText;
  @FXML private Text messageText;
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  @FXML private Text rightRoomText;
  @FXML private Text leftRoomText;
  @FXML private MFXButton swapButton;

  private ArrayList<Edge> edges = new ArrayList<Edge>();
  TreeMap<String, String> nodeToRoomMap;
  private Date currentDate;
  private Node currentNode;
  private ArrayList<Move> moves;
  @Setter @Getter private MoveDisplayStackController moveDisplayStackController;
  @Setter private MoveDisplayContainerController moveDisplayContainerController;

  @FXML
  public void initialize() {
    LoginButton.setDisable(true);
    LoginButton.setOnAction(
        event -> {
          try {
            login();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    swapButton.setOnAction(event -> switchLocations());
    edges = FDdb.getInstance().getAllEdges();
    moves = FDdb.getInstance().getAllMoves();
  }

  private void login() throws IOException {
    App.getRootPane()
        .setLeft(
            FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/NavBar.fxml")));
    moveDisplayContainerController.login();
    LoginButton.setDisable(true);
    swapButton.setVisible(true);
    swapButton.setManaged(true);
  }

  public void switchLocations() {
    String temp = rightRoomText.getText();
    rightRoomText.setText(leftRoomText.getText());
    leftRoomText.setText(temp);
  }

  public void logout() {
    LoginButton.setDisable(false);
    swapButton.setVisible(false);
    swapButton.setManaged(false);
  }

  public void setLocation(Move m) {
    locationNameText.setText(m.getLongName());
    messageText.setText(m.getMessage());
    Node currentNode = m.getNode();

    boolean leftAssigned = true;
    for (Edge e : edges) {
      if (currentNode == e.getToNode()) {
        if (leftAssigned) {
          leftRoomText.setText(getLocationName(e.getFromNode()));
          leftAssigned = false;
        } else {
          rightRoomText.setText(getLocationName(e.getFromNode()));
          break;
        }
      } else if (currentNode == e.getFromNode()) {
        if (leftAssigned) {
          leftRoomText.setText(getLocationName(e.getToNode()));
          leftAssigned = false;
        } else {
          rightRoomText.setText(getLocationName(e.getToNode()));
          break;
        }
      }
    }
  }

  private String getLocationName(Node n) {
    for (Move m : moves) {
      if (m.getNode() == n) {
        return m.getLongName();
      }
    }
    return "";
  }
}
