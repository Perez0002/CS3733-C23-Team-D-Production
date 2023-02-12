package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
  public GesturePane[] build() {
    System.out.println("Making Map!");
    AnchorPane[] listOfHolders = new AnchorPane[5];
    ImageView[] listOfImages = new ImageView[5];
    Canvas[] listOfCanvas = new Canvas[5];
    GesturePane[] listOfMaps = new GesturePane[5];

    listOfImages[0] =
        new ImageView(
            App.class.getResource("views/floorMaps/00_thelowerlevel1.png").toExternalForm());
    listOfImages[1] =
        new ImageView(
            App.class.getResource("views/floorMaps/00_thelowerlevel2.png").toExternalForm());
    listOfImages[2] =
        new ImageView(
            App.class.getResource("views/floorMaps/01_thefirstfloor.png").toExternalForm());
    listOfImages[3] =
        new ImageView(
            App.class.getResource("views/floorMaps/02_thesecondfloor.png").toExternalForm());
    listOfImages[4] =
        new ImageView(
            App.class.getResource("views/floorMaps/03_thethirdfloor.png").toExternalForm());

    for (int i = 0; i < listOfHolders.length; i++) {
      listOfHolders[i] = new AnchorPane();
      listOfCanvas[i] =
          new Canvas(listOfImages[i].getImage().getWidth(), listOfImages[i].getImage().getHeight());
      listOfCanvas[i].getGraphicsContext2D().setFill(Color.BLACK);
      listOfHolders[i].getChildren().add(listOfImages[i]);
      listOfHolders[i].getChildren().add(listOfCanvas[i]);
      listOfMaps[i] = new GesturePane(listOfHolders[i]);
      listOfMaps[i].setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    }

    BiConsumer<Node, javafx.scene.Node> mapNodesToFloors =
        (node, pane) -> {
          if (node.getFloor().equals("L1")) {
            listOfHolders[0].getChildren().add(pane);
          } else if (node.getFloor().equals("L2")) {
            listOfHolders[1].getChildren().add(pane);
          } else if (node.getFloor().equals("1")) {
            listOfHolders[2].getChildren().add(pane);
          } else if (node.getFloor().equals("2")) {
            listOfHolders[3].getChildren().add(pane);
          } else if (node.getFloor().equals("3")) {
            listOfHolders[4].getChildren().add(pane);
          }
        };

    if (!this.onlyStartEnd) {
      // For every Node
      for (Node node : nodeList) {
        // Creates popup object
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

        mapNodesToFloors.accept(node, tempPane);
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
      mapNodesToFloors.accept(nodeList.get(0), startPane);
      mapNodesToFloors.accept(nodeList.get(nodeList.size() - 1), endPane);
    }

    Function<Node, GraphicsContext> getFloorContextFromNode =
        (node) -> {
          if (node.getFloor().equals("L1")) {
            return listOfCanvas[0].getGraphicsContext2D();
          } else if (node.getFloor().equals("L2")) {
            return listOfCanvas[1].getGraphicsContext2D();
          } else if (node.getFloor().equals("1")) {
            return listOfCanvas[2].getGraphicsContext2D();
          } else if (node.getFloor().equals("2")) {
            return listOfCanvas[3].getGraphicsContext2D();
          } else if (node.getFloor().equals("3")) {
            return listOfCanvas[4].getGraphicsContext2D();
          }

          return null;
        };
    GraphicsContext context = null;
    if (this.withEdges) {
      Node lastNode = null;
      for (Node node : nodeList) {
        context = getFloorContextFromNode.apply(node);
        System.out.println(context.getCanvas());
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
    listOfImages[0].setCache(false);
    listOfImages[1].setCache(false);
    listOfImages[2].setCache(false);
    listOfImages[3].setCache(false);
    listOfImages[4].setCache(false);
    // Return the GesturePane
    return listOfMaps;
  }
}
