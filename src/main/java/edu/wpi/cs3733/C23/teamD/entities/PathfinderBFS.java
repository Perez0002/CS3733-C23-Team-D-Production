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
    Node currentNode = startNode;
    frontier.add(currentNode);
    while (!frontier.isEmpty()) {
      frontier.remove(currentNode);
      visited.add(currentNode);
      if (currentNode.equals(endNode)) {
        System.out.println(endNode.getLongName());
        path.add(endNode);
        while (!currentNode.equals(startNode)) {
          path.add(nodeParentMap.get(currentNode));
          System.out.println(path.get(path.size() - 1).getLongName());
          currentNode = nodeParentMap.get(currentNode);
        }
        Collections.reverse(path);
        return path;
      }
      for (Edge edge : currentNode.getNodeEdges()) {
        if (!visited.contains(edge.getToNode()) && !frontier.contains(edge.getToNode())) {
          frontier.add(edge.getToNode());
          nodeParentMap.put(edge.getToNode(), currentNode);
        }
      }
      System.out.println(frontier.size());
      currentNode = frontier.get(0);
    }

    return path;
  }
}
