package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.*;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.kurobako.gesturefx.GesturePane;

public class MapDrawController {

  protected static final int NODE_WIDTH = 16; // Node width for node Panes
  protected static final int NODE_HEIGHT = 16; // Node height for node Panes

  /**
   * @param nodeList ArrayList of Nodes to be placed
   * @param event A Function taking a Node and returning an EventHandler<MouseEvent> to pass to the
   *     onclick function for each Pane
   * @return A GesturePane containing the Image and each Node mapped as a Pane
   */
  public GesturePane genMapFromNodes(
      ArrayList<Node> nodeList, Function<Node, EventHandler<MouseEvent>> event) {
    AnchorPane anchor = new AnchorPane(); // AnchorPane to hold everything

    ImageView imageView =
        new ImageView(
            App.class
                .getResource("views/floorMaps/00_thelowerlevel1.png")
                .toExternalForm()); // Getting Image from resources
    anchor.getChildren().add(imageView); // Adding Image to AnchorPane

    // For every Node
    for (Node node : nodeList) {

      // Creates popup object
      MapEditorNodeController mapEditor = new MapEditorNodeController(node);
      Pane tempPane =
          PaneFactories.getMapPaneFactory()
              .posX(node.getXcoord() - MapDrawController.NODE_WIDTH / 2)
              .posY(node.getYcoord() - MapDrawController.NODE_HEIGHT / 2)
              .onClick(event.apply(node))
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

    // Pass the AnchorPane with everything in it to a GesturePane
    GesturePane returnPane = new GesturePane(anchor);
    // No more scroll bars!!!
    returnPane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    // Return the GesturePane
    return returnPane;
  }

  /**
   * @param nodeList List of Nodes to display with edges between
   * @return A GesturePane containing the Image of the floor and the Nodes mapped on it with Edges
   *     connecting them
   */
  public GesturePane genMapFromNodesWithEdges(ArrayList<Node> nodeList) {
    // TODO this functionality, while I thought was initially clean, is messy. This should be redone
    // Calling genMapFromNodes to add the nodes with a null function
    GesturePane oldPane =
        genMapFromNodes(
            nodeList,
            event -> {
              return null;
            });

    if (nodeList.isEmpty()) {
      return oldPane;
    }

    // Getting the AnchorPane from the GesturePane
    javafx.scene.Node incomingNode = oldPane.getContent();

    try {
      // Ensuring the AnchorPane is an AnchorPane
      assert incomingNode instanceof AnchorPane;
      // Casting
      AnchorPane anchor = (AnchorPane) incomingNode;
      // Getting the Image
      ImageView imageView = (ImageView) anchor.getChildren().get(0);
      // Making a Canvas of the Image Height and Width
      Canvas canvas =
          new Canvas(
              imageView.getImage().getWidth(),
              imageView.getImage().getHeight());
      // Getting GraphicsContext for drawing
      GraphicsContext context =
          canvas.getGraphicsContext2D();
      // Setting fill color to black
      context.setFill(Color.BLACK);

      Node lastNode = null;
      for (Node node : nodeList) {
        if (lastNode != null) {
          // Assuming we have the previous Node, drawing lines between them
          context.strokeLine(
              lastNode.getXcoord(), lastNode.getYcoord(), node.getXcoord(), node.getYcoord());
        } else {
          // Labeling first Node in the path as "START"
          context.strokeText("START", node.getXcoord() + 10, node.getYcoord(), 40);
          context.setLineWidth(3);
        }
        // Incrementing Node
        lastNode = node;
      }
      context.setLineWidth(1);
      // Labeling last Node in the Path as "END"
      context.strokeText(
          "END",
          lastNode.getXcoord() + 10,
          lastNode.getYcoord(),
          40);
      // Adding Canvas after the image but before the Panes start
      anchor
          .getChildren()
          .add(1, canvas);

      GesturePane gesturePane = new GesturePane(anchor); // Making a new GesturePane
      gesturePane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER); // No more scroll bars!!!

      // Returning GesturePane
      return gesturePane;

    } catch (ClassCastException CCE) {
      // If exception, print the stack trace
      CCE.printStackTrace();
    }
    // return the previous method's return if unable to cast
    return oldPane;
  }
}
