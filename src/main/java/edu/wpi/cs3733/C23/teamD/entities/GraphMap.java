package edu.wpi.cs3733.C23.teamD.entities;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphMap {
  // private HashMap<String, Node> nodeMap;
  // private HashMap<String, Edge> edgeMap;
  private HashMap<String, Node> nodeMap = new HashMap<String, Node>();
  private HashMap<String, Edge> edgeMap = new HashMap<String, Edge>();

  public void init(ArrayList<Node> nodeList, ArrayList<Edge> edgeList) {
    for (Node node : nodeList) {
      nodeMap.put(node.getNodeID(), node);
      // System.out.println(node.getNodeID());
    }

    for (Edge edge : edgeList) {
      edgeMap.put(edge.getEdgeID(), edge);
      // System.out.println(edge.getEdgeID());
    }
  }

  public void initFromDB() {
    ArrayList<Node> nodeList = FDdb.getInstance().getAllNodes();
    ArrayList<Edge> edgeList = FDdb.getInstance().getAllEdges();
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
