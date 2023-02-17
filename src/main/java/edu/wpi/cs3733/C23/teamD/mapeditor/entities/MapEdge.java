package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import lombok.Getter;

public class MapEdge {
  @Getter private PathEdge edge;
  @Getter private MapNode toNode;
  @Getter private MapNode fromNode;
  @Getter private Line edgeRepresentation;

  private Tooltip tooltip;

  public MapEdge(PathEdge edge) {
    this.edge = edge;

    this.toNode = new MapNode(edge.getToNode());
    this.fromNode = new MapNode(edge.getFromNode());

    this.edgeRepresentation = new Line();
    this.edgeRepresentation.startXProperty().bindBidirectional(this.fromNode.getNodeX());
    this.edgeRepresentation.startYProperty().bindBidirectional(this.fromNode.getNodeY());
    this.edgeRepresentation.endXProperty().bindBidirectional(this.toNode.getNodeX());
    this.edgeRepresentation.endYProperty().bindBidirectional(this.toNode.getNodeY());

    this.tooltip = new Tooltip();
    tooltip.setText(
        String.format(
            "Edge from Node: "
                + this.fromNode.getNode().getNode().getNodeID()
                + "\n"
                + "Edge to Node"
                + this.toNode.getNode().getNode().getNodeID()
                + "\n"
                + "Edge Cost: "
                + this.edge.getCost()));
    tooltip.setShowDelay(Duration.ZERO);
    tooltip.setShowDuration(Duration.INDEFINITE);
  }
}
