package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.entities.Node;
import java.util.ArrayList;

public class TestEnum {
  public static void main(String[] args) {
    // Ddb.csv2DBInsertNodes("src/main/resources/edu/wpi/cs3733/C23/teamD/data/L1Nodes.csv");
    // Ddb.csv2DBInsertEdges("src/main/resources/edu/wpi/cs3733/C23/teamD/data/L1Edges.csv");
    // Ddb.oldCSVtoNewCSV();
    ArrayList<Node> nodes = Ddb.createJavaNodes();
    Ddb.connectNodestoLocations(nodes);
    Node node = nodes.get(0);
    Node node1 = new Node(1, 1, "floorofhell", "building");
    node1.setLocation(node.getLocation());
    System.out.println(node.getNodeID());
    System.out.println(node.getLongName());
  }
}
