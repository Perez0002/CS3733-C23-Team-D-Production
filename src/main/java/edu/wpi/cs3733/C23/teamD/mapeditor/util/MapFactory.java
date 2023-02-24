package edu.wpi.cs3733.C23.teamD.mapeditor.util;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEdge;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class MapFactory {
  private boolean onlyStartEnd;
  private ArrayList<MapNode> nodeList;
  private ArrayList<MapEdge> edgeList;
  private Function<Node, EventHandler<MouseEvent>> nodeEvent;
  private Function<Node, EventHandler<MouseEvent>> nodeMouseEnterEvent;
  private Function<Node, EventHandler<MouseEvent>> nodeMouseExitEvent;

  /** Creates a new MapFactory Object */
  private MapFactory() {
    this.onlyStartEnd = false;
    this.nodeList = new ArrayList<MapNode>();
    this.edgeList = new ArrayList<MapEdge>();
    this.nodeEvent =
        new Function<Node, EventHandler<MouseEvent>>() {
          @Override
          public EventHandler<MouseEvent> apply(Node node) {
            return null;
          }
        };
    this.nodeMouseEnterEvent =
        new Function<Node, EventHandler<MouseEvent>>() {
          @Override
          public EventHandler<MouseEvent> apply(Node node) {
            return null;
          }
        };
    this.nodeMouseExitEvent =
        new Function<Node, EventHandler<MouseEvent>>() {
          @Override
          public EventHandler<MouseEvent> apply(Node node) {
            return null;
          }
        };
  }

  /** @return a new MapFactory object */
  public static MapFactory startBuild() {
    MapFactory returnable = new MapFactory();
    return returnable;
  }

  /**
   * @param withNodes list of Nodes to add to the map
   * @return the MapFactory with these changes
   */
  public MapFactory withNodes(ArrayList<MapNode> withNodes) {
    this.nodeList = withNodes;
    return this;
  }

  /**
   * @param event a Function< Node, EventHandler< MouseEvent >> to apply to every Node
   * @return the MapFactory with these changes
   */
  public MapFactory withNodeFunctions(Function<Node, EventHandler<MouseEvent>> event) {
    this.nodeEvent = event;
    return this;
  }

  public MapFactory withNodeMouseEnterFunctions(Function<Node, EventHandler<MouseEvent>> event) {
    this.nodeMouseEnterEvent = event;
    return this;
  }

  public MapFactory withNodeMouseExitFunctions(Function<Node, EventHandler<MouseEvent>> event) {
    this.nodeMouseExitEvent = event;
    return this;
  }

  /**
   * @param edges a list of MapEdges to add to the Map
   * @return the MapFactory with these changes
   */
  public MapFactory withEdges(ArrayList<MapEdge> edges) {
    this.edgeList = edges;
    return this;
  }

  /**
   * Sets the map to be generated as only drawing the starting and ending Nodes
   *
   * @return the MapFactory with these changes
   */
  public MapFactory onlyStartEnd() {
    this.onlyStartEnd = true;
    return this;
  }

  /**
   * Builds the MapFactory settings into a map
   *
   * @return An array of GesturePanes representing each floor
   */
  public GesturePane build(int floor) {
    HashMap<String, Integer> converter = new HashMap<String, Integer>();
    int totalX = 0;
    int totalY = 0;
    int totalNode = 0;

    converter.put("G", 0);
    converter.put("L1", 1);
    converter.put("L2", 2);
    converter.put("1", 3);
    converter.put("2", 4);
    converter.put("3", 5);

    ImageView image = new ImageView();
    AnchorPane holder = new AnchorPane();
    GesturePane map = new GesturePane();

    if (floor == 0) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/00_thegroundfloor.png").toExternalForm());
    } else if (floor == 1) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/00_thelowerlevel1.png").toExternalForm());
    } else if (floor == 2) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/00_thelowerlevel2.png").toExternalForm());
    } else if (floor == 3) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/01_thefirstfloor.png").toExternalForm());
    } else if (floor == 4) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/02_thesecondfloor.png").toExternalForm());
    } else if (floor == 5) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/03_thethirdfloor.png").toExternalForm());
    }

    holder.getChildren().add(image);

    for (MapEdge edge : edgeList) {
      if (converter.get(edge.getToNode().getNodeFloor().getValue()) == floor
          && converter.get(edge.getFromNode().getNodeFloor().getValue()) == floor)
        holder.getChildren().add(edge.getEdgeRepresentation());
    }

    if (!this.onlyStartEnd) {
      // For every Node
      for (MapNode node : nodeList) {

        if (converter.get(node.getNodeFloor().getValue()) != floor) {
          continue;
        }
        totalX += node.getNodeX().getValue();
        totalY += node.getNodeY().getValue();
        totalNode++;
        // Creates popup object
        holder.getChildren().add(node.getNodeRepresentation());
      }
    } else {
      holder.getChildren().add(nodeList.get(0).getNodeRepresentation());
      holder.getChildren().add(nodeList.get(nodeList.size() - 1).getNodeRepresentation());
    }

    map.setContent(holder);
    map.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    map.zoomTo(0, Point2D.ZERO);
    map.animate(Duration.millis(300))
        .centreOn(
            new Point2D(
                (totalX / (totalNode == 0 ? 1 : totalNode)
                    - App.getPrimaryStage().getScene().getWidth() / 2),
                (totalY / (totalNode == 0 ? 1 : totalNode)
                    - App.getPrimaryStage().getScene().getHeight() / 2)));
    // Return the GesturePane
    return map;
  }
}
