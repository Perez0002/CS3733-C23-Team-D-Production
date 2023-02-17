package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
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

    /* This needs to be done a better way for the updating to work, but this temporarily assigns the MapNodes */
    this.toNode = new MapNode(edge.getToNode());
    this.fromNode = new MapNode(edge.getFromNode());

    /* Creating and formatting the Line */
    this.edgeRepresentation = new Line();
    this.edgeRepresentation.startXProperty().bindBidirectional(this.fromNode.getNodeX());
    this.edgeRepresentation.startYProperty().bindBidirectional(this.fromNode.getNodeY());
    this.edgeRepresentation.endXProperty().bindBidirectional(this.toNode.getNodeX());
    this.edgeRepresentation.endYProperty().bindBidirectional(this.toNode.getNodeY());
    this.edgeRepresentation.setStrokeWidth(5);
    this.edgeRepresentation.setStroke(Color.rgb(0x00, 0x00, 0x00));

    /* Creating and formatting the Edge's tooltip */
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
    /* Ensuring the tooltip shows up instantly and does not vanish prematurely */
    tooltip.setShowDelay(Duration.ZERO);
    tooltip.setShowDuration(Duration.INDEFINITE);

    /* Making the tooltip appear on mouse enter */
    this.edgeRepresentation.setOnMouseEntered(
        event -> {
          Tooltip.install(this.edgeRepresentation, this.tooltip);
        });

    /* Making the tooltip disappear on mouse exit */
    this.edgeRepresentation.setOnMouseExited(
        event -> {
          Tooltip.uninstall(this.edgeRepresentation, this.tooltip);
        });
  }
}
