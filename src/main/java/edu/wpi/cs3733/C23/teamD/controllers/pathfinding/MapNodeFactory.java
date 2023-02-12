package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MapNodeFactory
{

  private int xPos;
  private int yPos;
  private int radius;
  private EventHandler<MouseEvent> onClick;
  private EventHandler<MouseEvent> onMouseEnter;
  private EventHandler<MouseEvent> onMouseExit;
  private Color color;
  private String nodeID;

  /** Creates a new MapNodeFactory Object */
  private MapNodeFactory() {
    this.xPos = -1;
    this.yPos = -1;
    this.radius = -1;
    this.onClick = null;
    this.onMouseEnter = null;
    this.onMouseExit = null;
    this.color = null;
    this.nodeID = "";
  }

  /**
   * Begins the building process
   * @return A MapNodeFactory to chain off of
   */
  public static MapNodeFactory startBuild() {
    final MapNodeFactory returnable = new MapNodeFactory();
    return returnable;
  }

  /**
   * Begins the building process with a preset for PathNode representation
   * @return A MapNodeFactory to chain off of
   */
  public static MapNodeFactory startPathBuild() {
    final MapNodeFactory returnable = new MapNodeFactory();
    returnable.color(Color.rgb(01, 58, 117)).radius(10);
    return returnable;
  }

  /**
   * @param xPos X Position of the center of the Pane
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory posX(int xPos) {
    this.xPos = xPos;
    return this;
  }

  /**
   * @param yPos Y Position of the center of the Pane
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory posY(int yPos) {
    this.yPos = yPos;
    return this;
  }

  /**
   * @param xPos X Position of the center of the Pane
   * @param yPos Y Position of the center of the Pane
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory pos(int xPos, int yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
    return this;
  }

  /**
   *
   * @param radius The radius for the MapNode to be
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory radius(int radius) {
    this.radius = radius;
    return this;
  }

  /**
   *
   * @param onClick An event to be run when the MapNode is clicked
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory onClick(EventHandler<MouseEvent> onClick) {
    this.onClick = onClick;
    return this;
  }

  /**
   *
   * @param onMouseEnter An event to be run when the mouse enters the MapNode
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory onMouseEnter(EventHandler<MouseEvent> onMouseEnter) {
    this.onMouseEnter = onMouseEnter;
    return this;
  }

  /**
   *
   * @param onMouseExit An event to be run when the mouse exits the MapNode
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory onMouseExit(EventHandler<MouseEvent> onMouseExit) {
    this.onMouseExit = onMouseExit;
    return this;
  }

  /**
   *
   * @param color The Color to set the MapNode to
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory color(Color color) {
    this.color = color;
    return this;
  }

  /**
   *
   * @param nodeID an ID to set the MapNode to
   * @return a MapNodeFactory with these settings
   */
  public MapNodeFactory nodeID(String nodeID) {
    this.nodeID = nodeID;
    return this;
  }

  /**
   * Finishes the building process and makes a new Node object
   * @return the Circle corresponding to the settings given to the MapNodeFactory
   */
  public Node build() {
    final Circle returnable = new Circle();
    returnable.setCenterX(this.xPos);
    returnable.setCenterY(this.yPos);
    returnable.setRadius(this.radius);
    returnable.setOnMouseClicked(this.onClick);
    returnable.setOnMouseEntered(this.onMouseEnter);
    returnable.setOnMouseExited(this.onMouseExit);
    returnable.setFill(this.color);
    returnable.setId(this.nodeID);
    return returnable;
  }
}
