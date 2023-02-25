package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEdge;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.PathfindingMapNode;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.controllers.PathfindingController;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.Pathfinder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;

public class MoveDisplayStackController {
  @FXML private StackPane stackPane;

  @Getter @Setter private MoveDisplayController moveDisplayController;

  private BorderPane mapPane = new BorderPane();
  private Pathfinder pathfinder = new Pathfinder();
  private Move currentMove;
  private Move futureMove;

  private PathfindingController pathfindingController = new PathfindingController();

  @FXML
  public void initialize() throws IOException {
    mapPane.setCenter(MapFactory.startBuild().build(1));
    stackPane.getChildren().add(mapPane);

    final FXMLLoader loader =
        new FXMLLoader(App.class.getResource("/edu/wpi/cs3733/C23/teamD/views/MoveDisplay.fxml"));
    stackPane.getChildren().add(loader.load());

    // stage.setScene(scene);
    // stage.show();

    moveDisplayController = loader.getController();
    moveDisplayController.setMoveDisplayStackController(this);
  }

  public void logout() {
    App.getRootPane().setLeft(null);
    moveDisplayController.logout();
  }

  public void setFutureMove(Move m) {
    futureMove = m;

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
    ArrayList<MapNode> mapNodes = new ArrayList<MapNode>();

    ArrayList<MapEdge> mapEdges = new ArrayList<>();
    MapNode lastNode = null;
    for (PathNode node : path) {
      PathfindingMapNode pathNode = new PathfindingMapNode(node);
      mapNodes.add(pathNode);
      if (lastNode != null) {
        MapEdge edge = new MapEdge(new PathEdge(lastNode.getNode(), node));
        edge.setNodes(lastNode, pathNode);
        mapEdges.add(edge);
      }

      lastNode = pathNode;
    }

    mapPane.setCenter(
        MapFactory.startBuild()
            .withNodes(mapNodes)
            .withEdges(mapEdges)
            .build(getFloor(currentMove.getNode().getFloor())));
  }

  public void setMove(Move m) {
    currentMove = m;
    moveDisplayController.setLocation(m);
    ArrayList<MapNode> mapNodes = new ArrayList<MapNode>();
    PathNode temp = new PathNode(m.getNode(), m.getLocation());
    mapNodes.add(new MapNode(temp));

    mapPane.setCenter(
        MapFactory.startBuild().withNodes(mapNodes).build(getFloor(m.getNode().getFloor())));
  }

  private int getFloor(String floor) {
    if (floor.equals("L1")) {
      return 1;
    } else if (floor.equals("L2")) {
      return 2;
    } else if (floor.equals("1")) {
      return 2;
    } else if (floor.equals("2")) {
      return 4;
    } else {
      return 5;
    }
  }
}
