package edu.wpi.cs3733.C23.teamD.entities;

import java.util.*;

public class PathfinderBFS {

  private GraphMap fullMap;

  public PathfinderBFS(GraphMap fullMap) {
    this.fullMap = fullMap;
  }

  public void init(ArrayList<Node> nodeList, ArrayList<Edge> edgeList) {
    this.fullMap.init(nodeList, edgeList);
  }

  public void initFromDB() {
    this.fullMap.initFromDB();
  }

  public ArrayList<Node> breadthFirstSearch(Node startNode, Node endNode) {
    ArrayList<Node> path = new ArrayList<Node>();
    ArrayList<Node> frontier = new ArrayList<Node>();
    HashSet<Node> visited = new HashSet<Node>();
    HashMap<Node, Node> nodeParentMap = new HashMap<>();
    path.add(startNode);
    Node currentNode = startNode;
    frontier.add(currentNode);
    while (!frontier.isEmpty()) {
      frontier.remove(currentNode);
      if (currentNode.equals(endNode)) {
        path.add(currentNode);
        while (!currentNode.equals(startNode)) {
          path.add(nodeParentMap.get(currentNode));
          currentNode = nodeParentMap.get(currentNode);
        }
        Collections.reverse(path);
        return path;
      }
      for (Edge edge : currentNode.getNodeEdges()) {
        frontier.add(edge.getToNode());
        nodeParentMap.put(edge.getToNode(), currentNode);
      }
      currentNode = frontier.get(0);
    }

    return null;
  }
}
