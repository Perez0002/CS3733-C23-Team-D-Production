package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import org.controlsfx.control.PopOver;

public class MapNode {
  @Getter private PathNode node;
  @Getter private SimpleDoubleProperty nodeX;
  @Getter private SimpleDoubleProperty nodeY;
  @Getter private SimpleStringProperty nodeBuilding;
  @Getter private SimpleStringProperty nodeFloor;
  @Getter private SimpleStringProperty nodeLongName;
  @Getter private SimpleStringProperty nodeShortName;
  @Getter private SimpleStringProperty nodeType;
  @Getter protected Circle nodeRepresentation;
  protected PopOver popup;
  private Tooltip tooltip;
  protected boolean allowTooltip;

  public MapNode(PathNode node) {
    this.node = node;

    this.allowTooltip = true;

    this.nodeX = new SimpleDoubleProperty();
    this.nodeX.set(this.node.getNode().getXcoord());

    this.nodeY = new SimpleDoubleProperty();
    this.nodeY.set(this.node.getNode().getYcoord());

    this.nodeBuilding = new SimpleStringProperty();
    this.nodeBuilding.set(this.node.getNode().getBuilding());

    this.nodeFloor = new SimpleStringProperty();
    this.nodeFloor.set(this.node.getNode().getFloor());

    this.nodeLongName = new SimpleStringProperty();
    this.nodeLongName.set(this.node.getLocation().getLongName());

    this.nodeShortName = new SimpleStringProperty();
    this.nodeShortName.set(this.node.getLocation().getShortName());

    this.nodeType = new SimpleStringProperty();
    this.nodeType.set(this.node.getLocation().getLocationType());

    this.tooltip = new Tooltip();
    tooltip.setText(
        String.format(
            "Node X Coordinate: "
                + this.nodeX.getValue()
                + "\n"
                + "Node Y Coordinate: "
                + this.nodeY.getValue()
                + "\n"
                + "Node Building: "
                + this.nodeBuilding.getValue()
                + "\n"
                + "Node Floor: "
                + this.nodeFloor.getValue()
                + "\n"
                + "Node Long Name: "
                + this.nodeLongName.getValue()
                + "\n"
                + "Node Short Name: "
                + this.nodeShortName.getValue()
                + "\n"
                + "Node Type: "
                + this.nodeType.getValue()
                + "\n"));
    tooltip.setAnchorX(this.nodeX.doubleValue());
    tooltip.setAnchorY(this.nodeY.doubleValue());
    tooltip.setShowDelay(Duration.ZERO);
    tooltip.setShowDuration(Duration.INDEFINITE);

    nodeRepresentation = new Circle();
    nodeRepresentation.centerXProperty().bindBidirectional(nodeX);
    nodeRepresentation.centerYProperty().bindBidirectional(nodeY);
    nodeRepresentation.setRadius(16);
    nodeRepresentation.setFill(Color.rgb(0x01, 0x3A, 0x75));
    nodeRepresentation.setOnMouseEntered(
        event -> {
          if (this.allowTooltip) {
            Tooltip.install(nodeRepresentation, this.tooltip);
          }
        });
    nodeRepresentation.setOnMouseExited(
        event -> {
          Tooltip.uninstall(nodeRepresentation, this.tooltip);
        });
  }
}
