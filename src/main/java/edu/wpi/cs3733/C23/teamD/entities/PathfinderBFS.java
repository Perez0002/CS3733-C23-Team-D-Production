package edu.wpi.cs3733.C23.teamD.entities;

import java.util.*;

public class PathfinderBFS {

  private GraphMap fullMap;

  public PathfinderBFS(GraphMap fullMap) {
    this.fullMap = fullMap;
  }

  public void init(ArrayList<NodePathfinding> nodeList, ArrayList<EdgePathfinding> edgeList) {
    this.fullMap.init(nodeList, edgeList);
  }

  public void initFromDB() {
    this.fullMap.initFromDB();
  }

  public ArrayList<NodePathfinding> breadthFirstSearch(
      NodePathfinding startNode, NodePathfinding endNode) {
    ArrayList<NodePathfinding> path = new ArrayList<NodePathfinding>();
    ArrayList<NodePathfinding> frontier = new ArrayList<NodePathfinding>();
    HashSet<NodePathfinding> visited = new HashSet<NodePathfinding>();
    HashMap<NodePathfinding, NodePathfinding> nodeParentMap = new HashMap<>();
    path.add(startNode);
    NodePathfinding currentNode = startNode;
    frontier.add(currentNode);
    while (!frontier.isEmpty()) {
      frontier.remove(currentNode);
      visited.add(currentNode);
      if (currentNode.equals(endNode)) {
        while (!currentNode.equals(startNode)) {
          path.add(nodeParentMap.get(currentNode));
          currentNode = nodeParentMap.get(currentNode);
        }
        Collections.reverse(path);
        return path;
      }
      for (EdgePathfinding edge : currentNode.getNodeEdges()) {
        if (!visited.contains(edge.getToNode())) {
          frontier.add(edge.getToNode());
          nodeParentMap.put(edge.getToNode(), currentNode);
        }
      }
      currentNode = frontier.get(0);
    }

    return path;
  }
}
