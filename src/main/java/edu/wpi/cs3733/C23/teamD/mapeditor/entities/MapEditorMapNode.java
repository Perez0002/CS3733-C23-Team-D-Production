package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.PopupFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class MapEditorMapNode extends MapNode {

  private static MapEditorMapNode FIRST_NODE = null;
  private static boolean IS_MAKING_EDGE = false;
  private static Line CURRENT_EDGE = null;
  private DoubleProperty oldX = new SimpleDoubleProperty(-1);
  private DoubleProperty oldY = new SimpleDoubleProperty(-1);
  protected final Color EDGE_CREATION = Color.rgb(0x00, 0xFF, 0x00);

  public MapEditorMapNode(PathNode node) {
    /* Superclass object */
    super(node);

    nodeRepresentation.setOnMouseClicked(
        event -> {
          /* Creates popup on mouse click, assuming an edge is not being made and shift is not being pressed */
          if (!event.isShiftDown() && !IS_MAKING_EDGE) {
            this.MakePopup();
          }

          /* If shift is being pressed... */
          if (event.isShiftDown()) {

            /* ...deselect the first node selected assuming that it was the node that was clicked  */
            if (IS_MAKING_EDGE && FIRST_NODE == this) {
              ((AnchorPane) this.nodeRepresentation.getParent()).getChildren().remove(CURRENT_EDGE);
              FIRST_NODE.nodeRepresentation.setFill(this.NO_SELECTION);
              IS_MAKING_EDGE = false;
              CURRENT_EDGE = null;
              FIRST_NODE = null;
              return;
            }

            /* ...create an edge between first selected node and this node assuming this node is not first node  */
            if (IS_MAKING_EDGE && FIRST_NODE != this) {
              ((AnchorPane) this.nodeRepresentation.getParent()).getChildren().remove(CURRENT_EDGE);
              MapEdge tempEdge = new MapEdge(new PathEdge(FIRST_NODE.getNode(), this.getNode()));
              tempEdge.setNodes(FIRST_NODE, this);
              ((AnchorPane) this.nodeRepresentation.getParent())
                  .getChildren()
                  .add(1, tempEdge.getEdgeRepresentation());

              try {
                FDdb.getInstance()
                    .saveEdge(
                        new Edge(
                            tempEdge.getEdge().getFromNode().getNode(),
                            tempEdge.getEdge().getToNode().getNode()));
              } catch (Exception e) {
                e.printStackTrace();
              }

              this.nodeRepresentation.setFill(this.NO_SELECTION);
              FIRST_NODE.nodeRepresentation.setFill(this.NO_SELECTION);
              IS_MAKING_EDGE = false;
              CURRENT_EDGE = null;
              FIRST_NODE = null;
              return;
            }

            /* ...if we are not making an edge set this node as the first node and initialize the line  */
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
              this.nodeRepresentation.setFill(this.EDGE_CREATION);
              return;
            }
          }
        });

    nodeRepresentation.setOnMouseEntered(
        event -> {
          /* If tooltips are allowed, make a tooltip */
          if (this.allowTooltip) {
            Tooltip.install(nodeRepresentation, this.tooltip);
          }

          /* If we are making an edge, preview the edge */
          if (IS_MAKING_EDGE && FIRST_NODE != this) {
            CURRENT_EDGE.endXProperty().bindBidirectional(this.getNodeX());
            CURRENT_EDGE.endYProperty().bindBidirectional(this.getNodeY());
            this.nodeRepresentation.setFill(this.EDGE_CREATION);
          }
        });

    nodeRepresentation.setOnMouseExited(
        event -> {
          /* Remove tooltip */
          Tooltip.uninstall(nodeRepresentation, this.tooltip);

          /* Remove the line preview */
          if (IS_MAKING_EDGE && FIRST_NODE != this) {
            CURRENT_EDGE.endXProperty().unbindBidirectional(this.getNodeX());
            CURRENT_EDGE.endYProperty().unbindBidirectional(this.getNodeY());

            CURRENT_EDGE.endXProperty().setValue(FIRST_NODE.getNodeX().getValue());
            CURRENT_EDGE.endYProperty().setValue(FIRST_NODE.getNodeY().getValue());
            this.nodeRepresentation.setFill(this.NO_SELECTION);
          }
        });

    nodeRepresentation.setOnMouseReleased(
        event -> {
          /* If the mouse moved since it was pressed */
          if (!event.isStillSincePress()) {
            /* Allow the GesturePane to be moved again */
            GesturePane gesturePane =
                ((GesturePane) this.nodeRepresentation.getParent().getParent());
            gesturePane.setGestureEnabled(true);
          }
        });

    nodeRepresentation.setOnMouseDragged(
        event -> {
          if (!event.isShiftDown() && !IS_MAKING_EDGE) {

            /* If oldX and oldY are not set, set them */
            if (oldX.getValue() == -1 && oldY.getValue() == -1) {
              oldX.setValue(this.getNodeX().getValue());
              oldY.setValue(this.getNodeY().getValue());
            }

            GesturePane gesturePane =
                ((GesturePane) this.nodeRepresentation.getParent().getParent());
            BorderPane borderPane = ((BorderPane) gesturePane.getParent());
            gesturePane.setGestureEnabled(false);
            /* Move node with mouse */
            this.getNodeX().setValue(event.getX());
            this.getNodeY().setValue(event.getY());

            Point2D gesturePaneStartPoint =
                new Point2D(
                    borderPane.getLayoutX()
                        + App.getRootPane().getLeft().getLayoutBounds().getWidth(),
                    borderPane.getLayoutY()
                        + App.getRootPane().getLeft().getLayoutBounds().getWidth());
            Point2D gesturePaneEndPoint =
                new Point2D(
                    gesturePaneStartPoint.getX() + gesturePane.getViewportBound().getWidth(),
                    gesturePaneStartPoint.getY() + gesturePane.getViewportBound().getHeight());
            double triggerX = gesturePane.getViewportBound().getWidth() * 0.1;
            double triggerY = gesturePane.getViewportBound().getWidth() * 0.1;

            double centerPointX = gesturePane.targetPointAtViewportCentre().getX();
            double centerPointY = gesturePane.targetPointAtViewportCentre().getY();
            /* If mouse goes past specific bounds, scroll the GesturePane to compensate */
            if (event.getSceneX() < gesturePaneStartPoint.getX() + triggerX) {
              centerPointX = (gesturePane.targetPointAtViewportCentre().getX() - triggerX);
            }

            /* If mouse goes past specific bounds, scroll the GesturePane to compensate */
            if (event.getSceneX() > gesturePaneEndPoint.getX() - triggerX) {
              centerPointX = (gesturePane.targetPointAtViewportCentre().getX() + triggerX);
            }

            /* If mouse goes past specific bounds, scroll the GesturePane to compensate */
            if (event.getSceneY() < gesturePaneStartPoint.getY() + triggerY) {
              centerPointY = (gesturePane.targetPointAtViewportCentre().getY() - triggerY);
            }

            /* If mouse goes past specific bounds, scroll the GesturePane to compensate */
            if (event.getSceneY() > gesturePaneEndPoint.getY() - triggerY) {
              centerPointY = (gesturePane.targetPointAtViewportCentre().getY() + triggerY);
            }

            /* Actually adjust the GesturePane */
            gesturePane
                .animate(Duration.millis(50))
                .centreOn(new Point2D(centerPointX, centerPointY));
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
      this.nodeRepresentation.setFill(this.SELECTED);
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
      this.nodeRepresentation.setFill(this.NO_SELECTION);
      /* Allow this Node's tooltip to pop up */
      this.allowTooltip = true;

      if(this.oldX.getValue() != -1 && this.oldY.getValue() != -1)
      {
        this.getNodeX().setValue(this.oldX.getValue());
        this.getNodeY().setValue(this.oldY.getValue());
      }

      this.oldX.setValue(-1);
      this.oldY.setValue(-1);
    }
  }
}
