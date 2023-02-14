package edu.wpi.cs3733.C23.teamD.entities;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphMap {
  // private HashMap<String, Node> nodeMap;
  // private HashMap<String, Edge> edgeMap;
  private HashMap<String, NodePathfinding> nodeMap = new HashMap<String, NodePathfinding>();
  private HashMap<String, EdgePathfinding> edgeMap = new HashMap<String, EdgePathfinding>();

  public void init(ArrayList<NodePathfinding> nodeList, ArrayList<EdgePathfinding> edgeList) {

    for (NodePathfinding node : nodeList) {
      nodeMap.put(node.getNodeID(), node);
      // System.out.println(node.getNodeID());
    }

    for (EdgePathfinding edge : edgeList) {
      edgeMap.put(edge.getEdgeID(), edge);
      // System.out.println(edge.getEdgeID());
    }
  }

  public void initFromDB() {
    ArrayList<NodePathfinding> nodeList = Ddb.nodeToPathfinding(FDdb.getInstance().getAllNodes());
    ArrayList<EdgePathfinding> edgeList = Ddb.edgetoPathfinding(FDdb.getInstance().getAllEdges());
    connectNodestoLocations(nodeList);
    for (NodePathfinding node : nodeList) {
      nodeMap.put(node.getNodeID(), node);
    }
    for (EdgePathfinding edge : edgeList) {
      edgeMap.put(edge.getEdgeID(), edge);
      EdgePathfinding tempEdge = new EdgePathfinding(edge.getToNode(), edge.getFromNode());
      tempEdge.genEdgeID();
      tempEdge.genCost();
      edge.getFromNode().getNodeEdges().add(edge);
      edge.getToNode().getNodeEdges().add(tempEdge);
      edgeMap.put(tempEdge.getEdgeID(), tempEdge);
    }
  }

  public NodePathfinding getNode(String nodeID) {
    return nodeMap.get(nodeID);
  }

  public EdgePathfinding getEdge(String edgeID) {
    return edgeMap.get(edgeID);
  }
}
