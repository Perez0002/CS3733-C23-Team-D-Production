package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.PopupFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.PopOver;

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
              PathEdge tempEdge = new PathEdge(FIRST_NODE.getNode(), this.getNode());
              tempEdge.setEdge(new Edge(FIRST_NODE.getNode().getNode(), this.getNode().getNode()));
              MapEdge tempMapEdge = new MapEdge(tempEdge);

              tempMapEdge.setNodes(FIRST_NODE, this);
              ((AnchorPane) this.nodeRepresentation.getParent())
                  .getChildren()
                  .add(1, tempMapEdge.getEdgeRepresentation());

              try {
                FDdb.getInstance().saveEdge(tempEdge.getEdge());
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
            this.refreshTooltip();
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
            BorderPane borderPane = ((BorderPane) gesturePane.getParent());

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

            /* If mouse goes past specific bounds, fail to place past map */
            if (event.getSceneX() < gesturePaneStartPoint.getX()
                || event.getSceneX() > gesturePaneEndPoint.getX()
                || event.getSceneY() < gesturePaneStartPoint.getY()
                || event.getSceneY() > gesturePaneEndPoint.getY()) {
              this.getNodeX().setValue(this.oldX.getValue());
              this.getNodeY().setValue(this.oldY.getValue());
            }
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

            AnchorPane holder = (AnchorPane) this.nodeRepresentation.getParent();
            GesturePane gesturePane =
                ((GesturePane) this.nodeRepresentation.getParent().getParent());
            BorderPane borderPane = ((BorderPane) gesturePane.getParent());
            gesturePane.setGestureEnabled(false);

            /* Move node with mouse */
            if (event.getX() < holder.getWidth() - 16 && event.getX() > 16) {
              this.getNodeX().setValue(event.getX());
            }
            if (event.getY() < holder.getHeight() - 16 && event.getY() > 16) {
              this.getNodeY().setValue(event.getY());
            }

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

  public void MakePopup() {
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
              .deleteEvent(
                  event -> {
                    this.deleteNode();
                  })
              .submitEvent(
                  event -> {
                    this.saveNode();
                  })
              .build();
      popup.setHideOnEscape(false);
      /* Color the node on the map to represent selection */
      this.nodeRepresentation.setFill(this.SELECTED);
      /* Prevent this Node's tooltip from popping up */
      this.allowTooltip = false;
    }
  }

  public void MakePopup(boolean adding) {
    if (this.popup == null && adding) {
      /* Assuming the popup does not exist, build a new one from the factory */
      this.popup =
          PopupFactory.startBuild()
              .anchor(this.nodeRepresentation)
              .mapNode(this)
              .editable()
              .closeEvent(
                  event -> {
                    this.RemovePopup();
                    ((AnchorPane) this.nodeRepresentation.getParent())
                        .getChildren()
                        .remove(this.nodeRepresentation);
                    this.allowTooltip = true;
                  })
              .submitEvent(
                  event -> {
                    this.addNode();
                  })
              .build();
      /* Color the node on the map to represent selection */
      this.nodeRepresentation.setFill(this.SELECTED);
      /* Prevent this Node's tooltip from popping up */
      this.allowTooltip = false;
    }
  }

  public void RemovePopup() {
    if (popup != null) {
      /* Assuming the popup exists, hide and then remove it to save VRam space */
      popup.hide();
      popup = null;
      /* Set the color of the Node on the map to represent deselection */
      this.nodeRepresentation.setFill(this.NO_SELECTION);
      /* Allow this Node's tooltip to pop up */
      this.allowTooltip = true;

      if (this.oldX.getValue() != -1 && this.oldY.getValue() != -1) {
        this.getNodeX().setValue(this.oldX.getValue());
        this.getNodeY().setValue(this.oldY.getValue());
      }

      this.oldX.setValue(-1);
      this.oldY.setValue(-1);
    }
  }

  public void addNode() {
    this.addNodeToMap(this);
  }

  public void deleteNode() {
    this.RemovePopup();
    /* Making Warning */
    // TODO make this look nicer
    final PopOver warning = new PopOver();
    warning.setArrowSize(0);
    warning.centerOnScreen();
    MFXButton yesButton = new MFXButton();
    yesButton.getStyleClass().add("submitButton");
    MFXButton noButton = new MFXButton();
    noButton.getStyleClass().add("cancelButton");
    HBox buttonHolder = new HBox(yesButton, noButton);
    buttonHolder.setAlignment(Pos.CENTER);
    Text prompt = new Text("Do you want to auto repair edges?");
    warning.setContentNode(new VBox(prompt, buttonHolder));
    warning.show(App.getPrimaryStage());
    /* End Making Warning */

    yesButton.setText("Yes");
    noButton.setText("No");

    yesButton.setOnAction(
        evt -> {
          this.deleteNodeFixEdges(this);

          warning.hide();
        });

    noButton.setOnAction(
        evt -> {
          this.deleteNodeVoidEdges(this);
          warning.hide();
        });
  }

  public void saveNode() {
    this.oldX.setValue(-1);
    this.oldY.setValue(-1);

    this.RemovePopup();

    this.saveNodeChanges(this);
  }

  public void saveNodeChanges(MapNode node) {

    String oldNodeID = node.getNode().getNode().getNodeID();

    node.getNode().getNode().setXcoord(node.getNodeX().getValue().intValue());
    node.getNode().getNode().setYcoord(node.getNodeY().getValue().intValue());
    node.getNode().getNode().setFloor(node.getNodeFloor().getValue());
    node.getNode().getNode().setBuilding(node.getNodeBuilding().getValue());

    ArrayList<Object> updateList = new ArrayList<>();

    try {
      updateList = FDdb.getInstance().updateNodePK(node.getNode().getNode());
    } catch (Exception e) {
      e.printStackTrace();
    }

    for (Object changeable : updateList) {
      if (changeable instanceof Node) {
        Node nodeInstance = (Node) changeable;

        node.getNode().setNode(nodeInstance);
      }

      if (changeable instanceof Edge) {
        Edge edgeInstance = (Edge) changeable;

        for (MapEdge edge : node.getMapEdgeList()) {
          if (edge.getEdge().getToNode().getNode().getNodeID().equals(oldNodeID)) {
            if (edgeInstance
                .getFromNodeID()
                .equals(edge.getEdge().getFromNode().getNode().getNodeID())) {
              edge.getEdge().setEdge(edgeInstance);
            }
          }

          if (edge.getEdge().getFromNode().getNode().getNodeID().equals(oldNodeID)) {
            if (edgeInstance
                .getToNodeID()
                .equals(edge.getEdge().getToNode().getNode().getNodeID())) {
              edge.getEdge().setEdge(edgeInstance);
            }
          }
        }
      }

      if (changeable instanceof Move) {
        Move moveInstance = (Move) changeable;

        node.getNode().setLocation(moveInstance.getLocation());
      }
    }
  }

  public void addNodeToMap(MapNode node) {
    Node newNode = node.getNode().getNode();
    LocationName loc = node.getNode().getLocation();

    FDdb.getInstance().saveNode(newNode);
    FDdb.getInstance().saveLocationName(loc);
  }

  private void deleteNodeFixEdges(MapNode node) {

    // Save old node ID
    String oldNodeID = node.getNode().getNode().getNodeID();

    // Remove old Edges Representation
    ArrayList<MapNode> nodesToModify = new ArrayList<>();
    for (MapEdge edge : node.getMapEdgeList()) {

      if (edge.getToNode().getNode().getNode().getNodeID().equals(oldNodeID)) {
        nodesToModify.add(edge.getFromNode());
      }
      if (edge.getFromNode().getNode().getNode().getNodeID().equals(oldNodeID)) {
        nodesToModify.add(edge.getToNode());
      }

      if (edge.getEdgeRepresentation().getParent() != null) {
        ((AnchorPane) edge.getEdgeRepresentation().getParent())
            .getChildren()
            .remove(edge.getEdgeRepresentation());
      }
    }

    // Removing Edges from nodes connected to initial node
    for (MapNode n : nodesToModify) {
      MapEdge edgeToRemove = n.getMapEdgeList().get(0);
      for (MapEdge edge : n.getMapEdgeList()) {
        if (edge.getEdge().getToNode().getNode().getNodeID().equals(oldNodeID)) {
          edgeToRemove = edge;
        }
      }
      n.getMapEdgeList().remove(edgeToRemove);
    }

    // Delete old Edges
    for (MapEdge edge : node.getMapEdgeList()) {
      try {
        FDdb.getInstance().deleteEdge(edge.getEdge().getEdge());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // Delete old Node
    try {
      FDdb.getInstance().deleteNode(node.getNode().getNode());
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(nodesToModify.size());
    // Create new patched Edges
    for (int i = 0; i < nodesToModify.size(); i++) {
      for (int c = i + 1; c < nodesToModify.size(); c++) {
        Edge newEdge =
            new Edge(
                nodesToModify.get(i).getNode().getNode(), nodesToModify.get(c).getNode().getNode());
        if (!nodesToModify.get(i).equals(nodesToModify.get(c))
            && !FDdb.getInstance().getAllEdges().contains(newEdge)) {
          PathEdge newPathEdge =
              new PathEdge(nodesToModify.get(i).getNode(), nodesToModify.get(c).getNode());
          MapEdge newMapEdge = new MapEdge(newPathEdge);
          newMapEdge.setNodes(nodesToModify.get(i), nodesToModify.get(c));
          try {
            FDdb.getInstance().saveEdge(newEdge);
          } catch (Exception e) {
            e.printStackTrace();
          }

          ((AnchorPane) node.getNodeRepresentation().getParent())
              .getChildren()
              .add(newMapEdge.getEdgeRepresentation());
        }
      }
    }

    // Place new Patched MapEdges on map
    ((AnchorPane) node.getNodeRepresentation().getParent())
        .getChildren()
        .remove(node.getNodeRepresentation());
  }

  private void deleteNodeVoidEdges(MapNode node) {
    // Save old node ID
    String oldNodeID = node.getNode().getNode().getNodeID();

    // Remove old Edges Representation
    ArrayList<MapNode> nodesToModify = new ArrayList<>();
    for (MapEdge edge : node.getMapEdgeList()) {
      if (edge.getFromNode().getNode().getNode().getNodeID().equals(oldNodeID)) {
        nodesToModify.add(edge.getToNode());
      }

      ((AnchorPane) edge.getEdgeRepresentation().getParent())
          .getChildren()
          .remove(edge.getEdgeRepresentation());
    }

    // Removing Edges from nodes connected to initial node
    for (MapNode n : nodesToModify) {
      MapEdge edgeToRemove = n.getMapEdgeList().get(0);
      for (MapEdge edge : n.getMapEdgeList()) {
        if (edge.getEdge().getToNode().getNode().getNodeID().equals(oldNodeID)) {
          edgeToRemove = edge;
        }
      }
      n.getMapEdgeList().remove(edgeToRemove);
    }

    // Delete old Edges
    for (MapEdge edge : node.getMapEdgeList()) {
      try {
        FDdb.getInstance().deleteEdge(edge.getEdge().getEdge());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // Delete old Node
    try {
      FDdb.getInstance().deleteNode(node.getNode().getNode());
    } catch (Exception e) {
      e.printStackTrace();
    }

    ((AnchorPane) node.getNodeRepresentation().getParent())
        .getChildren()
        .remove(node.getNodeRepresentation());
  }

  private void addEdge(MapEdge edge) {
    FDdb.getInstance().saveEdge(edge.getEdge().getEdge());
  }
}
