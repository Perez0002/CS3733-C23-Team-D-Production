package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import java.util.*;

public class PathfinderBFS {

  public PathfinderBFS() {}

  public ArrayList<PathNode> breadthFirstSearch(PathNode startNode, PathNode endNode) {
    ArrayList<PathNode> path = new ArrayList<>();
    ArrayList<PathNode> frontier = new ArrayList<>();
    HashSet<PathNode> visited = new HashSet<>();
    HashMap<PathNode, PathNode> nodeParentMap = new HashMap<>();
    PathNode currentNode = startNode;
    frontier.add(currentNode);
    while (!frontier.isEmpty()) {
      frontier.remove(currentNode);
      visited.add(currentNode);
      if (currentNode.equals(endNode)) {

        path.add(endNode);
        while (!currentNode.equals(startNode)) {
          path.add(nodeParentMap.get(currentNode));
          currentNode = nodeParentMap.get(currentNode);
        }
        Collections.reverse(path);
        return path;
      }
      for (PathEdge edge : currentNode.getEdgeList()) {
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
