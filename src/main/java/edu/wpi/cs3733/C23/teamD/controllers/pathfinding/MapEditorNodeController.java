package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class MapEditorNodeController {

  /* Attributes */
  Node node; // contains pathfinding node information

  MapEditorNodeController(Node node) {
    makeEditorNode();
  }


  /**
   * Creates a popover object that contains necessary popup information
   * (pane, text containing node information).
   * neither param nor return
   */
  // TODO: make this happen during node hover, not node click
  private void makeEditorNode() {
    PopOver popover = new PopOver(); // creates PopOver container
    Pane pane = new Pane(); // creates Pane object to place within PopOver

    // Start format PopOver container
    pane.setStyle("-fx-background-color: '#ffffff';");
    popover.setPrefHeight(100);
    popover.setPrefWidth(200);
    pane.setPrefHeight(100);
    pane.setPrefWidth(200);
    popover.setArrowSize(0);
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

    popover.setContentNode(pane); // sets pane as content of popover
    popover.show(App.getPrimaryStage()); // shows popover on primary stage
  }

  public void initialize() {
  }
} // end class

