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
  protected static final int NODE_HEIGHT = 16; // Node width for node Panes

  /**
   *
   * @param nodeList ArrayList of Nodes to be placed
   * @param event A Function taking a Node and returning an EventHandler<MouseEvent> to pass to the onclick function for each Pane
   * @return A GesturePane containing the Image and each Node mapped as a Pane
   */
  public GesturePane genMapFromNodes(
      ArrayList<Node> nodeList, Function<Node, EventHandler<? super MouseEvent>> event) {
    AnchorPane anchor = new AnchorPane(); // AnchorPane to hold everything

    ImageView imageView =
        new ImageView(
            App.class.getResource("views/floorMaps/00_thelowerlevel1.png").toExternalForm()); // Getting Image from resources
    anchor.getChildren().add(imageView); // Adding Image to AnchorPane

    for (Node node : nodeList) { // For every Node
      final Pane tempPane = new Pane();
      tempPane.setPrefSize(NODE_WIDTH, NODE_HEIGHT);
      tempPane.setLayoutX(node.getXcoord() - NODE_WIDTH / 2);
      tempPane.setLayoutY(node.getYcoord() - NODE_HEIGHT / 2);
      tempPane.setStyle("-fx-background-color: '#013A75'; -fx-border-radius: 1000px;");
      tempPane.setOnMouseClicked(event.apply(node));
      tempPane.setId(node.getNodeID() + "_pane");
      anchor.getChildren().add(tempPane); // Make a new Pane, set it's onclick to a passed in function, and add it to the AnchorPane
    }

    GesturePane returnPane = new GesturePane(anchor); // Pass the AnchorPane with everything in it to a GesturePane
    returnPane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER); // No more scroll bars!!!
    return returnPane; // Return the GesturePane
  }

  /**
   *
   * @param nodeList List of Nodes to display with edges between
   * @return A GesturePane containing the Image of the floor and the Nodes mapped on it with Edges connecting them
   */
  public GesturePane genMapFromNodesWithEdges(ArrayList<Node> nodeList) {
    GesturePane oldPane =
        genMapFromNodes(
            nodeList,
            event -> {
              return null;
            }); // Calling genMapFromNodes to add the nodes with a null function

    javafx.scene.Node incomingNode = oldPane.getContent(); // Getting the AnchorPane from the GesturePane
    try {
      assert incomingNode instanceof AnchorPane; // Ensuring the AnchorPane is an AnchorPane
      AnchorPane anchor = (AnchorPane) incomingNode; // Casting
      ImageView imageView = (ImageView) anchor.getChildren().get(0); // Getting the Image
      Canvas canvas = new Canvas(imageView.getImage().getWidth(), imageView.getImage().getHeight()); // Making a Canvas of the Image Height and Width
      GraphicsContext context = canvas.getGraphicsContext2D(); // Getting GraphicsContext for drawing
      context.setFill(Color.BLACK); // Setting fill color to black

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
        lastNode = node; // Incrementing Node
      }
      context.setLineWidth(1);
      context.strokeText("END", lastNode.getXcoord() + 10, lastNode.getYcoord(), 40); // Labeling last Node in the Path as "END"

      anchor.getChildren().add(1, canvas); // Adding Canvas after the image but before the Panes start

      GesturePane gesturePane = new GesturePane(anchor); // Making a new GesturePane
      gesturePane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER); // No more scroll bars!!!

      return gesturePane; // Returning GesturePane

    } catch (ClassCastException CCE) {
      CCE.printStackTrace(); // If exception, print the stack trace
    }

    return oldPane; // return the previous method's return if unable to cast
  }
}
