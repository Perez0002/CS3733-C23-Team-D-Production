package edu.wpi.cs3733.C23.teamD.entities;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Pathfinder
{
   private GraphMap fullMap;
   public Pathfinder(GraphMap fullMap)
   {
      this.fullMap = fullMap;
   }
   public void init()
   {
      this.fullMap.init();
   }

   private class PathCostPair extends Pair<ArrayList<PathNode>, Double>
   {
      public PathCostPair(ArrayList<PathNode> n, double c) {
         super(n, c);
      }
   }

   private class PathCostPairComparator implements Comparator<PathCostPair>
   {
      public int compare(PathCostPair i, PathCostPair p) {
         return i.getValue().compareTo(p.getValue());
      }
   }

   private double getHeuristic(PathNode currentNode, PathNode goalNode) {
      double heuristic =
              sqrt(
                      pow(currentNode.getNodeX() - goalNode.getNodeX(), 2)
                              + pow(currentNode.getNodeY() - goalNode.getNodeY(), 2));
      return heuristic;
   }

   public ArrayList<PathNode> aStarSearch(PathNode startNode, PathNode endNode)
   {
      // init variables
      ArrayList<PathNode> path = new ArrayList<PathNode>();
      HashMap<String, PathNode> beenNodes = new HashMap<String, PathNode>();
      PriorityQueue<PathCostPair> queue =
              new PriorityQueue<PathCostPair>(5, new PathCostPairComparator());

      // add starting Node to beginning of path
      path.add(startNode);

      PathCostPair currentPath = null;

      queue.add(new PathCostPair(path, 0));

      // Loop as long as something is in the queue
      while (!queue.isEmpty()) {

         // remove and get the first 'path' in the queue
         currentPath = queue.poll();

         PathNode currentNode = currentPath.getKey().get(currentPath.getKey().size() - 1);

         // Put the Node we are at right now into list of Nodes we have been to
         beenNodes.put(currentNode.getNodeID(), currentNode);

         if (currentNode.equals(endNode)) {
            // if the current Node is our target Node, we are done, exit loop
            return currentPath.getKey();
         }

         // for every connection in our current Node, add  those we have not been too to the queue
         for (PathEdge e : currentNode.getNodeEdges()) {
            if (!beenNodes.containsKey(e.getToNode())) {
               ArrayList<PathNode> temp = (ArrayList)currentPath.getKey().clone();
               temp.add(e.getToNode());
               queue.add(
                       new PathCostPair(
                               temp,
                               currentPath.getValue()
                                       + e.getCost()
                                       + getHeuristic(e.getToNode(), endNode)));
            }
         }
      }

      // In the event a Node could not be found, return default path
      System.out.println("Could not find Node!");
      return path;
   }
}
