package edu.wpi.cs3733.C23.teamD.entities;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphMap {
  // private HashMap<String, Node> nodeMap;
  // private HashMap<String, Edge> edgeMap;
  private HashMap<String, Node> nodeMap = new HashMap<String, Node>();
  private HashMap<String, Edge> edgeMap = new HashMap<String, Edge>();

  public void init() {
    // do something lol;
  }
  /*
  public void initFromCSV(String nodePath, String edgePath) {
    try (InputStream in = App.class.getResourceAsStream(nodePath)) {
      assert in != null;
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line = reader.readLine();

      while ((line = reader.readLine()) != null) {
        String[] nodeValues = line.split(",");
        nodeMap.put(
            nodeValues[0],
            new Node(
                Integer.parseInt(nodeValues[1]),
                Integer.parseInt(nodeValues[2]),
                nodeValues[3],
                nodeValues[4],
                nodeValues[5],
                nodeValues[6],
                nodeValues[7]));
      }
      reader.close();
    } catch (IOException x) {
      x.printStackTrace();
      System.err.println("Could not find file for Nodes!");
    }

    try (InputStream in = App.class.getResourceAsStream(edgePath)) {
      assert in != null;
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line = reader.readLine();
      while ((line = reader.readLine()) != null) {
        String[] edgeValues = line.split(",");
        Edge base = new Edge(nodeMap.get(edgeValues[1]), nodeMap.get(edgeValues[0]));
        Edge reverse = new Edge(nodeMap.get(edgeValues[0]), nodeMap.get(edgeValues[1]));

        edgeMap.put(base.getEdgeID(), base);
        edgeMap.put(reverse.getEdgeID(), reverse);

        nodeMap.get(edgeValues[1]).getNodeEdges().add(base);
        nodeMap.get(edgeValues[0]).getNodeEdges().add(reverse);
      }
      reader.close();
    } catch (IOException x) {
      x.printStackTrace();
      System.err.println("Could not find file for Edges!");
    }
  }
  */
  public void initFromDB() {
    ArrayList<Node> nodeList = createJavaNodes();
    ArrayList<Edge> edgeList = createJavaEdges(nodeList);
    connectNodestoLocations(nodeList);
    for (Node node : nodeList) {
      nodeMap.put(node.getNodeID(), node);
    }
    for (Edge edge : edgeList) {
      edgeMap.put(edge.getEdgeID(), edge);
      Edge tempEdge = new Edge(edge.getToNode(), edge.getFromNode());
      tempEdge.genEdgeID();
      tempEdge.genCost();
      edge.getFromNode().getNodeEdges().add(edge);
      edge.getToNode().getNodeEdges().add(tempEdge);
      edgeMap.put(tempEdge.getEdgeID(), tempEdge);
    }
  }

  public Node getNode(String nodeID) {
    return nodeMap.get(nodeID);
  }

  public Edge getEdge(String edgeID) {
    return edgeMap.get(edgeID);
  }
}
