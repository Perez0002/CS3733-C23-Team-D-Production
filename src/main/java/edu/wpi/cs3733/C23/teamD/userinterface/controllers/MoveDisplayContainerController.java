package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.*;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEdge;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.PathfindingMapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.Pathfinder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.kurobako.gesturefx.GesturePane;

public class MoveDisplayContainerController {
  @FXML private MFXFilterComboBox<String> mfxFilterComboBox;
  @FXML private VBox move;
  @FXML BorderPane borderPane;
  @FXML MFXDatePicker datePicker;
  @FXML BorderPane mapPane;
  @FXML StackPane stackPane;
  @FXML private MFXButton LoginButton;
  @FXML private Text locationNameText;
  @FXML private Text messageText;
  @FXML private Text rightRoomText;
  @FXML private Text leftRoomText;
  @FXML private MFXButton swapButton;
  @FXML private MFXButton backButton;
  @FXML private MFXButton floorL1Button;
  @FXML private MFXButton floorL2Button;
  @FXML private MFXButton floor1Button;
  @FXML private MFXButton floor2Button;
  @FXML private MFXButton floor3Button;

  private MFXButton[] floorButtons = new MFXButton[5];
  private Pathfinder pathfinder = new Pathfinder();
  private Move currentMove;
  private Move futureMove;
  private ArrayList<Edge> edges = new ArrayList<Edge>();
  private ArrayList<Move> moves;
  TreeMap<String, Move> nodeToRoomMap;
  private ArrayList<Move> locationMoves = new ArrayList<>();

  ArrayList<MapNode> mapNodes = new ArrayList<MapNode>();
  ArrayList<MapEdge> mapEdges = new ArrayList<MapEdge>();

  @FXML
  public void initialize() {
    // set up the MFXComboBox
    nodeToRoomMap = new TreeMap<>();
    ArrayList<Move> moveList = FDdb.getInstance().getAllCurrentMoves(new Date());
    for (Move m : moveList) {
      if (m.getNode() != null) {
        String locName = m.getLocation().getLongName(); // long name
        nodeToRoomMap.put(locName, m);
      }
    }
    mfxFilterComboBox.setItems(FXCollections.observableArrayList(nodeToRoomMap.keySet()));
    mfxFilterComboBox.setOnAction(setLocation);

    // set up mapPane
    mapPane.setCenter(MapFactory.startBuild().scaleMap().build(1));
    mapPane.setBorder(
        new Border(
            new BorderStroke(
                Color.rgb(4, 17, 64),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(3, 3, 3, 3))));

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
    datePicker.setOnAction(setDate);
    LoginButton.setOnAction(event -> Navigation.navigate(Screen.LOGIN_PAGE));
    swapButton.setOnAction(event -> switchLocations());
    edges = FDdb.getInstance().getAllEdges();
    moves = FDdb.getInstance().getAllMoves();

    floorL1Button.setOnAction(event -> switchFloor(1));
    floorL2Button.setOnAction(event -> switchFloor(2));
    floor1Button.setOnAction(event -> switchFloor(3));
    floor2Button.setOnAction(event -> switchFloor(4));
    floor3Button.setOnAction(event -> switchFloor(5));
    floorButtons[0] = floorL1Button;
    floorButtons[1] = floorL2Button;
    floorButtons[2] = floor1Button;
    floorButtons[3] = floor2Button;
    floorButtons[4] = floor3Button;
    setFloorButtons(1);
  }

  public void switchLocations() {
    String temp = rightRoomText.getText();
    rightRoomText.setText(leftRoomText.getText());
    leftRoomText.setText(temp);
  }

  EventHandler<ActionEvent> setLocation =
      new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          currentMove = nodeToRoomMap.get(mfxFilterComboBox.getValue());
          locationMoves =
              FDdb.getInstance()
                  .getFutureMoves(
                      nodeToRoomMap.get(mfxFilterComboBox.getValue()).getLocation(), new Date());

          setMove(currentMove);

