package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.entities.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class MapEditorNodeController {

  /* Attributes */
  Node node; // contains pathfinding node information
  PopOver popover; // contains popover object
  javafx.scene.Node anchor;
  MFXButton button;

  MapEditorNodeController(Node node, double xPos, double yPos, javafx.scene.Node anchor) {
    this.node = node;
    this.anchor = anchor;
    makeEditorNode(xPos, yPos); // calls pseudo-constructor object
  }

  /**
   * Creates a popover object that contains necessary popup information (pane, text containing node
   * information). neither param nor return
   */
  private void makeEditorNode(double xPos, double yPos) {
    popover = new PopOver(); // creates PopOver container
    VBox pane = new VBox(); // creates Pane object to place within PopOver
    button = new MFXButton();
    button.setText("Close");
    button.setPrefWidth(200);
    // Start format PopOver container
    pane.setStyle("-fx-background-color: '#ffffff';");
    pane.setPrefHeight(100);
    pane.setPrefWidth(200);
    popover.setPrefHeight(100);
    popover.setPrefWidth(200);
    popover.setCloseButtonEnabled(false);
    popover.setAutoHide(false);
    // End format PopOver container

    if (node != null) { // connection to database is intact
      popover.setTitle(String.format("%s Information", node.getLongName()));
      Text nodeData =
          new Text(
              String.format(
                  "\n  xCoord: %d \n  yCoord: %d \n  floor: %s \n  shortName: %s \n  longName: %s ",
                  node.getXcoord(),
                  node.getYcoord(),
                  node.getFloor(),
                  node.getShortName(),
                  node.getLongName()));
      nodeData.setStyle("-fx-font-family: OpenSans");

      pane.getChildren().add(nodeData);
      pane.getChildren().add(button);
    } else { // connection to database is null (test case)
      popover.setTitle(String.format("%s Information", "nodeLongName"));
      Text nodeData =
          new Text(
              String.format(
                  "\n  xCoord: %d \n  yCoord: %d \n  floor: %d \n  shortName: %s \n  longName: %s ",
                  -10, -10, -10, "getShortName", "getLongName"));
      nodeData.setStyle("-fx-font-family: OpenSans");
      pane.getChildren().add(nodeData); // adds text to pane
    }

    popover.setHeaderAlwaysVisible(true);

    popover.setContentNode(pane); // sets pane as content of popover
  } // end makeEditorNode class

  void makePopupAppear() {
    popover.show(anchor); // shows Popup
  }

  void makePopupDisappear() {
    popover.hide(); // shows popup object
  }

  void setOnClose(EventHandler<ActionEvent> event) {
    button.setOnAction(event);
  }

  public void initialize() {}
} // end class
