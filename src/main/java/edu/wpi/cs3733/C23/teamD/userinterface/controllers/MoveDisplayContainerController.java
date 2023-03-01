package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.*;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.*;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEdge;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.PathfindingMapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.pathfinding.controllers.TextDirectionsController;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.Pathfinder;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import edu.wpi.cs3733.C23.teamD.user.entities.Kiosk;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.RoomPickComboBoxController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.io.IOException;
import java.net.InetAddress;
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
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.PopOver;

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
  @FXML private MFXToggleButton nodeNameToggle;
  @FXML private Parent roomComboBox;
  @FXML private RoomPickComboBoxController roomComboBoxController;
  @FXML private HBox rightRoomHBox;
  @FXML private HBox leftRoomHBox;
  boolean oneBorder;

  private ArrayList<String> directions = new ArrayList<>();

  private ArrayList<PathfindingMapNode> pathDirections = new ArrayList<PathfindingMapNode>();

  private MFXButton[] floorButtons = new MFXButton[5];
  private Pathfinder pathfinder = new Pathfinder();
  private Move currentMove;
  private Move futureMove;
  private Kiosk defaultKiosk;
  private ArrayList<Edge> edges = new ArrayList<Edge>();
  private ArrayList<Move> moves;
  private PopOver popover;
  private TextDirectionsController textDirectionsController;

  TreeMap<String, Move> nodeToRoomMap;
  private ArrayList<Move> locationMoves = new ArrayList<>();

  ArrayList<MapNode> mapNodes = new ArrayList<MapNode>();
  ArrayList<MapEdge> mapEdges = new ArrayList<MapEdge>();
  private boolean switched = false;

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

    nodeNameToggle.setOnAction(event -> toggleNodeNames());
    nodeNameToggle.setDisable(true);

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
    datePicker.setOnAction(handleDate);
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
    defaultKiosk = new Kiosk();
    try {
      defaultKiosk.setIPaddress(InetAddress.getLocalHost().toString());
      final var resource = App.class.getResource("views/TextDirections.fxml");
      final FXMLLoader loader = new FXMLLoader(resource);
      popover = new PopOver(loader.load());
      textDirectionsController = loader.getController();
      popover.setArrowSize(0);
      popover.setTitle("Text Directions");
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (FDdb.getInstance().getKiosk(defaultKiosk) != null) {
      defaultKiosk = FDdb.getInstance().getKiosk(defaultKiosk);
      if (nodeToRoomMap.get(defaultKiosk.getLocation()) != null) {
        roomComboBoxController.setLocationName(
            nodeToRoomMap.get(defaultKiosk.getLocation()).getLocation().getLongName());
        setRightAndLeft(nodeToRoomMap.get(defaultKiosk.getLocation()), false);
        switched = false;
      }
    }

    stackPane.setPadding(new Insets(32, 32, 32, 32));

    setFloorButtons(1);
  }

  public void switchLocations() {
    if (oneBorder) {
      boolean right = rightRoomHBox.isVisible();
      rightRoomHBox.setVisible(!right);
      leftRoomHBox.setVisible(right);
    }
    String temp = rightRoomText.getText();
    rightRoomText.setText(leftRoomText.getText());
    leftRoomText.setText(temp);
    switched = !switched;
  }

  public void toggleNodeNames() {
    AnchorPane holder = (AnchorPane) ((GesturePane) mapPane.getCenter()).getContent();
    for (javafx.scene.Node node : holder.getChildren()) {
      if (node instanceof TextArea) {
        node.setVisible(nodeNameToggle.isSelected());
      }
    }
  }

  EventHandler<ActionEvent> setLocation =
      new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          currentMove = nodeToRoomMap.get(mfxFilterComboBox.getValue());
          locationMoves =
              FDdb.getInstance()
                  .getFutureMoves(
                      nodeToRoomMap.get(mfxFilterComboBox.getValue()).getLocation(), new Date());

          nodeNameToggle.setDisable(false);

          setMove(currentMove);

          setDate();

          locationNameText.setText(
              String.format(
                  "%.18s" + (currentMove.getLongName().length() > 18 ? "..." : ""),
                  currentMove.getLongName()));
          messageText.setText(currentMove.getMessage());
          stackPane.setPadding(new Insets(32, 32, 32, 32));
        }
      };

  private void setRightAndLeft(Move m, boolean bool) {
    oneBorder = true;
    Node currentNode = m.getNode();
    boolean leftAssigned = true;
    leftRoomText.setText("");
    rightRoomText.setText("");
    for (Edge edge : edges) {
      if (currentNode == edge.getToNode()) {
        if (leftAssigned) {
          leftRoomText.setText(getLocationName(edge.getFromNode()));
          leftAssigned = false;
        } else {
          rightRoomText.setText(getLocationName(edge.getFromNode()));
          oneBorder = false;
          break;
        }
      } else if (currentNode == edge.getFromNode()) {
        if (leftAssigned) {
          leftRoomText.setText(getLocationName(edge.getToNode()));
          leftAssigned = false;
        } else {
          rightRoomText.setText(getLocationName(edge.getToNode()));
          oneBorder = false;
          break;
        }
      }
    }
    leftRoomHBox.setVisible(true);
    if (oneBorder) {
      rightRoomHBox.setVisible(false);
    } else {
      rightRoomHBox.setVisible(true);
    }

    if (leftAssigned) {
      rightRoomText.setText("");
    }
    if (!bool) {
      if (leftRoomText.getText() != null) {
        leftRoomText.setText(String.format("%1.15s", leftRoomText.getText()) + "...");
      }
      if (rightRoomText.getText() != null) {
        rightRoomText.setText(String.format("%1.15s", rightRoomText.getText()) + "...");
      }
    }
  }

  EventHandler<ActionEvent> handleDate =
      new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          setDate();
        }
      };

  private void setDate() {
    boolean exactDate = false;
    Move latest = currentMove;
    ArrayList<Move> movesToRemove = new ArrayList<Move>();
    for (Move m : locationMoves) {
      if (m.getMoveDate() == null) {
        movesToRemove.add(m);
      } else {
        LocalDate localDate =
            Instant.ofEpochMilli(m.getMoveDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (datePicker.getValue() != null) {
          if (localDate.isBefore(datePicker.getValue())) {
            latest = m;
          }
          if (localDate.equals(datePicker.getValue())) {
            setFutureMove(m);
            exactDate = true;
          }
        }
      }
      if (!exactDate) {
        System.out.println("new move");
        setMove(latest);
      }
    }
    locationMoves.removeAll(movesToRemove);
  }

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
    String string = currentMove == null ? "Location Name" : currentMove.getLongName();
    locationNameText.setText(string);
    borderPane.setPadding(new Insets(0, 0, 0, 0));
    move.setManaged(false);
    App.getRootPane().setLeft(null);
    stackPane.setPadding(new Insets(0, 0, 0, 0));
    mapPane.setBorder(null);
    GesturePane g = (GesturePane) mapPane.getCenter();
    g.setGestureEnabled(false);
    LoginButton.setManaged(false);
    LoginButton.setVisible(false);
    swapButton.setManaged(false);
    swapButton.setVisible(false);
    backButton.setManaged(true);
    backButton.setVisible(true);
    backButton.setDisable(false);
    if (defaultKiosk.getLocation() != null)
      setRightAndLeft(nodeToRoomMap.get(defaultKiosk.getLocation()), true);
    if (switched) {
      switchLocations();
      switched = true;
    }
  }

  @FXML
  public void viewServiceRequests() {
    try {
      generateRequestDetailsPopup();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void generateRequestDetailsPopup() throws IOException {
    ArrayList<ServiceRequest> moveServiceRequests = new ArrayList<ServiceRequest>();

    String moveDate =
        Instant.ofEpochMilli(futureMove.getMoveDate().getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .toString();
    String moveLocation = futureMove.getLongName();
    String moveNode = futureMove.getNodeID();
    System.out.println(moveDate + " " + moveLocation + " " + moveNode);
    ArrayList<ServiceRequest> serviceRequests = FDdb.getInstance().getAllGenericServiceRequests();
    for (ServiceRequest s : serviceRequests) {
      String[] items = s.getReason().split(";");
      if (items.length > 2) {
        System.out.println(items[0] + " " + items[1] + " " + items[2]);
        if (items[0].equals(moveDate)
            && items[1].equals(moveLocation)
            && items[2].equals(moveNode)) {
          moveServiceRequests.add(s);
        }
      }
    }

    System.out.println("moveServiceRequests size: " + moveServiceRequests.size());

    final var resource = App.class.getResource("views/RequestDetailsPopup.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    popover = new PopOver(loader.load());
    RequestDetailsPopupController requestDetailsPopupController = loader.getController();
    requestDetailsPopupController.setServiceRequests(moveServiceRequests);
    popover.setArrowSize(0);
    popover.setCornerRadius(32);
    requestDetailsPopupController.setMoveDisplayContainerController(this);
    requestDetailsPopupController.setMove(futureMove);
    requestDetailsPopupController.setFields(0);
    popover.setTitle("Generated Request Details");
    popover.show(App.getPrimaryStage());
  }

  public void back() throws IOException {
    System.out.println(switched);
    App.getRootPane()
        .setLeft(
            FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/C23/teamD/views/NavBar.fxml")));
    backButton.setDisable(true);
    swapButton.setVisible(true);
    swapButton.setManaged(true);
    move.setManaged(true);
    borderPane.setPadding(new Insets(32, 32, 32, 0));
    GesturePane g = (GesturePane) mapPane.getCenter();
    g.setGestureEnabled(true);
    mapPane.setBorder(
        new Border(
            new BorderStroke(
                Color.rgb(4, 17, 64),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(3, 3, 3, 3))));
    stackPane.setPadding(new Insets(32, 32, 32, 32));
    setRightAndLeft(nodeToRoomMap.get(defaultKiosk.getLocation()), false);
    if (switched) {
      switchLocations();
      switched = true;
    }
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
    ArrayList<String> text = pathfinder.textPath(path);
    mapEdges = new ArrayList<>();
    MapNode lastNode = null;
    int i = 0;
    for (PathNode node : path) {
      PathfindingMapNode pathNode = new PathfindingMapNode(node);
      mapNodes.add(pathNode);
      pathDirections.add(pathNode);
      if (text.size() > 0) {
        if (i == 0) {
          pathNode.addDirections(text.get(0));
        } else if (i == path.size() - 1) {
          pathNode.addDirections(text.get(text.size() - 1));
        } else if (node.getLocation().getLocationType().equals("ELEV")
            || node.getLocation().getLocationType().equals("STAI")) {
          pathNode.addDirections(text.get(i));
        } else {
          pathNode.addDirections(null);
        }
      }
      if (lastNode != null) {
        MapEdge edge =
            new MapEdge(new PathEdge(lastNode.getNode(), node), new SimpleBooleanProperty());
        edge.setNodes(lastNode, pathNode);
        mapEdges.add(edge);
      }

      lastNode = pathNode;
      ++i;
    }

    // TODO generate text directions
    ArrayList<String> text2 = pathfinder.textPath(path);
    for (String t : text2) {
      directions.add(t);
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
    locationNameText.setText(
        String.format("%.18s" + (m.getLongName().length() > 18 ? "..." : ""), m.getLongName()));
    messageText.setText(m.getMessage());
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
  public void setDefaultLocation() {
    ArrayList<LocationName> locationNames = FDdb.getInstance().getAllLocationNames();
    for (LocationName l : locationNames) {
      if (l.getLongName().equals(roomComboBoxController.getLocationName())) {
        try {
          if (defaultKiosk.getLocation() == null) {
            FDdb.getInstance()
                .saveKiosk(
                    defaultKiosk =
                        new Kiosk(InetAddress.getLocalHost().toString(), l.getLongName()));

          } else {
            defaultKiosk.setLocation(l.getLongName());
            FDdb.getInstance().updateKiosk(defaultKiosk);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    setRightAndLeft(nodeToRoomMap.get(defaultKiosk.getLocation()), false);
    switched = false;
  }

  @FXML
  public void getDirections() {
    popover.show(App.getPrimaryStage());
    textDirectionsController.setDirections(pathDirections);
  }
}
