package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
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

  private void makeEditorNode() {
    PopOver popover = new PopOver();
    FXMLLoader loader = new FXMLLoader(App.class.getResource("views/MapEditorPopup.fxml"));
    popover.setContentNode(mapEditorPane);
    popover.show(App.getPrimaryStage());
  }

  public void initialize() {}

  public void testButtonFunction() {}
}
