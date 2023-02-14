package edu.wpi.cs3733.C23.teamD.entities;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import javafx.util.Pair;

public class PathfinderAStar {
  private GraphMap fullMap;

  public PathfinderAStar(GraphMap fullMap) {
    this.fullMap = fullMap;
  }

  public void init(ArrayList<NodePathfinding> nodeList, ArrayList<EdgePathfinding> edgeList) {
    this.fullMap.init(nodeList, edgeList);
  }

  public void initFromDB() {
    this.fullMap.initFromDB();
  }

  private class PathCostPair extends Pair<ArrayList<NodePathfinding>, Double> {
    public PathCostPair(ArrayList<NodePathfinding> n, double c) {
      super(n, c);
    }
  }

  private class PathCostPairComparator implements Comparator<PathCostPair> {
    public int compare(PathCostPair i, PathCostPair p) {
      return i.getValue().compareTo(p.getValue());
    }
  }

  private double getHeuristic(Node currentNode, Node goalNode) {
    double heuristic =
        sqrt(
            pow(currentNode.getXcoord() - goalNode.getXcoord(), 2)
                + pow(currentNode.getYcoord() - goalNode.getYcoord(), 2));
    return heuristic;
  }

  public ArrayList<NodePathfinding> aStarSearch(
      NodePathfinding startNode, NodePathfinding endNode) {
    // init variables
    ArrayList<NodePathfinding> path = new ArrayList<NodePathfinding>();
    HashMap<String, NodePathfinding> beenNodes = new HashMap<String, NodePathfinding>();
    PriorityQueue<PathCostPair> queue =
        new PriorityQueue<PathCostPair>(5, new PathCostPairComparator());
    System.out.println(startNode.getNodeID());
    // add starting Node to beginning of path
    path.add(startNode);

    PathCostPair currentPath = null;

    queue.add(new PathCostPair(path, 0));
    // Loop as long as something is in the queue
    while (!queue.isEmpty()) {

      // remove and get the first 'path' in the queue
      currentPath = queue.poll();

      NodePathfinding currentNode =
          new NodePathfinding(currentPath.getKey().get(currentPath.getKey().size() - 1));

      // Put the Node we are at right now into list of Nodes we have been to
      beenNodes.put(currentNode.getNodeID(), currentNode);

      if (currentNode.equals(endNode)) {
        // if the current Node is our target Node, we are done, exit loop
        // System.out.println("Found node!");
        return currentPath.getKey();
      }

      // for every connection in our current Node, add  those we have not been too to the queue
      for (EdgePathfinding e : currentNode.getNodeEdges()) {
        System.out.print(e.getToNode().getNodeID() + " ");
        System.out.println(e.getFromNode().getNodeID());
        if (!beenNodes.containsKey(e.getToNode().getNodeID())) {
          ArrayList<NodePathfinding> temp = (ArrayList) currentPath.getKey().clone();
          temp.add(e.getToNode());
          queue.add(
              new PathCostPair(
                  temp,
                  currentPath.getValue() + e.getCost() + getHeuristic(e.getToNode(), endNode)));
        }
      }
    }

    // In the event a Node could not be found, return default path
    // System.out.println("Could not find Node!");
    return new ArrayList<NodePathfinding>();
  }
}
