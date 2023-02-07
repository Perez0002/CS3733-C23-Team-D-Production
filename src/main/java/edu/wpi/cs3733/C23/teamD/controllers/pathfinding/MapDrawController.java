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

  protected static final int NODE_WIDTH = 16;
  protected static final int NODE_HEIGHT = 16;

  public GesturePane genMapFromNodes(
      ArrayList<Node> nodeList, Function<Node, EventHandler<? super MouseEvent>> event) {
    AnchorPane anchor = new AnchorPane();

    ImageView imageView =
        new ImageView(
            App.class.getResource("views/floorMaps/00_thelowerlevel1.png").toExternalForm());
    anchor.getChildren().add(imageView);

    for (Node node : nodeList) {
      final Pane tempPane = new Pane();
      tempPane.setPrefSize(NODE_WIDTH, NODE_HEIGHT);
      tempPane.setLayoutX(node.getXcoord() - NODE_WIDTH / 2);
      tempPane.setLayoutY(node.getYcoord() - NODE_HEIGHT / 2);
      tempPane.setStyle("-fx-background-color: '#013A75'; -fx-border-radius: 1000px;");
      tempPane.setOnMouseClicked(event.apply(node));
      tempPane.setId(node.getNodeID() + "_pane");
      anchor.getChildren().add(tempPane);
    }

    GesturePane returnPane = new GesturePane(anchor);
    return returnPane;
  }

  public GesturePane genMapFromNodesWithEdges(ArrayList<Node> nodeList) {
    GesturePane oldPane =
        genMapFromNodes(
            nodeList,
            event -> {
              return null;
            });
    javafx.scene.Node incomingNode = oldPane.getContent();
    try {
      assert incomingNode instanceof AnchorPane;
      AnchorPane anchor = (AnchorPane) incomingNode;
      ImageView imageView = (ImageView) anchor.getChildren().get(0);
      Canvas canvas = new Canvas(imageView.getImage().getWidth(), imageView.getImage().getHeight());
      GraphicsContext context = canvas.getGraphicsContext2D();
      context.setFill(Color.BLACK);

      Node lastNode = null;
      for (Node node : nodeList) {
        if (lastNode != null) {
          context.strokeLine(
              lastNode.getXcoord(), lastNode.getYcoord(), node.getXcoord(), node.getYcoord());
        } else {
          context.strokeText("START", node.getXcoord() + 10, node.getYcoord(), 40);
          context.setLineWidth(3);
        }
        lastNode = node;
      }
      context.setLineWidth(1);
      context.strokeText("END", lastNode.getXcoord() + 10, lastNode.getYcoord(), 40);

      anchor.getChildren().add(1, canvas);

      GesturePane gesturePane = new GesturePane(anchor);
      return gesturePane;

    } catch (ClassCastException CCE) {
      CCE.printStackTrace();
    }

    return oldPane;
  }
}
