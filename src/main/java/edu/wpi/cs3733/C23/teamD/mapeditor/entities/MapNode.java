package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

  @Getter private Circle nodeRepresentation;

  private PopOver popup;
  private Tooltip tooltip;

  public MapNode(PathNode node) {
    this.node = node;

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

    nodeRepresentation = new Circle();
    nodeRepresentation.centerXProperty().bindBidirectional(nodeX);
    nodeRepresentation.centerYProperty().bindBidirectional(nodeY);
    nodeRepresentation.setFill(Color.rgb(1, 58, 117));
  }

  private void MakePopup() {
    popup = new PopOver();


  }
}
