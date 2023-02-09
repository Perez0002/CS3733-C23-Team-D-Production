package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.kurobako.gesturefx.GesturePane;

public class MapFactory {

  protected static final int NODE_WIDTH = 16; // Node width for node Panes
  protected static final int NODE_HEIGHT = 16; // Node height for node Panes

  private boolean withEdges;
  private ArrayList<Node> nodeList;
  private Function<Node, EventHandler<MouseEvent>> nodeEvent;

  private MapFactory() {
    this.withEdges = false;
    this.nodeList = new ArrayList<Node>();
    this.nodeEvent =
        new Function<Node, EventHandler<MouseEvent>>() {
          @Override
          public EventHandler<MouseEvent> apply(Node node) {
            return null;
          }
        };
  }

  public static MapFactory startBuild() {
    MapFactory returnable = new MapFactory();
    return returnable;
  }

  public MapFactory withNodes(ArrayList<Node> withNodes) {
    this.nodeList = withNodes;
    return this;
  }

  public MapFactory withNodeFunctions(Function<Node, EventHandler<MouseEvent>> event) {
    this.nodeEvent = event;
    return this;
  }

  public MapFactory withEdges() {
    this.withEdges = true;
    return this;
  }

  public MapFactory withoutEdges() {
    this.withEdges = false;
    return this;
  }

  public GesturePane build() {
    AnchorPane anchor = new AnchorPane(); // AnchorPane to hold everything

    ImageView imageView =
        new ImageView(
            App.class
                .getResource("views/floorMaps/00_thelowerlevel1.png")
                .toExternalForm()); // Getting Image from resources
    anchor.getChildren().add(imageView); // Adding Image to AnchorPane

    Canvas canvas = new Canvas(imageView.getImage().getWidth(), imageView.getImage().getHeight());
    GraphicsContext context = canvas.getGraphicsContext2D();
    anchor.getChildren().add(canvas);
    // For every Node
    for (Node node : nodeList) {
      // Creates popup object
      MapEditorNodeController mapEditor = new MapEditorNodeController(node);
      Pane tempPane =
          MapPaneFactory.startBuild()
              .posX(node.getXcoord() - NODE_WIDTH / 2)
              .posY(node.getYcoord() - NODE_HEIGHT / 2)
              .onClick(this.nodeEvent.apply(node))
              .onMouseEnter(
                  e -> {
                    mapEditor.makePopupAppear();
                  })
              .onMouseExit(
                  e -> {
                    mapEditor.makePopupDisappear();
                  })
              .paneID(node.getNodeID() + "_pane")
              .build();

      // Make a new Pane, set it's onclick to a passed in function, and add it to the AnchorPane
      anchor.getChildren().add(tempPane);
    }

    if (this.withEdges) {
      context.setFill(Color.BLACK);
      Node lastNode = null;
      for (Node node : nodeList) {
        if (lastNode != null) {
          context.strokeLine(
              lastNode.getXcoord(), lastNode.getYcoord(), node.getXcoord(), node.getYcoord());
        } else {
          context.strokeText("START", node.getXcoord(), node.getYcoord() - 10, 40);
          context.setLineWidth(3);
        }
        lastNode = node;
      }
      context.setLineWidth(1);
      context.strokeText("END", lastNode.getXcoord(), lastNode.getYcoord() - 10, 40);
    }

    // Pass the AnchorPane with everything in it to a GesturePane
    GesturePane returnPane = new GesturePane(anchor);
    // No more scroll bars!!!
    returnPane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    // Return the GesturePane
    return returnPane;
  }
}
