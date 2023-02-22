package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.mapeditor.util.PopupFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class PathfindingMapNode extends MapNode {
  private PathfindingMapNode prevNode = null;
  private PathfindingMapNode nextNode = null;
  private EventHandler floorSwitchEvent;
  private Event changeFloor;
  private String directions;

  public PathfindingMapNode(PathNode node) {
    /* Superclass object */
    super(node);
    /* Creates popup on mouse click */

    if (this.getNodeType().getValue().equals("BLUBBERSNUFF")) {
      nodeRepresentation.setVisible(false);
    }
    nodeRepresentation.setOnMouseClicked(
        event -> {
          this.MakePopup();
        });
  }

  public void addNextNode(PathfindingMapNode nextNode) {
    this.nextNode = nextNode;
  }

  public void addPrevNode(PathfindingMapNode prevNode) {
    this.prevNode = prevNode;
  }

  public void addDirections(String directions) {
    this.directions = directions;
  }

  private void MakePopup() {
    if (this.popup == null) {
      /* Assuming the popup does not exist, build a new one from the factory */
      this.popup =
          PopupFactory.startBuild()
              .anchor(this.nodeRepresentation)
              .mapNode(this)
              .withArrows()
              .closeEvent(
                  event -> {
                    this.RemovePopup();
                    this.allowTooltip = true;
                  })
              .nextEvent(
                  event -> {
                    this.focusNext();
                  })
              .prevEvent(
                  event -> {
                    this.focusPrev();
                  })
              .assignDirections(directions)
              .build();
      /* Color the node on the map to represent selection */
      this.nodeRepresentation.setFill(Color.rgb(0xCC, 0x22, 0x22));
      /* Prevent this Node's tooltip from popping up */
      this.allowTooltip = false;
    }
  }

  private void focusNext() {
    this.RemovePopup();
    if (!(this.getNodeFloor().getValue().equals(nextNode.getNodeFloor().getValue()))) {
      System.out.println("Switching Floor");
      nextNode.switchFloor();
    }
    if (nextNode.getNodeType().getValue().equals("BLUBBERSNUFF")) {
      nextNode.fastForward();
    } else {
      Platform.runLater(() -> nextNode.MakePopup());
    }
  }

  private void fastForward() {
    this.focusNext();
  }

  private void rewind() {
    this.focusPrev();
  }

  private void switchFloor() {
    floorSwitchEvent.handle(null);
  }

  private void focusPrev() {
    this.RemovePopup();
    if (!this.getNodeFloor().getValue().equals(prevNode.getNodeFloor().getValue())) {
      prevNode.switchFloor();
    }
    if (prevNode.getNodeType().getValue().equals("BLUBBERSNUFF")) {
      prevNode.rewind();
    } else {
      Platform.runLater(() -> prevNode.MakePopup());
    }
  }

  public void setFloorSwitchEvent(javafx.event.EventHandler floorSwitchEvent) {
    this.floorSwitchEvent = floorSwitchEvent;
  }

  private void RemovePopup() {
    if (popup != null) {
      /* Assuming the popup exists, hide and then remove it to save VRam space */
      popup.hide();
      popup = null;
      /* Set the color of the Node on the map to represent deselection */
      this.nodeRepresentation.setFill(this.NO_SELECTION);
      /* Allow this Node's tooltip to pop up */
      this.allowTooltip = true;
    }
  }
}
