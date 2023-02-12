package edu.wpi.cs3733.C23.teamD.entities;

import java.util.ArrayList;

public class Pathfinder {

  private GraphMap fullMap;

  public Pathfinder(GraphMap fullMap) {
    this.fullMap = fullMap;
  }

  public void init(ArrayList<Node> nodeList, ArrayList<Edge> edgeList) {
    this.fullMap.init(nodeList, edgeList);
  }

  public ArrayList<Node> pathfind(Node startNode, Node endNode, String algorithim) {
    ArrayList<Node> path = new ArrayList<>();
    if (algorithim.equals("AStar")) {
      PathfinderAStar pathfinderAStar = new PathfinderAStar(fullMap);
      path = pathfinderAStar.aStarSearch(startNode, endNode);
    } else if (algorithim.equals("DFS")) {
      PathfinderDFS pathfinderDFS = new PathfinderDFS(fullMap);
      path = pathfinderDFS.depthFirstSearch(startNode, endNode);
    } else if (algorithim.equals("BFS")) {
      PathfinderBFS pathfinderBFS = new PathfinderBFS(fullMap);
      path = pathfinderBFS.breadthFirstSearch(startNode, endNode);
    }

    return path;
  }
}
