package edu.wpi.cs3733.C23.teamD.entities;

import static edu.wpi.cs3733.C23.teamD.Ddb.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    Connection conn = makeConnection();
    ArrayList<Node> nodeList = createJavaNodes(conn);
    ArrayList<Edge> edgeList = createJavaEdges(conn, nodeList);
    ArrayList<locationName> locList = createJavaLocat(conn);

    for (Node node : nodeList) {
      //      System.out.println(node.getNodeID());
      nodeMap.put(node.getNodeID(), node);
      ResultSet rset;
      String curName = "";

      try {
        PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM Move where nodeID = ?");
        pstmnt.setString(1, node.getNodeID());
        rset = pstmnt.executeQuery();
        if (rset.next()) curName = rset.getString("longName");
        for (locationName loc : locList) {
          if (loc.getLongName().equals(curName)) {
            node.setLocation(loc);
            continue;
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
        return;
      }
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
