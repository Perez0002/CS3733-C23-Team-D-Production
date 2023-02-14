package edu.wpi.cs3733.C23.teamD.entities;

import java.util.ArrayList;

public class Pathfinder {

  private GraphMap fullMap;

  public Pathfinder(GraphMap fullMap) {
    this.fullMap = fullMap;
  }

  public void init(ArrayList<NodePathfinding> nodeList, ArrayList<EdgePathfinding> edgeList) {
    this.fullMap.init(nodeList, edgeList);
  }

  public ArrayList<NodePathfinding> pathfind(
      NodePathfinding startNode, NodePathfinding endNode, String algorithim) {
    ArrayList<NodePathfinding> path = new ArrayList<>();
    for (EdgePathfinding e : startNode.getNodeEdges()) {
      System.out.print(e.getToNode().getNodeID() + " ");
      System.out.println(e.getFromNode().getNodeID());
    }
    if (algorithim.equals("AStar")) {
      PathfinderAStar pathfinderAStar = new PathfinderAStar(fullMap);
      path.addAll(pathfinderAStar.aStarSearch(startNode, endNode));
    } else if (algorithim.equals("DFS")) {
      PathfinderDFS pathfinderDFS = new PathfinderDFS(fullMap);
      path.addAll(pathfinderDFS.depthFirstSearch(startNode, endNode));
    } else if (algorithim.equals("BFS")) {
      PathfinderBFS pathfinderBFS = new PathfinderBFS(fullMap);
      path.addAll(pathfinderBFS.breadthFirstSearch(startNode, endNode));
    }

    return path;
  }
}
