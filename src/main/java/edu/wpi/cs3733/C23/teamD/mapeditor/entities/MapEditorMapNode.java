package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.PopupFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import net.kurobako.gesturefx.GesturePane;

public class MapEditorMapNode extends MapNode {

  private static MapEditorMapNode FIRST_NODE = null;
  private static boolean IS_MAKING_EDGE = false;
  private static Line CURRENT_EDGE = null;

  public MapEditorMapNode(PathNode node) {
    /* Superclass object */
    super(node);
    /* Creates popup on mouse click */

    nodeRepresentation.setOnMouseClicked(
        event -> {
          ((GesturePane) this.nodeRepresentation.getParent().getParent()).setGestureEnabled(true);

          if (!event.isShiftDown() && !IS_MAKING_EDGE) {
            this.MakePopup();
          }

          if (event.isShiftDown()) {

            if (IS_MAKING_EDGE && FIRST_NODE == this) {
              ((AnchorPane) this.nodeRepresentation.getParent()).getChildren().remove(CURRENT_EDGE);
              FIRST_NODE.nodeRepresentation.setFill(Color.rgb(0x01, 0x3A, 0x75));
              IS_MAKING_EDGE = false;
              CURRENT_EDGE = null;
              FIRST_NODE = null;
              return;
            }

            if (IS_MAKING_EDGE && FIRST_NODE != this) {
              ((AnchorPane) this.nodeRepresentation.getParent()).getChildren().remove(CURRENT_EDGE);
              MapEdge tempEdge = new MapEdge(new PathEdge(FIRST_NODE.getNode(), this.getNode()));
              tempEdge.setNodes(FIRST_NODE, this);
              ((AnchorPane) this.nodeRepresentation.getParent())
                  .getChildren()
                  .add(1, tempEdge.getEdgeRepresentation());

              FDdb.getInstance()
                  .saveEdge(
                      new Edge(
                          tempEdge.getEdge().getFromNode().getNode(),
                          tempEdge.getEdge().getToNode().getNode()));

              this.nodeRepresentation.setFill(Color.rgb(0x01, 0x3A, 0x75));
              FIRST_NODE.nodeRepresentation.setFill(Color.rgb(0x01, 0x3A, 0x75));
              IS_MAKING_EDGE = false;
              CURRENT_EDGE = null;
              FIRST_NODE = null;
              return;
            }

            if (!IS_MAKING_EDGE) {
              FIRST_NODE = this;
              IS_MAKING_EDGE = true;

              CURRENT_EDGE = new Line();
              CURRENT_EDGE.setStrokeWidth(5);
              CURRENT_EDGE.startXProperty().bindBidirectional(this.getNodeX());
              CURRENT_EDGE.startYProperty().bindBidirectional(this.getNodeY());

              CURRENT_EDGE.endXProperty().setValue(this.getNodeX().getValue());
              CURRENT_EDGE.endYProperty().setValue(this.getNodeY().getValue());

              ((AnchorPane) this.nodeRepresentation.getParent()).getChildren().add(1, CURRENT_EDGE);
              this.nodeRepresentation.setFill(Color.rgb(0x00, 0xFF, 0x00));
              return;
            }
          }
        });

    nodeRepresentation.setOnMouseEntered(
        event -> {
          if (this.allowTooltip) {
            Tooltip.install(nodeRepresentation, this.tooltip);
          }

          if (IS_MAKING_EDGE && FIRST_NODE != this) {
            CURRENT_EDGE.endXProperty().bindBidirectional(this.getNodeX());
            CURRENT_EDGE.endYProperty().bindBidirectional(this.getNodeY());
            this.nodeRepresentation.setFill(Color.rgb(0x00, 0xFF, 0x00));
          }
        });

    nodeRepresentation.setOnMouseExited(
        event -> {
          Tooltip.uninstall(nodeRepresentation, this.tooltip);

          if (IS_MAKING_EDGE && FIRST_NODE != this) {
            CURRENT_EDGE.endXProperty().unbindBidirectional(this.getNodeX());
            CURRENT_EDGE.endYProperty().unbindBidirectional(this.getNodeY());

            CURRENT_EDGE.endXProperty().setValue(FIRST_NODE.getNodeX().getValue());
            CURRENT_EDGE.endYProperty().setValue(FIRST_NODE.getNodeY().getValue());
            this.nodeRepresentation.setFill(Color.rgb(0x01, 0x3A, 0x75));
          }
        });

    nodeRepresentation.setOnMouseDragged(
        event -> {
          if (!event.isShiftDown() && !IS_MAKING_EDGE) {
            ((GesturePane) this.nodeRepresentation.getParent().getParent())
                .setGestureEnabled(false);
            this.getNodeX().setValue(event.getX());
            this.getNodeY().setValue(event.getY());
          }
        });
  }

  private void MakePopup() {
    if (this.popup == null) {
      /* Assuming the popup does not exist, build a new one from the factory */
      this.popup =
          PopupFactory.startBuild()
              .anchor(this.nodeRepresentation)
              .mapNode(this)
              .deletable()
              .editable()
              .closeEvent(
                  event -> {
                    this.RemovePopup();
                    this.allowTooltip = true;
                  })
              .build();
      /* Color the node on the map to represent selection */
      this.nodeRepresentation.setFill(Color.rgb(0xCC, 0x22, 0x22));
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
      this.nodeRepresentation.setFill(Color.rgb(0x01, 0x3A, 0x75));
      /* Allow this Node's tooltip to pop up */
      this.allowTooltip = true;
    }
  }
}
