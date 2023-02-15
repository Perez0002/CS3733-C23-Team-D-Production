package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class MapFactory {
  private boolean withEdges;
  private boolean onlyStartEnd;
  private ArrayList<Node> nodeList;
  private Function<Node, EventHandler<MouseEvent>> nodeEvent;
  private Function<Node, EventHandler<MouseEvent>> nodeMouseEnterEvent;
  private Function<Node, EventHandler<MouseEvent>> nodeMouseExitEvent;

  /** Creates a new MapFactory Object */
  private MapFactory() {
    this.withEdges = false;
    this.onlyStartEnd = false;
    this.nodeList = new ArrayList<Node>();
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
  public MapFactory withNodes(ArrayList<Node> withNodes) {
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
   * Sets the map to be generated with Edges drawn
   *
   * @return the MapFactory with these changes
   */
  public MapFactory withEdges() {
    this.withEdges = true;
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
    System.out.println("Making Map!");
    HashMap<String, Integer> converter = new HashMap<String, Integer>();
    int totalX = 0;
    int totalY = 0;
    int totalNode = 0;

    converter.put("L1", 0);
    converter.put("L2", 1);
    converter.put("1", 2);
    converter.put("2", 3);
    converter.put("3", 4);

    AnchorPane holder = new AnchorPane();
    ImageView image = new ImageView();
    GesturePane map = new GesturePane();

    if (floor == 0) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/00_thelowerlevel1.png").toExternalForm());
    } else if (floor == 1) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/00_thelowerlevel2.png").toExternalForm());
    } else if (floor == 2) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/01_thefirstfloor.png").toExternalForm());
    } else if (floor == 3) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/02_thesecondfloor.png").toExternalForm());
    } else if (floor == 4) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/03_thethirdfloor.png").toExternalForm());
    }

    Canvas canvas = new Canvas(image.getImage().getWidth(), image.getImage().getHeight());
    holder.getChildren().add(image);
    holder.getChildren().add(canvas);

    if (!this.onlyStartEnd) {
      // For every Node
      for (Node node : nodeList) {

        if (converter.get(node.getFloor()) != floor) {
          continue;
        }
        totalX += node.getXcoord();
        totalY += node.getYcoord();
        totalNode++;
        // Creates popup object
        javafx.scene.Node tempPane =
            MapNodeFactory.startPathBuild()
                .posX(node.getXcoord())
                .posY(node.getYcoord())
                .onClick(this.nodeEvent.apply(node))
                .onMouseEnter(this.nodeMouseEnterEvent.apply(node))
                .onMouseExit(this.nodeMouseExitEvent.apply(node))
                .nodeID(node.getNodeID() + "_pane")
                .build();
        holder.getChildren().add(tempPane);
      }
    } else {
      System.out.println("In else!");

      javafx.scene.Node startPane =
          MapNodeFactory.startPathBuild()
              .posX(nodeList.get(0).getXcoord())
              .posY(nodeList.get(0).getYcoord())
              .onClick(this.nodeEvent.apply(nodeList.get(0)))
              .onMouseEnter(this.nodeMouseEnterEvent.apply(nodeList.get(0)))
              .onMouseExit(this.nodeMouseExitEvent.apply(nodeList.get(0)))
              .nodeID(nodeList.get(0).getNodeID() + "_pane")
              .build();
      javafx.scene.Node endPane =
          MapNodeFactory.startPathBuild()
              .posX(nodeList.get(nodeList.size() - 1).getXcoord())
              .posY(nodeList.get(nodeList.size() - 1).getYcoord())
              .onClick(this.nodeEvent.apply(nodeList.get(nodeList.size() - 1)))
              .onMouseEnter(this.nodeMouseEnterEvent.apply(nodeList.get(nodeList.size() - 1)))
              .onMouseExit(this.nodeMouseExitEvent.apply(nodeList.get(nodeList.size() - 1)))
              .nodeID(nodeList.get(nodeList.size() - 1).getNodeID() + "_pane")
              .build();
      holder.getChildren().add(startPane);
      holder.getChildren().add(endPane);
    }

    GraphicsContext context = canvas.getGraphicsContext2D();
    boolean throughFirst = false;
    if (this.withEdges) {
      Node lastNode = null;
      for (Node node : nodeList) {

        if (converter.get(node.getFloor()) != floor) {
          lastNode = node;
          continue;
        }

        if (throughFirst) {
          context.strokeLine(
              lastNode.getXcoord(), lastNode.getYcoord(), node.getXcoord(), node.getYcoord());
        } else {
          throughFirst = true;
          context.strokeText("START", node.getXcoord(), node.getYcoord() - 10, 40);
          context.setLineWidth(5);
        }
        lastNode = node;
      }
      context.setLineWidth(1);
      if (lastNode != null) {
        context.strokeText("END", lastNode.getXcoord(), lastNode.getYcoord() - 10, 40);
      }
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
