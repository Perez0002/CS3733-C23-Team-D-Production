package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PathfindingTest {
  private GraphMap graphMap;
  private Pathfinder pathfinder;

  @BeforeEach
  public void init() {
    graphMap = new GraphMap();
    pathfinder = new Pathfinder(graphMap);
    ArrayList<Node> nodeList = new ArrayList<Node>();
    ArrayList<Edge> edgeList = new ArrayList<Edge>();

    nodeList.add(new Node(0, 0, "L1", "Fred"));
    nodeList.add(new Node(5, 5, "L1", "Fred"));
    nodeList.add(new Node(1, 2, "L1", "Fred"));
    nodeList.add(new Node(8, 6, "L1", "Fred"));
    nodeList.add(new Node(10, 10, "L1", "Fred"));
    nodeList.add(new Node(12, 12, "L1", "Fred"));

    edgeList.add(new Edge(nodeList.get(0), nodeList.get(1)));
    edgeList.add(new Edge(nodeList.get(0), nodeList.get(2)));
    edgeList.add(new Edge(nodeList.get(0), nodeList.get(3)));
    edgeList.add(new Edge(nodeList.get(1), nodeList.get(4)));

    edgeList.add(new Edge(nodeList.get(1), nodeList.get(0)));
    edgeList.add(new Edge(nodeList.get(2), nodeList.get(0)));
    edgeList.add(new Edge(nodeList.get(3), nodeList.get(0)));
    edgeList.add(new Edge(nodeList.get(4), nodeList.get(1)));

    nodeList.get(0).getNodeEdges().add(edgeList.get(0));
    nodeList.get(0).getNodeEdges().add(edgeList.get(1));
    nodeList.get(0).getNodeEdges().add(edgeList.get(2));
    nodeList.get(1).getNodeEdges().add(edgeList.get(3));

    nodeList.get(1).getNodeEdges().add(edgeList.get(4));
    nodeList.get(2).getNodeEdges().add(edgeList.get(5));
    nodeList.get(3).getNodeEdges().add(edgeList.get(6));
    nodeList.get(4).getNodeEdges().add(edgeList.get(7));

    pathfinder.init(nodeList, edgeList);
  }

  @Test
  public void testBasicPath() {
    ArrayList<Node> tempList = new ArrayList<Node>();

    tempList.add(graphMap.getNode("L1X0Y0"));
    tempList.add(graphMap.getNode("L1X5Y5"));
    tempList.add(graphMap.getNode("L1X10Y10"));

    ArrayList<Node> output =
        pathfinder.aStarSearch(graphMap.getNode("L1X0Y0"), graphMap.getNode("L1X10Y10"));
    for (Node n : output) {
      System.out.println(n.getNodeID());
    }
    Assertions.assertEquals(tempList, output);
  }

  @Test
  public void testNoPath() {
    ArrayList<Node> tempList = new ArrayList<Node>();

    ArrayList<Node> output =
        pathfinder.aStarSearch(graphMap.getNode("L1X0Y0"), graphMap.getNode("L1X12Y12"));

    Assertions.assertEquals(tempList, output);
  }
}
