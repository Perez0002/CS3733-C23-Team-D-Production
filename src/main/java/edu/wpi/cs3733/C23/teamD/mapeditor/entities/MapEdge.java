package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.PopupFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import lombok.Getter;
import org.controlsfx.control.PopOver;

public class MapEdge {
  @Getter private PathEdge edge;
  @Getter private MapNode toNode;
  @Getter private MapNode fromNode;
  @Getter private Line edgeRepresentation;

  private Tooltip tooltip;
  private boolean allowTooltip;
  private BooleanProperty editMode;
  private PopOver popup;
  private final Color SELECTED = Color.rgb(0xCD, 0xDF, 0xF6);
  private final Color NO_SELECTION = Color.rgb(0x00, 0x00, 0x00);

  public MapEdge(PathEdge edge, BooleanProperty modeLink) {
    this.edge = edge;

    this.editMode = new SimpleBooleanProperty();
    this.editMode.bind(modeLink);

    /* This needs to be done a better way for the updating to work, but this temporarily assigns the MapNodes */
    this.toNode = null; // new MapNode(edge.getToNode());
    this.fromNode = null; // new MapNode(edge.getFromNode());

    /* Creating and formatting the Line */
    this.edgeRepresentation = new Line();
    this.edgeRepresentation.setStrokeWidth(5);
    this.edgeRepresentation.setStroke(this.NO_SELECTION);

    /* Creating and formatting the Edge's tooltip */
    this.tooltip = new Tooltip();
    this.allowTooltip = true;

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

    this.edgeRepresentation.setOnMouseClicked(
        event -> {
          System.out.println(editMode.getValue());
          if (editMode.getValue()) {
            this.MakePopup();
          }
        });
  }

  public void setNodes(MapNode fromNode, MapNode toNode) {
    this.fromNode = fromNode;
    this.toNode = toNode;

    fromNode.getMapEdgeList().add(this);
    toNode.getMapEdgeList().add(this);

    this.edgeRepresentation.startXProperty().bindBidirectional(this.fromNode.getNodeX());
    this.edgeRepresentation.startYProperty().bindBidirectional(this.fromNode.getNodeY());
    this.edgeRepresentation.endXProperty().bindBidirectional(this.toNode.getNodeX());
    this.edgeRepresentation.endYProperty().bindBidirectional(this.toNode.getNodeY());

    tooltip.setText(
        String.format(
            "Edge from Node: "
                + this.fromNode.getNode().getNode().getNodeID()
                + "\n"
                + "Edge to Node: "
                + this.toNode.getNode().getNode().getNodeID()
                + "\n"
                + "Edge Cost: "
                + this.edge.getCost()));
  }

  private void MakePopup() {
    if (this.popup == null) {
      /* Assuming the popup does not exist, build a new one from the factory */
      this.popup =
          PopupFactory.startBuild()
              .anchor(this.edgeRepresentation)
              .mapEdge(this)
              .deletable()
              .closeEvent(
                  event -> {
                    this.RemovePopup();
                    this.allowTooltip = true;
                  })
              .deleteEvent(
                  event -> {
                    this.RemovePopup();
                    ((AnchorPane) this.edgeRepresentation.getParent())
                        .getChildren()
                        .remove(this.edgeRepresentation);

                    try {
                      FDdb.getInstance().deleteEdge(this.edge.getEdge());
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  })
              .build();
      /* Color the node on the map to represent selection */
      this.edgeRepresentation.setStroke(this.SELECTED);
      /* Prevent this Node's tooltip from popping up */
      this.allowTooltip = false;
    }
  }

  private void RemovePopup() {
    if (popup != null) {
      /* Assuming the popup exists, hide and then remove it to save VRam space */
      popup.hide();
      popup = null;
      /* Set the color of the Node on the map to represent deselection */
      this.edgeRepresentation.setStroke(this.NO_SELECTION);
      /* Allow this Node's tooltip to pop up */
      this.allowTooltip = true;
    }
  }
}
