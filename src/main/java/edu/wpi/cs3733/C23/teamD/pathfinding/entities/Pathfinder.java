package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.Node;

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
  public String textPath(ArrayList<PathNode> pathList) {
    String path = "Starting at "+pathList.get(0).getLocation().getLongName()+", first ";

    for(int i=1; i<pathList.size();i++) {
     Node curNode = pathList.get(i).getNode();
      int xDistance = curNode.getXcoord()-pathList.get(i+1).getNode().getXcoord();
      int yDistance = curNode.getYcoord()-pathList.get(i+1).getNode().getYcoord();
      double r = Math.sqrt((xDistance*xDistance)+(yDistance*yDistance));
      double Degrees;
      if(yDistance==0)
        Degrees = Math.acos(xDistance/);
      else if(xDistance==0)
        Degrees = Math.acos(yDistance/);
      else
        Degrees = Math.acos()

    }

    return path;
  }
  private String getDirection(Direction lastD, Direction curD){
    if(lastD.equals(Direction.STRAIGHT)){
      if(curD.equals(Direction.RIGHT)){
        return "go right from this kiosk";
      }
      else if(curD.equals(Direction.LEFT)){
        return "go left from this kiosk";
      }
    }
    if(curD.equals(lastD)){
      return " continue straight ";
    }
    else{
      if(curD.equals(Direction.RIGHT)){
        return " turn right ";
      }
      else if(curD.equals(Direction.LEFT)){
        return " turn left ";
      }
    }
    return "";
  }
}
