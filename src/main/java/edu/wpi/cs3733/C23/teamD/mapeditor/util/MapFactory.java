package edu.wpi.cs3733.C23.teamD.mapeditor.util;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapEdge;
import edu.wpi.cs3733.C23.teamD.mapeditor.entities.MapNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class MapFactory {

  private static final GesturePane gesturePane = new GesturePane();
  private boolean onlyStartEnd;
  private boolean labelsShown = false;
  private ArrayList<MapNode> nodeList;
  private ArrayList<MapEdge> edgeList;
  private Function<Node, EventHandler<MouseEvent>> nodeEvent;
  private Function<Node, EventHandler<MouseEvent>> nodeMouseEnterEvent;
  private Function<Node, EventHandler<MouseEvent>> nodeMouseExitEvent;
  private boolean scaleMap = false;

  private boolean flipLabel = true;
  private AnchorPane holder;

  /** Creates a new MapFactory Object */
  private MapFactory() {
    this.onlyStartEnd = false;
    this.nodeList = new ArrayList<MapNode>();
    this.edgeList = new ArrayList<MapEdge>();
    this.nodeEvent =
        new Function<Node, EventHandler<MouseEvent>>() {
          @Override
          public EventHandler<MouseEvent> apply(Node node) {
            return null;
          }
        };
    this.nodeMouseEnterEvent =
        new Function<Node, EventHandler<MouseEvent>>() {
          @Override
          public EventHandler<MouseEvent> apply(Node node) {
            return null;
          }
        };
    this.nodeMouseExitEvent =
        new Function<Node, EventHandler<MouseEvent>>() {
          @Override
          public EventHandler<MouseEvent> apply(Node node) {
            return null;
          }
        };
  }

  /** @return a new MapFactory object */
  public static MapFactory startBuild() {
    MapFactory returnable = new MapFactory();
    return returnable;
  }

  /**
   * @param withNodes list of Nodes to add to the map
   * @return the MapFactory with these changes
   */
  public MapFactory withNodes(ArrayList<MapNode> withNodes) {
    this.nodeList = withNodes;
    return this;
  }

  /**
   * @param event a Function< Node, EventHandler< MouseEvent >> to apply to every Node
   * @return the MapFactory with these changes
   */
  public MapFactory withNodeFunctions(Function<Node, EventHandler<MouseEvent>> event) {
    this.nodeEvent = event;
    return this;
  }

  public MapFactory scaleMap() {
    this.scaleMap = true;
    return this;
  }

  public MapFactory withNodeMouseEnterFunctions(Function<Node, EventHandler<MouseEvent>> event) {
    this.nodeMouseEnterEvent = event;
    return this;
  }

  public MapFactory withNodeMouseExitFunctions(Function<Node, EventHandler<MouseEvent>> event) {
    this.nodeMouseExitEvent = event;
    return this;
  }

  public MapFactory setLabelsVisible(boolean labelsShown) {
    this.labelsShown = labelsShown;
    return this;
  }

  /**
   * @param edges a list of MapEdges to add to the Map
   * @return the MapFactory with these changes
   */
  public MapFactory withEdges(ArrayList<MapEdge> edges) {
    this.edgeList = edges;
    return this;
  }

  /**
   * Sets the map to be generated as only drawing the starting and ending Nodes
   *
   * @return the MapFactory with these changes
   */
  public MapFactory onlyStartEnd() {
    this.onlyStartEnd = true;
    return this;
  }

  /**
   * Builds the MapFactory settings into a map
   *
   * @return An array of GesturePanes representing each floor
   */
  public GesturePane build(int floor) {
    HashMap<String, Integer> converter = new HashMap<String, Integer>();
    int totalX = 0;
    int totalY = 0;
    int maxX = 0;
    int maxY = 0;
    int minX = 5000;
    int minY = 3000;
    int totalNode = 0;

    converter.put("G", 0);
    converter.put("L1", 1);
    converter.put("L2", 2);
    converter.put("1", 3);
    converter.put("2", 4);
    converter.put("3", 5);

    ImageView image = new ImageView();
    AnchorPane holder = new AnchorPane();

    if (floor == 0) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/00_thegroundfloor.png").toExternalForm());
    } else if (floor == 1) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/00_thelowerlevel1.png").toExternalForm());
    } else if (floor == 2) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/00_thelowerlevel2.png").toExternalForm());
    } else if (floor == 3) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/01_thefirstfloor.png").toExternalForm());
    } else if (floor == 4) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/02_thesecondfloor.png").toExternalForm());
    } else if (floor == 5) {
      image =
          new ImageView(
              App.class.getResource("views/floorMaps/03_thethirdfloor.png").toExternalForm());
    }

    holder.getChildren().add(image);

    for (MapEdge edge : edgeList) {
      if (converter.get(edge.getToNode().getNodeFloor().getValue()) == floor
          && converter.get(edge.getFromNode().getNodeFloor().getValue()) == floor)
        holder.getChildren().add(edge.getEdgeRepresentation());
    }

    if (!this.onlyStartEnd) {
      // For every Node
      for (MapNode node : nodeList) {

        if (converter.get(node.getNodeFloor().getValue()) != floor) {
          continue;
        }

        totalX += node.getNodeX().getValue();
        totalY += node.getNodeY().getValue();
        maxY = (int) Math.max(maxY, node.getNodeY().getValue());
        maxX = (int) Math.max(maxX, node.getNodeX().getValue());
        minY = (int) Math.min(minY, node.getNodeY().getValue());
        minX = (int) Math.min(minX, node.getNodeX().getValue());
        totalNode++;
        // Creates popup object

        holder.getChildren().add(node.getNodeRepresentation());

        if (!node.getNodeType().getValue().equals("HALL")) {
          final TextArea nodeLabel = new TextArea();
          nodeLabel.setEditable(false);
          nodeLabel.setVisible(labelsShown);
          nodeLabel.textProperty().bindBidirectional(node.getNodeLongName());
          nodeLabel.setFont(javafx.scene.text.Font.font("Nunito Sans", 5));
          Platform.runLater(
              () -> {
                nodeLabel.setPrefColumnCount(node.getNodeLongName().getValue().length() / 2);
                nodeLabel.setPrefRowCount(0);
                nodeLabel.setLayoutX(node.getNodeX().getValue());

                nodeLabel.setLayoutY(node.getNodeY().getValue() - 30);
                nodeLabel.setRotate(-30);
                nodeLabel.setWrapText(true);

                //                nodeLabel.setStyle(
                //                    "-fx-background-color:white;  -fx-background-insets: 1, 1; +
                // -fx-background-radius: 3, 2;");
                // nodeLabel.setCenterShape(true);
                //                nodeLabel.setBackground(
                //                    new Background(
                //                        new BackgroundFill(
                //                            Paint.valueOf("white"),
                //                            CornerRadii.EMPTY,
                //                            new Insets(
                //                                0,
                //                                (nodeLabel.getWidth() -
                // nodeLabel.getText().length() * 7) / 2,
                //                                0,
                //                                (nodeLabel.getWidth() -
                // nodeLabel.getText().length() * 7) / 2))));

                nodeLabel.toFront();
              });

          node.getNodeY()
              .addListener(
                  new ChangeListener<Number>() {
                    @Override
                    public void changed(
                        ObservableValue<? extends Number> observable,
                        Number oldValue,
                        Number newValue) {
                      nodeLabel.setLayoutY(newValue.doubleValue() - 30);
                    }
                  });
          node.getNodeX()
              .addListener(
                  new ChangeListener<Number>() {
                    @Override
                    public void changed(
                        ObservableValue<? extends Number> observable,
                        Number oldValue,
                        Number newValue) {
                      nodeLabel.setLayoutX(newValue.doubleValue());
                    }
                  });
          // nodeLabel.setAlignment(Pos.CENTER);
          // nodeLabel.setStyle("-fx-background-color: '#FFFFFF'; -fx-padding: 5");
          holder.getChildren().add(nodeLabel);
        }
      }
    } else {
      holder.getChildren().add(nodeList.get(0).getNodeRepresentation());
      holder.getChildren().add(nodeList.get(nodeList.size() - 1).getNodeRepresentation());
    }

    gesturePane.setContent(holder);
    gesturePane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);

    double scale = 0;
    double xAdjust = 0.7;

    if (scaleMap) {
      xAdjust = 1;
      if (minX != 5000) {
        double temp =
            (Math.max(
                        Math.max(
                            ((double) (maxX - minX)) / (App.getPrimaryStage().getWidth() * 0.5),
                            ((double) (maxY - minY)) / (App.getPrimaryStage().getWidth() * 0.5)),
                        0)
                    * 31)
                + 1;
        if (temp < 32) {
          scale = 5 - (Math.log(temp)) / Math.log(2);
        } else {
          scale = 0;
        }
      }

      gesturePane.zoomTo(scale, Point2D.ZERO);

      gesturePane
          .animate(Duration.millis(300))
          .centreOn(
              new Point2D(
                  ((minX + maxX) / 2
                      - App.getPrimaryStage().getWidth() * xAdjust * (Math.pow(2, (5 - scale))) / 32
                      - 50),
                  ((minY + maxY) / 2
                      - App.getPrimaryStage().getHeight() * Math.pow(2, (5 - scale)) / 32
                      - 50)));
    }

    // Return the GesturePane
    return gesturePane;
  }
}
