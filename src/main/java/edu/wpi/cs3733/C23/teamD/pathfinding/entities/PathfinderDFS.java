package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import java.util.ArrayList;

public class PathfinderDFS {

  public PathfinderDFS() {}

  public ArrayList<PathNode> depthFirstSearch(PathNode startNode, PathNode endNode) {
    ArrayList<PathNode> path = new ArrayList<>();
    ArrayList<PathNode> visited = new ArrayList<>();
    ArrayList<PathEdge> neighbors = new ArrayList<>();
    PathNode currentNode = startNode;
    int rollbackCount = 1;

    while (!currentNode.getNode().getNodeID().equals(endNode.getNode().getNodeID())) {
      // neighbors.clear();

      neighbors = (ArrayList<PathEdge>) currentNode.getEdgeList().clone();
      if (startNode.equals(currentNode) && neighbors.size() == 0) {
        path.clear();
        break;
      }
      for (int i = 0; i < neighbors.size(); i++) {
        // if the edge leads to an unvisited node: Make it the new current node

        if (!visited.contains(neighbors.get(i).getToNode())) {
          rollbackCount = 1;
          currentNode = neighbors.get(i).getToNode();
          visited.add(currentNode);
          path.add(currentNode);
          break;
        }

        // If the current node has no unvisited neighbors, go back through visited until we find one
        // that does. Assign it to be the new current node
        else if (i == (neighbors.size() - 1)) {
          // visited.add(currentNode);
          path.remove(currentNode);
          currentNode = visited.get((visited.size() - rollbackCount));
          rollbackCount++;
        }
      }
    }

    return path;
  }
}
