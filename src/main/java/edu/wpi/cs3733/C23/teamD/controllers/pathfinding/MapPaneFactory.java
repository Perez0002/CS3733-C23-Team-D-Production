package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MapPaneFactory {

  private int xPos;
  private int yPos;

  private int width;
  private int height;
  private EventHandler<MouseEvent> onClick;
  private EventHandler<MouseEvent> onMouseEnter;
  private EventHandler<MouseEvent> onMouseExit;
  private String style;
  private String paneID;

  public MapPaneFactory() {
    this.xPos = -1;
    this.yPos = -1;
    this.width = -1;
    this.height = -1;
    this.onClick = null;
    this.onMouseEnter = null;
    this.onMouseExit = null;
    this.style = "";
    this.paneID = "";
  }

  public MapPaneFactory posX(int xPos) {
    this.xPos = xPos;
    return this;
  }

  public MapPaneFactory posY(int yPos) {
    this.yPos = yPos;
    return this;
  }

  public MapPaneFactory pos(int xPos, int yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
    return this;
  }

  public MapPaneFactory width(int width) {
    this.width = width;
    return this;
  }

  public MapPaneFactory height(int height) {
    this.height = height;
    return this;
  }

  public MapPaneFactory size(int width, int height) {
    this.width = width;
    this.height = height;
    return this;
  }

  public MapPaneFactory onClick(EventHandler<MouseEvent> onClick) {
    this.onClick = onClick;
    return this;
  }

  public MapPaneFactory onMouseEnter(EventHandler<MouseEvent> onMouseEnter) {
    this.onMouseEnter = onMouseEnter;
    return this;
  }

  public MapPaneFactory onMouseExit(EventHandler<MouseEvent> onMouseExit) {
    this.onMouseExit = onMouseExit;
    return this;
  }

  public MapPaneFactory style(String style) {
    this.style = style;
    return this;
  }

  public MapPaneFactory paneID(String paneID) {
    this.paneID = paneID;
    return this;
  }

  public Pane build() {
    final Pane returnable = new Pane();
    returnable.setLayoutX(this.xPos);
    returnable.setLayoutY(this.yPos);
    returnable.setPrefSize(this.width, this.height);
    returnable.setOnMouseClicked(this.onClick);
    returnable.setOnMouseEntered(this.onMouseEnter);
    returnable.setOnMouseExited(this.onMouseExit);
    returnable.setStyle(this.style);
    returnable.setId(this.paneID);
    return returnable;
  }
}
