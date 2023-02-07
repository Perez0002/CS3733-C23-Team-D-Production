package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class MapEditorNodeController {

  Node node;
  int y;
  int x;
  @FXML Pane mapEditorPane;

  MapEditorNodeController(Node node) {
    node = node;
    x = node.getXcoord();
    y = node.getYcoord();
    makeEditorNode();
  }

  // TODO: make this happen during node hover, not node click
  private void makeEditorNode() {
    PopOver popover = new PopOver();
    Pane pane = new Pane();
    pane.setStyle("-fx-background-color: '#ffffff';");
    popover.setPrefHeight(100);
    popover.setPrefWidth(200);
    pane.setPrefHeight(100);
    pane.setPrefWidth(200);
    popover.setArrowSize(0);

    // REMOVE after database connection
    if (node != null) {
      popover.setTitle(String.format("%s Information", node.getLongName()));
      Text nodeData =
          new Text(
              String.format(
                  "\n  xCoord: %d \n  yCoord: %d \n  floor: %d \n  shortName: %s \n  longName: %s ",
                  node.getXcoord(),
                  node.getYcoord(),
                  node.getFloor(),
                  node.getShortName(),
                  node.getLongName()));
      nodeData.setStyle("-fx-font-family: OpenSans");
      pane.getChildren().add(nodeData);
    } else {
      popover.setTitle(String.format("%s Information", "nodeLongName"));
      Text nodeData =
          new Text(
              String.format(
                  "\n  xCoord: %d \n  yCoord: %d \n  floor: %d \n  shortName: %s \n  longName: %s ",
                  -10, -10, -10, "getShortName", "getLongName"));
      nodeData.setStyle("-fx-font-family: OpenSans");
      pane.getChildren().add(nodeData);
    }

    popover.setContentNode(pane);
    popover.show(App.getPrimaryStage());
  }

  public void initialize() {}

  public void testButtonFunction() {}
}
