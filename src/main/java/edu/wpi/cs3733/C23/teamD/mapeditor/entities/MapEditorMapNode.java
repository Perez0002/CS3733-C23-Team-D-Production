package edu.wpi.cs3733.C23.teamD.mapeditor.entities;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.mapeditor.util.PopupFactory;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathEdge;
import edu.wpi.cs3733.C23.teamD.pathfinding.entities.PathNode;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
  private BooleanProperty nodeMode;
  private BooleanProperty edgeMode;
  protected final Color EDGE_CREATION = Color.rgb(0x00, 0xFF, 0x00);

  public MapEditorMapNode(PathNode node, BooleanProperty nodeLink, BooleanProperty edgeLink) {
    /* Superclass object */
    super(node);

    nodeMode = new SimpleBooleanProperty();
    nodeMode.setValue(true);
    edgeMode = new SimpleBooleanProperty();
    edgeMode.setValue(false);

    nodeMode.bindBidirectional(nodeLink);
    edgeMode.bindBidirectional(edgeLink);

    nodeRepresentation.setOnMouseClicked(
        event -> {
          /* Creates popup on mouse click, assuming an edge is not being made and editing nodes */
          if (nodeMode.getValue() && !IS_MAKING_EDGE) {
            this.MakePopup();
          }

          /* ...deselect the first node selected assuming that it was the node that was clicked  */
          if (IS_MAKING_EDGE && FIRST_NODE == this) {
            ((AnchorPane) this.nodeRepresentation.getParent()).getChildren().remove(CURRENT_EDGE);
            FIRST_NODE.nodeRepresentation.setFill(this.NO_SELECTION);
            IS_MAKING_EDGE = false;
            CURRENT_EDGE = null;
            FIRST_NODE = null;
            return;
          }

          /* If editing edges... */
          if (edgeMode.getValue()) {

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
          if (edgeMode.getValue() && IS_MAKING_EDGE && FIRST_NODE != this) {
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
          if (nodeMode.getValue() && !IS_MAKING_EDGE) {

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

  private static void autoRepairEdges(MapNode node) {
    PathNode n = node.getNode();
    ArrayList<MapNode> nodeList = new ArrayList<>();
    ArrayList<MapEdge> edgeList = node.getMapEdgeList();
    System.out.println(edgeList.size());

    for (MapEdge edge : node.getMapEdgeList()) {
      if (!edge.getToNode().equals(node)) {
        edge.getToNode().getMapEdgeList().remove(edge);
      } else if (!edge.getFromNode().equals(node)) {
        edge.getFromNode().getMapEdgeList().remove(edge);
      }
    }

    for (MapEdge e : edgeList) {
      if (e.getEdge().getEdge().getToNode().nodeEquals(n.getNode())) {
        nodeList.add(e.getFromNode());
      } else if (e.getEdge().getEdge().getFromNode().nodeEquals(n.getNode())) {
        nodeList.add(e.getToNode());
      }
    }

    for (int i = 0; i < nodeList.size(); i++) {
      for (int j = i + 1; j < nodeList.size(); j++) {
        Edge temp =
            new Edge(nodeList.get(i).getNode().getNode(), nodeList.get(j).getNode().getNode());
        PathEdge tempPathEdge = new PathEdge(nodeList.get(i).getNode(), nodeList.get(j).getNode());
        MapEdge tempMapEdge = new MapEdge(tempPathEdge);
        tempMapEdge.setNodes(nodeList.get(i), nodeList.get(j));
        ((AnchorPane) node.getNodeRepresentation().getParent())
            .getChildren()
            .add(1, tempMapEdge.getEdgeRepresentation());
        FDdb.getInstance().saveEdge(temp);
      }
    }
    FDdb.getInstance().deleteNode(n.getNode());
  }

  public void addNode() {
    if (this.getNodeX() != null
        && this.getNodeY() != null
        && !this.getNodeFloor().isEmpty().getValue()
        && !this.getNodeBuilding().isEmpty().getValue()) {
      // TODO proceed with node save
      Node node =
          new Node(
              this.getNodeX().intValue(),
              this.getNodeY().intValue(),
              this.getNodeFloor().getValue(),
              this.getNodeBuilding().getValue());
      if (!FDdb.getInstance().getAllNodes().contains(node)) {
        FDdb.getInstance().saveNode(node);
      }
    }

    if (!this.getNodeLongName().isEmpty().getValue()
        && !this.getNodeShortName().isEmpty().getValue()
        && !this.getNodeType().isEmpty().getValue()) {
      LocationName locationName =
          new LocationName(
              this.getNodeLongName().getValue(),
              this.getNodeShortName().getValue(),
              this.getNodeType().getValue());
      if (!FDdb.getInstance().getAllLocationNames().contains(locationName)) {
        FDdb.getInstance().saveLocationName(locationName);
      } else {
        FDdb.getInstance().updateLocationName(locationName);
      }
    }
    // Save move
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
          try {
            this.nodeRepresentation.setRadius(0);
            autoRepairEdges(this);
            for (MapEdge edge : this.getMapEdgeList()) {
              ((AnchorPane) edge.getEdgeRepresentation().getParent())
                  .getChildren()
                  .remove(edge.getEdgeRepresentation());
            }
            ((AnchorPane) this.nodeRepresentation.getParent())
                .getChildren()
                .remove(this.nodeRepresentation);

          } catch (Exception e) {
            e.printStackTrace();
          }

          warning.hide();
        });

    noButton.setOnAction(
        evt -> {
          for (MapEdge edge : this.getMapEdgeList()) {
            ((AnchorPane) edge.getEdgeRepresentation().getParent())
                .getChildren()
                .remove(edge.getEdgeRepresentation());
            try {
              FDdb.getInstance().deleteEdge(edge.getEdge().getEdge());
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

          Node n = this.getNode().getNode();

          try {
            FDdb.getInstance().deleteNode(n);
          } catch (Exception e) {
            e.printStackTrace();
          }
          ((AnchorPane) this.nodeRepresentation.getParent())
              .getChildren()
              .remove(this.nodeRepresentation);
          warning.hide();
        });
  }

  public void saveNode() {
    this.oldX.setValue(-1);
    this.oldY.setValue(-1);

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
    Text prompt = new Text("Do you want to keep edges?");
    warning.setContentNode(new VBox(prompt, buttonHolder));
    warning.show(App.getPrimaryStage());
    /* End Making Warning */

    yesButton.setText("Yes");
    noButton.setText("No");

    yesButton.setOnAction(
        evt -> {
          System.out.println(
              this.getNode().getNode().getXcoord() + ", " + this.getNode().getNode().getYcoord());
          Node n = new Node(this.getNode().getNode());
          n.setXcoord(this.getNodeX().getValue().intValue());
          n.setYcoord(this.getNodeY().getValue().intValue());
          n.setFloor(this.getNodeFloor().getValue());
          n.setBuilding(this.getNodeBuilding().getValue());
          System.out.println(n.getXcoord() + ", " + n.getYcoord());

          try {
            FDdb.getInstance().updateNodePK(n);
          } catch (Exception e) {
            e.printStackTrace();
          }
          warning.hide();
        });

    noButton.setOnAction(
        evt -> {
          for (MapEdge edge : this.getMapEdgeList()) {
            ((AnchorPane) edge.getEdgeRepresentation().getParent())
                .getChildren()
                .remove(edge.getEdgeRepresentation());
            try {
              FDdb.getInstance().deleteEdge(edge.getEdge().getEdge());
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

          Node n = new Node(this.getNode().getNode());
          n.setXcoord(this.getNodeX().getValue().intValue());
          n.setYcoord(this.getNodeY().getValue().intValue());
          n.setFloor(this.getNodeFloor().getValue());
          n.setBuilding(this.getNodeBuilding().getValue());

          try {
            FDdb.getInstance().updateNodePK(n);
          } catch (Exception e) {
            e.printStackTrace();
          }
          warning.hide();
        });
  }
}
