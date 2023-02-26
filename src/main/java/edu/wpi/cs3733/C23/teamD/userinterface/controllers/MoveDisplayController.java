package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
  @FXML private MFXButton backButton;

  private ArrayList<Edge> edges = new ArrayList<Edge>();
  TreeMap<String, String> nodeToRoomMap;
  private Date currentDate;
  private Node currentNode;
  private ArrayList<Move> moves;
  @FXML @Getter private HBox topHBox;
  @FXML @Getter private HBox bottomHBox;
  @Setter @Getter private MoveDisplayStackController moveDisplayStackController;
  @Setter private MoveDisplayContainerController moveDisplayContainerController;

  @FXML
  public void initialize() {
    LoginButton.setManaged(false);
    LoginButton.setVisible(false);
    backButton.setDisable(true);
    backButton.setOnAction(
        event -> {
          try {
            back();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    LoginButton.setOnAction(event -> Navigation.navigate(Screen.LOGIN_PAGE));
    swapButton.setOnAction(event -> switchLocations());
    edges = FDdb.getInstance().getAllEdges();
    moves = FDdb.getInstance().getAllMoves();
  }

  private void back() throws IOException {
    App.getRootPane()
        .setLeft(
            FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/NavBar.fxml")));
    moveDisplayContainerController.back();
    moveDisplayStackController.back();
    backButton.setDisable(true);
    swapButton.setVisible(true);
    swapButton.setManaged(true);
  }

  public void switchLocations() {
    String temp = rightRoomText.getText();
    rightRoomText.setText(leftRoomText.getText());
    leftRoomText.setText(temp);
  }

  public void logout() {
    System.out.println("logout");
    LoginButton.setManaged(true);
    LoginButton.setVisible(true);
    backButton.setManaged(false);
    backButton.setVisible(false);
    CurrentUserEnum._CURRENTUSER.setCurrentUser(null);
  }

  public void display() {
    System.out.println("display");
    LoginButton.setManaged(false);
    LoginButton.setVisible(false);
    swapButton.setManaged(false);
    swapButton.setVisible(false);
    backButton.setManaged(true);
    backButton.setVisible(true);
    backButton.setDisable(false);
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