          datePicker.clear();

          locationNameText.setText(currentMove.getLongName());
          messageText.setText(currentMove.getMessage());
          setRightAndLeft();
        }
      };

  private void setRightAndLeft() {
    Node currentNode = currentMove.getNode();
    boolean leftAssigned = true;
    for (Edge edge : edges) {
      if (currentNode == edge.getToNode()) {
        if (leftAssigned) {
          leftRoomText.setText(getLocationName(edge.getFromNode()));
          leftAssigned = false;
        } else {
          rightRoomText.setText(getLocationName(edge.getFromNode()));
          break;
        }
      } else if (currentNode == edge.getFromNode()) {
        if (leftAssigned) {
          leftRoomText.setText(getLocationName(edge.getToNode()));
          leftAssigned = false;
        } else {
          rightRoomText.setText(getLocationName(edge.getToNode()));
          break;
        }
      }
    }
    if (leftAssigned) {
      rightRoomText.setText("");
    }
  }

  EventHandler<ActionEvent> setDate =
      new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {

          for (Move m : locationMoves) {

            LocalDate localDate =
                Instant.ofEpochMilli(m.getMoveDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (localDate.equals(datePicker.getValue())) {
              setFutureMove(m);
            }
          }
        }
      };

  @FXML
  public void logout() {
    display();
    LoginButton.setManaged(true);
    LoginButton.setVisible(true);
    backButton.setManaged(false);
    backButton.setVisible(false);
    CurrentUserEnum._CURRENTUSER.setCurrentUser(null);
  }

  @FXML
  public void display() {
    borderPane.setPadding(new Insets(0, 0, 0, 0));
    move.setManaged(false);
    App.getRootPane().setLeft(null);
    mapPane.setDisable(true);
    stackPane.setPadding(new Insets(0, 0, 0, 0));
    mapPane.setBorder(null);
    LoginButton.setManaged(false);
    LoginButton.setVisible(false);
    swapButton.setManaged(false);
    swapButton.setVisible(false);
    backButton.setManaged(true);
    backButton.setVisible(true);
    backButton.setDisable(false);
  }

  @FXML
  public void viewServiceRequests() {}

  public void back() throws IOException {
    App.getRootPane()
        .setLeft(
            FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/NavBar.fxml")));
    backButton.setDisable(true);
    swapButton.setVisible(true);
    swapButton.setManaged(true);
    move.setManaged(true);
    borderPane.setPadding(new Insets(32, 32, 32, 0));
    mapPane.setDisable(false);
    mapPane.setBorder(
        new Border(
            new BorderStroke(
                Color.rgb(4, 17, 64),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(3, 3, 3, 3))));
    stackPane.setPadding(new Insets(32, 32, 32, 32));
  }

  public void setFutureMove(Move m) {
    futureMove = m;

    messageText.setText(m.getMessage());

    Date dateToRun = new Date();

    ArrayList<Edge> baseEdgeList = FDdb.getInstance().getAllEdges();
    ArrayList<LocationName> baseLocationList = FDdb.getInstance().getAllLocationNames();

    ArrayList<Move> moves = FDdb.getInstance().getAllCurrentMoves(dateToRun);

    HashMap<String, PathNode> pathNodes = new HashMap<>();

    for (Move move : moves) {
      if (move.getNode() != null)
        pathNodes.put(move.getNode().getNodeID(), new PathNode(move.getNode(), move.getLocation()));
    }

    for (Edge edge : baseEdgeList) {
      if (pathNodes.containsKey(edge.getToNodeID())
          && pathNodes.containsKey(edge.getFromNodeID())) {
        PathEdge edge1 =
            new PathEdge(
                pathNodes.get(edge.getFromNode().getNodeID()),
                pathNodes.get(edge.getToNode().getNodeID()));
        PathEdge edge2 =
            new PathEdge(
                pathNodes.get(edge.getToNode().getNodeID()),
                pathNodes.get(edge.getFromNode().getNodeID()));
        if (pathNodes.get(edge.getFromNode().getNodeID()) != null) {
          pathNodes.get(edge.getFromNode().getNodeID()).getEdgeList().add(edge1);
        }

        if (pathNodes.get(edge.getToNode().getNodeID()) != null) {
          pathNodes.get(edge.getToNode().getNodeID()).getEdgeList().add(edge2);
        }
      }
    }

    ArrayList<PathNode> path =
        pathfinder.pathfind(
            pathNodes.get(currentMove.getNodeID()), pathNodes.get(m.getNodeID()), "AStar");
    mapNodes = new ArrayList<MapNode>();

    mapEdges = new ArrayList<>();
    MapNode lastNode = null;
    for (PathNode node : path) {
      PathfindingMapNode pathNode = new PathfindingMapNode(node);
      mapNodes.add(pathNode);
      if (lastNode != null) {
        MapEdge edge =
            new MapEdge(new PathEdge(lastNode.getNode(), node), new SimpleBooleanProperty());
        edge.setNodes(lastNode, pathNode);
        mapEdges.add(edge);
      }

      lastNode = pathNode;
    }

    mapPane.setCenter(
        MapFactory.startBuild()
            .scaleMap()
            .withNodes(mapNodes)
            .withEdges(mapEdges)
            .build(getFloor(currentMove.getNode().getFloor())));
    setFloorButtons(getFloor(currentMove.getNode().getFloor()));
  }

  private String getLocationName(Node n) {
    for (Move m : moves) {
      if (m.getNode() == n) {
        return m.getLongName();
      }
    }
    return "";
  }

  public void setMove(Move m) {
    currentMove = m;
    mapNodes.clear();
    PathNode temp = new PathNode(m.getNode(), m.getLocation());
    mapNodes.add(new MapNode(temp));

    mapPane.setCenter(
        MapFactory.startBuild()
            .scaleMap()
            .withNodes(mapNodes)
            .build(getFloor(m.getNode().getFloor())));
    setFloorButtons(getFloor(m.getNode().getFloor()));
  }

  private int getFloor(String floor) {
    if (floor.equals("L1")) {
      return 1;
    } else if (floor.equals("L2")) {
      return 2;
    } else if (floor.equals("1")) {
      return 3;
    } else if (floor.equals("2")) {
      return 4;
    } else {
      return 5;
    }
  }

  private void switchFloor(int floor) {
    mapPane.setCenter(
        MapFactory.startBuild().scaleMap().withEdges(mapEdges).withNodes(mapNodes).build(floor));
    setFloorButtons(floor);
  }

  private void setFloorButtons(int floor) {
    for (MFXButton b : floorButtons) {
      b.getStyleClass().remove("mapEditorFloorButtonSelected");
      b.getStyleClass().add("mapEditorFloorButton");
    }
    floorButtons[floor - 1].getStyleClass().remove("mapEditorFloorButton");
    floorButtons[floor - 1].getStyleClass().add("mapEditorFloorButtonSelected");

    int[] nodesOnFloors = new int[] {0, 0, 0, 0, 0};

    for (MapNode n : mapNodes) {
      int f = getFloor(n.getNodeFloor().getValue());
      nodesOnFloors[f - 1] = nodesOnFloors[f - 1] + 1;
    }

    for (int i = 0; i < 5; i++) {
      if (nodesOnFloors[i] < 1) {
        floorButtons[i].setManaged(false);
        floorButtons[i].setVisible(false);
      } else {
        floorButtons[i].setManaged(true);
        floorButtons[i].setVisible(true);
      }
    }
  }

  private void scaleMap() {
    GesturePane g = (GesturePane) mapPane.getCenter();
    AnchorPane a = (AnchorPane) g.getContent();
    a.setScaleX(0.5);
    a.setScaleY(0.5);
    a.setTranslateY(-mapPane.getHeight());
    a.setTranslateX(-mapPane.getWidth());
  }

  @FXML
  public void setDefaultLocation() {}
}
