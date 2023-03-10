package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import java.util.ArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
  @Getter private StringProperty toolTipMessage;
  @Getter protected Circle nodeRepresentation;
  @Getter private ArrayList<MapEdge> mapEdgeList = new ArrayList<>();
  protected PopOver popup;
  protected Tooltip tooltip;
  protected boolean allowTooltip;
  public static final Color NO_SELECTION = Color.rgb(0x01, 0x3A, 0x75);
  public static final Color SELECTED = Color.rgb(0xCC, 0x22, 0x22);

  public MapNode(PathNode node) {
    /* Set the underlying PathNode of this Node */
    this.node = node;

    /* Default to allowing tooltips to pop up */
    this.allowTooltip = true;

    /* Setting nodeX to a SimpleDoubleProperty to allow tracking as it changes */
    this.nodeX = new SimpleDoubleProperty();
    this.nodeX.set(this.node.getNode().getXcoord());

    /* Setting nodeY to a SimpleDoubleProperty to allow tracking as it changes */
    this.nodeY = new SimpleDoubleProperty();
    this.nodeY.set(this.node.getNode().getYcoord());

    /* Setting nodeBuilding to a SimpleStringProperty to allow tracking as it changes */
    this.nodeBuilding = new SimpleStringProperty();
    this.nodeBuilding.set(this.node.getNode().getBuilding());

    /* Setting nodeFloor to a SimpleStringProperty to allow tracking as it changes */
    this.nodeFloor = new SimpleStringProperty();
    this.nodeFloor.set(this.node.getNode().getFloor());

    /* Setting nodeLongName to a SimpleStringProperty to allow tracking as it changes */
    this.nodeLongName = new SimpleStringProperty();
    this.nodeLongName.set(this.node.getLocation().getLongName());

    /* Setting nodeShortName to a SimpleStringProperty to allow tracking as it changes */
    this.nodeShortName = new SimpleStringProperty();
    this.nodeShortName.set(this.node.getLocation().getShortName());

    /* Setting nodeType to a SimpleStringProperty to allow tracking as it changes */
    this.nodeType = new SimpleStringProperty();
    this.nodeType.set(this.node.getLocation().getLocationType());

    /* Creating and formatting the tooltip */
    this.tooltip = new Tooltip();

    this.toolTipMessage = new SimpleStringProperty();
    this.toolTipMessage.setValue(
        String.format(
            "--------"
                + "%s"
                + "--------"
                + "\n"
                + "Node X Coordinate: "
                + "%s"
                + "\n"
                + "Node Y Coordinate: "
                + "%s"
                + "\n"
                + "Node Building: "
                + "%s"
                + "\n"
                + "Node Floor: "
                + "%s"
                + "\n"
                + "Node Short Name: "
                + "%s"
                + "\n"
                + "Node Type: "
                + "%s"
                + "\n",
            this.getNodeLongName().getValue(),
            this.getNodeX().getValue(),
            this.getNodeY().getValue(),
            this.getNodeBuilding().getValue(),
            this.getNodeFloor().getValue(),
            this.getNodeShortName().getValue(),
            this.getNodeType().getValue()));

    tooltip.textProperty().bindBidirectional(toolTipMessage);

    /* Ensuring the tooltip shows up instantly and does not vanish prematurely */
    tooltip.setShowDelay(Duration.ZERO);
    tooltip.setShowDuration(Duration.INDEFINITE);

    /* Creating default presets for the representation on the map */
    nodeRepresentation = new Circle();
    nodeRepresentation.centerXProperty().bindBidirectional(nodeX);
    nodeRepresentation.centerYProperty().bindBidirectional(nodeY);
    nodeRepresentation.setRadius(16);
    nodeRepresentation.setFill(this.NO_SELECTION);

    /* Allowing tooltip to appear when mouse enters the representation */
    nodeRepresentation.setOnMouseEntered(
        event -> {
          if (this.allowTooltip) {
            Tooltip.install(nodeRepresentation, this.tooltip);
          }
        });
    /* Allowing tooltip to disappear when mouse exits the representation */
    nodeRepresentation.setOnMouseExited(
        event -> {
          Tooltip.uninstall(nodeRepresentation, this.tooltip);
        });
  }

  public void refreshTooltip() {
    this.toolTipMessage.setValue(
        String.format(
            "--------"
                + "%s"
                + "--------"
                + "\n"
                + "Node X Coordinate: "
                + "%s"
                + "\n"
                + "Node Y Coordinate: "
                + "%s"
                + "\n"
                + "Node Building: "
                + "%s"
                + "\n"
                + "Node Floor: "
                + "%s"
                + "\n"
                + "Node Short Name: "
                + "%s"
                + "\n"
                + "Node Type: "
                + "%s"
                + "\n",
            this.getNodeLongName().getValue(),
            this.getNodeX().getValue(),
            this.getNodeY().getValue(),
            this.getNodeBuilding().getValue(),
            this.getNodeFloor().getValue(),
            this.getNodeShortName().getValue(),
            this.getNodeType().getValue()));
  }
}
