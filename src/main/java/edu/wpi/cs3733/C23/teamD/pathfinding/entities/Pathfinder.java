package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import java.util.ArrayList;

public class Pathfinder {

  public Pathfinder() {}

  public ArrayList<PathNode> pathfind(PathNode startNode, PathNode endNode, String algorithim) {
    ArrayList<PathNode> path = new ArrayList<>();

    if (algorithim.equals("AStar")) {
      PathfinderAStar pathfinderAStar = new PathfinderAStar();
      path.addAll(pathfinderAStar.aStarSearch(startNode, endNode));
    } else if (algorithim.equals("DFS")) {
      PathfinderDFS pathfinderDFS = new PathfinderDFS();
      path.addAll(pathfinderDFS.depthFirstSearch(startNode, endNode));
    } else if (algorithim.equals("BFS")) {
      PathfinderBFS pathfinderBFS = new PathfinderBFS();
      path.addAll(pathfinderBFS.breadthFirstSearch(startNode, endNode));
    }

    return path;
  }
  private enum direction{
    XCHANGE,
    YCHANGE;
  }
  public String textPath(ArrayList<PathNode> pathList) {
    String path = "Starting at "+pathList.get(0).getLocation().getLongName()+", first ";

    for(int i=1; i<pathList.size();i++) {
      int xDistance = pathList.get(i).getNode().getXcoord();
      int yDistance = pathList.get(i).getNode().getYcoord()-pathList.get(i+1).getNode().getYcoord();
      if(i>1 && i<pathList.size()-1) {
        switch ((int)(Math.random() * 4)) {
          case 0:
            path=path.concat(", thereafter ");
          case 1:
            path =path.concat(", next ");
            break;
          case 2:
            path = path.concat(", then ");
            break;
          case 3:
            path = path.concat(", subsequently ");
            break;
        }
        if(xDistance ==0){
          if(yDistance >0){

          }
          if(yDistance <0){

          }
        }
        else if(xDistance>0){

        }
        else{

        }
        if(i==pathList.size()-1){
          path = path.concat(", finally ");
        }
      }
    }

    return path;
  }
}
