package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.createJavaNodes;
import static edu.wpi.cs3733.C23.teamD.Ddb.makeConnection;

import edu.wpi.cs3733.C23.teamD.entities.Node;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MapEditorPageController {

  @FXML private BorderPane mapEditorPane;

  @FXML
  public void initialize() {
    MapDrawController mapDrawer = new MapDrawController();
    Connection conn = makeConnection();
    ArrayList<Node> nodeList = createJavaNodes(conn);
    mapEditorPane.setRight(mapDrawer.genMapFromNodes(nodeList));
  }
}
