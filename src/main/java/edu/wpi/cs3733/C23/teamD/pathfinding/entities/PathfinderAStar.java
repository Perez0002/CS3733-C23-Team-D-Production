package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.*;
import javafx.util.Pair;

public class PathfinderAStar {

  public PathfinderAStar() {}

  private class PathCostPair extends Pair<ArrayList<PathNode>, Double> {
    public PathCostPair(ArrayList<PathNode> n, double c) {
      super(n, c);
    }
  }

  private class PathCostPairComparator implements Comparator<PathCostPair> {
    public int compare(PathCostPair i, PathCostPair p) {
      return i.getValue().compareTo(p.getValue());
    }
  }

  private double getHeuristic(PathNode currentNode, PathNode goalNode) {
    double heuristic =
        sqrt(
            pow(currentNode.getNode().getXcoord() - goalNode.getNode().getXcoord(), 2)
                + pow(currentNode.getNode().getYcoord() - goalNode.getNode().getYcoord(), 2));
    return heuristic;
  }

  public ArrayList<PathNode> aStarSearch(PathNode startNode, PathNode endNode) {
    // init variables
    ArrayList<PathNode> path = new ArrayList<PathNode>();
    HashSet<PathNode> beenNodes = new HashSet<PathNode>();
    PriorityQueue<PathCostPair> queue =
        new PriorityQueue<PathCostPair>(5, new PathCostPairComparator());
    // add starting Node to beginning of path
    path.add(startNode);
    beenNodes.add(startNode);

    PathCostPair currentPath = null;

    queue.add(new PathCostPair(path, 0));
    // Loop as long as something is in the queue
    while (!queue.isEmpty()) {

      // remove and get the first 'path' in the queue
      currentPath = queue.poll();

      PathNode currentNode = currentPath.getKey().get(currentPath.getKey().size() - 1);
      beenNodes.add(currentNode);

      if (currentNode.equals(endNode)) {
        // if the current Node is our target Node, we are done, exit loop
        return currentPath.getKey();
      }

      for (PathEdge e : currentNode.getEdgeList()) {
        if (!beenNodes.contains(e.getToNode())) {
          ArrayList<PathNode> temp = (ArrayList) currentPath.getKey().clone();
          temp.add(e.getToNode());
          queue.add(new PathCostPair(temp, currentPath.getValue() + e.getCost()));
        }
      }
    }

    // In the event a Node could not be found, return default path
    // System.out.println("Could not find Node!");
    return new ArrayList<PathNode>();
  }
}
