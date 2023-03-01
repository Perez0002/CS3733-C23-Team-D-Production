package edu.wpi.cs3733.C23.teamD.pathfinding.controllers;

import edu.wpi.cs3733.C23.teamD.mapeditor.entities.PathfindingMapNode;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Setter;

public class TextDirectionsController {
  @FXML private VBox paneDirections;
  @Setter PathfindingController pathfindingController;

  public void setDirections(ArrayList<PathfindingMapNode> pathfindingMapNodes) {
    paneDirections.getChildren().clear();
    String directionStr = "";
    Label headerInit = new Label("Floor " + pathfindingMapNodes.get(0).getNodeFloor().getValue());
    headerInit.setStyle("-fx-font-size: 16pt; -fx-font-family: 'Nunito Sans Bold', sans-serif;");
    paneDirections.getChildren().add(headerInit);
    for (PathfindingMapNode pathfindingMapNode : pathfindingMapNodes) {
      if (pathfindingMapNode.getDirections() != null) {
        directionStr = directionStr.concat(pathfindingMapNode.getDirections() + "\n");

        if (pathfindingMapNode.getNextNode() == null) {
          Label label = new Label(directionStr);
          VBox box = new VBox(label);
          label.setWrapText(true);
          VBox.setMargin(box, new Insets(0, 0, 0, 0));
          paneDirections.getChildren().add(box);
          label.setStyle("-fx-font-size: 16pt;");

        } else if (!pathfindingMapNode
            .getNode()
            .getNode()
            .getFloor()
            .equals(pathfindingMapNode.getNextNode().getNode().getNode().getFloor())) {
          directionStr = directionStr.concat("\n");
          Label label = new Label(directionStr);
          VBox box = new VBox(label);
          label.setWrapText(true);
          VBox.setMargin(box, new Insets(0, 0, 0, 10));
          paneDirections.getChildren().add(box);

          label.setStyle("-fx-font-size: 16pt;");
          paneDirections.getChildren().add(label);
          Label header =
              new Label("Floor " + pathfindingMapNode.getNextNode().getNode().getNode().getFloor());
          header.setStyle("-fx-font-size: 16pt;-fx-font-family: 'Nunito Sans Bold', sans-serif;");

          paneDirections.getChildren().add(header);
          directionStr = "";
        }
      }
    }
  }

  public void setPath(PathNode node) {}
}
