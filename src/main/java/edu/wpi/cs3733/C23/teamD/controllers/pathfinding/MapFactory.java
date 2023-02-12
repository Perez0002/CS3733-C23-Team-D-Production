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
import net.kurobako.gesturefx.GesturePane;

public class MapFactory {
  protected static final int NODE_WIDTH = 16; // Node width for node Panes
  protected static final int NODE_HEIGHT = 16; // Node height for node Panes

  private boolean withEdges;
  private boolean onlyStartEnd;
  private ArrayList<Node> nodeList;
  private Function<Node, EventHandler<MouseEvent>> nodeEvent;

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
        // Creates popup object
        if (converter.get(node.getFloor()) != floor) {
          continue;
        }

        MapEditorNodeController mapEditor = new MapEditorNodeController(node);
        javafx.scene.Node tempPane =
            MapNodeFactory.startPathBuild()
                .posX(node.getXcoord())
                .posY(node.getYcoord())
                .onClick(this.nodeEvent.apply(node))
                .onMouseEnter(
                    e -> {
                      mapEditor.makePopupAppear();
                    })
                .onMouseExit(
                    e -> {
                      mapEditor.makePopupDisappear();
                    })
                .nodeID(node.getNodeID() + "_pane")
                .build();
        holder.getChildren().add(tempPane);
      }
    } else {
      System.out.println("In else!");
      MapEditorNodeController startNodePopup = new MapEditorNodeController(nodeList.get(0));
      MapEditorNodeController endNodePopup =
          new MapEditorNodeController(nodeList.get(nodeList.size() - 1));
      javafx.scene.Node startPane =
          MapNodeFactory.startPathBuild()
              .posX(nodeList.get(0).getXcoord())
              .posY(nodeList.get(0).getYcoord())
              .onClick(this.nodeEvent.apply(nodeList.get(0)))
              .onMouseEnter(
                  event -> {
                    startNodePopup.makePopupAppear();
                  })
              .onMouseExit(
                  event -> {
                    startNodePopup.makePopupDisappear();
                  })
              .nodeID(nodeList.get(0).getNodeID() + "_pane")
              .build();
      javafx.scene.Node endPane =
          MapNodeFactory.startPathBuild()
              .posX(nodeList.get(nodeList.size() - 1).getXcoord())
              .posY(nodeList.get(nodeList.size() - 1).getYcoord())
              .onClick(this.nodeEvent.apply(nodeList.get(nodeList.size() - 1)))
              .onMouseEnter(
                  event -> {
                    endNodePopup.makePopupAppear();
                  })
              .onMouseExit(
                  event -> {
                    endNodePopup.makePopupDisappear();
                  })
              .nodeID(nodeList.get(nodeList.size() - 1).getNodeID() + "_pane")
              .build();
      holder.getChildren().add(startPane);
      holder.getChildren().add(endPane);
    }

    GraphicsContext context = canvas.getGraphicsContext2D();
    if (this.withEdges) {
      Node lastNode = null;
      for (Node node : nodeList) {

        if (converter.get(node.getFloor()) != floor) {
          lastNode = node;
          continue;
        }

        if (lastNode != null) {
          context.strokeLine(
              lastNode.getXcoord(), lastNode.getYcoord(), node.getXcoord(), node.getYcoord());
        } else {
          context.strokeText("START", node.getXcoord(), node.getYcoord() - 10, 40);
          context.setLineWidth(5);
        }
        lastNode = node;
      }
      context.setLineWidth(1);
      context.strokeText("END", lastNode.getXcoord(), lastNode.getYcoord() - 10, 40);
    }

    map.setContent(holder);
    map.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    map.zoomTo(0, Point2D.ZERO);
    // Return the GesturePane
    return map;
  }
}
