package edu.wpi.cs3733.C23.teamD.controllers.pathfinding;

import static edu.wpi.cs3733.C23.teamD.Ddb.createJavaNodes;
import static edu.wpi.cs3733.C23.teamD.Ddb.makeConnection;

import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import java.sql.Connection;
import java.util.ArrayList;

public class MapEditorController {

  public MapEditorController() {
    MapDrawController mapDrawer = new MapDrawController();
    Connection conn = makeConnection();
    ArrayList<Node> nodeList = createJavaNodes(conn);
    javafx.scene.Node sceneNode = mapDrawer.genMapFromNodes(nodeList);
    Navigation.navigate(sceneNode);
  }
}
