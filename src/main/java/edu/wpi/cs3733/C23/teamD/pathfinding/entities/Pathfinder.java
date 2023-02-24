package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;

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

  public ArrayList<String> textPath(ArrayList<PathNode> pathList) {
    String angleText = "";
    ArrayList<String> directions = new ArrayList<>();
    for (int i = 0; i < pathList.size() - 1; i++) {
      int curX = pathList.get(i).getNode().getXcoord();
      int nextX = pathList.get(i + 1).getNode().getXcoord();
      int curY = pathList.get(i).getNode().getYcoord();
      int nextY = pathList.get(i + 1).getNode().getYcoord();
      int eucDistance = (int) Math.sqrt(abs(Math.pow(curX - nextX, 2) - Math.pow(curY - nextY, 2)));
      if (i - 1 > 0 && i + 1 < pathList.size()) {
        double angle =
            atan2(
                    abs(
                        pathList.get(i - 1).getNode().getYcoord()
                            - pathList.get(i).getNode().getYcoord()),
                    abs(
                        pathList.get(i - 1).getNode().getXcoord()
                            - pathList.get(i).getNode().getXcoord()))
                - atan2(
                    abs(
                        pathList.get(i + 1).getNode().getYcoord()
                            - pathList.get(i).getNode().getYcoord()),
                    abs(
                        pathList.get(i + 1).getNode().getXcoord()
                            - pathList.get(i).getNode().getXcoord()));
        if (angle > 0.1) {
          angleText = "turn right";
        } else if (angle < -0.1) {
          angleText = "turn left";
        } else {
          angleText = "go straight";
        }
      }
      if (i == 0) {
        directions.add(
            "Starting at "
                + pathList.get(i).getLocation().getLongName()
                + ", first go "
                + eucDistance
                + " meters to "
                + pathList.get(i + 1).getLocation().getLongName());
      } else if (i < pathList.size() - 2) {
        switch ((int) (Math.random() * 4)) {
          case 0:
            directions.add(
                "Thereafter, from "
                    + pathList.get(i).getLocation().getLongName()
                    + " "
                    + angleText
                    + " and then go "
                    + eucDistance
                    + " meters to "
                    + pathList.get(i + 1).getLocation().getLongName());
            break;
          case 1:
            directions.add(
                "Next, from "
                    + pathList.get(i).getLocation().getLongName()
                    + " "
                    + angleText
                    + "and then go"
                    + eucDistance
                    + " meters to "
                    + pathList.get(i + 1).getLocation().getLongName());
            break;
          case 2:
            directions.add(
                "Then, from "
                    + pathList.get(i).getLocation().getLongName()
                    + " "
                    + angleText
                    + " and then go "
                    + eucDistance
                    + " meters to "
                    + pathList.get(i + 1).getLocation().getLongName());
            break;
          case 3:
            directions.add(
                "Subsequently, from "
                    + pathList.get(i).getLocation().getLongName()
                    + " "
                    + angleText
                    + " and then go "
                    + eucDistance
                    + " meters to "
                    + pathList.get(i + 1).getLocation().getLongName());
            break;
        }
      } else {
        directions.add(
            "Finally, from "
                + pathList.get(i).getLocation().getLongName()
                + " go "
                + eucDistance
                + " meters to "
                + pathList.get(i + 1).getLocation().getLongName());
      }
    }
    return directions;
  }
}
