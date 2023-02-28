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
    PathNode nextNonStraight = null;
    ArrayList<String> directions = new ArrayList<>();
    for (int i = 0; i < pathList.size() - 1; i++) {
      int curX = pathList.get(i).getNode().getXcoord();
      int nextX = pathList.get(i + 1).getNode().getXcoord();
      int curY = pathList.get(i).getNode().getYcoord();
      int nextY = pathList.get(i + 1).getNode().getYcoord();
      int eucDistance = (int) Math.sqrt(abs(Math.pow(curX - nextX, 2) - Math.pow(curY - nextY, 2)));
      char rightArrow = 0x2190;
      char leftArrow = 0x2192;
      char upArrow = 0x2191;
      char icon = ' ';
      if (i == 0) {
        directions.add(
            upArrow
                + " Starting at "
                + pathList.get(i).getLocation().getLongName()
                + ", first go "
                + eucDistance
                + " meters to "
                + pathList.get(i + 1).getLocation().getLongName());
      } else if (i < pathList.size() - 1) {
        if (!pathList
            .get(i)
            .getNode()
            .getFloor()
            .equals(pathList.get(i + 1).getNode().getFloor())) {
          directions.add(
              upArrow + " Take the elevator to floor " + pathList.get(i + 1).getNode().getFloor());
        } else if (!pathList
            .get(i)
            .getNode()
            .getFloor()
            .equals(pathList.get(i - 1).getNode().getFloor())) {
          directions.add(
              upArrow
                  + " From the elevator go to "
                  + pathList.get(i + 1).getLocation().getLongName());
        } else {
          double angle = findAngle(pathList.get(i - 1), pathList.get(i), pathList.get(i + 1));
          if (angle > 0.2) {
            angleText = "turn right";
            icon = rightArrow;
          } else if (angle < -0.2) {
            angleText = "turn left";
            icon = leftArrow;
          } else {
            directions.add(null);
          }
          if (abs(angle) > 0.2) {
            nextNonStraight = findNextNonStraight(pathList, i);
            nextY = nextNonStraight.getNode().getYcoord();
            nextX = nextNonStraight.getNode().getXcoord();
            eucDistance =
                (int) Math.sqrt(abs(Math.pow(curX - nextX, 2) - Math.pow(curY - nextY, 2)));
            directions.add(
                icon
                    + " Next, from "
                    + pathList.get(i).getLocation().getLongName()
                    + " "
                    + angleText
                    + " and then go "
                    + eucDistance
                    + " meters to "
                    + nextNonStraight.getLocation().getLongName());
          }
        }
      } else {
        //        directions.add(
        //            "Finally, from "
        //                + pathList.get(i).getLocation().getLongName()
        //                + " go "
        //                + eucDistance
        //                + " meters to "
        //                + pathList.get(i + 1).getLocation().getLongName());
      }
    }
    directions.add("   You have reached your destination");

    return directions;
  }

  private double findAngle(PathNode prevNode, PathNode currNode, PathNode nextNode) {
    double angle =
        atan2(
                abs(prevNode.getNode().getYcoord() - currNode.getNode().getYcoord()),
                abs(prevNode.getNode().getXcoord() - currNode.getNode().getXcoord()))
            - atan2(
                abs(nextNode.getNode().getYcoord() - currNode.getNode().getYcoord()),
                abs(nextNode.getNode().getXcoord() - currNode.getNode().getXcoord()));

    return angle;
  }

  private PathNode findNextNonStraight(ArrayList<PathNode> pathList, int index) {
    PathNode nextNonStraight = null;
    for (int i = index + 1; i < pathList.size() - 1; i++) {
      double angle = findAngle(pathList.get(i - 1), pathList.get(i), pathList.get(i + 1));
      if (abs(angle) > 0.2
          || !pathList
              .get(i)
              .getNode()
              .getFloor()
              .equals(pathList.get(i + 1).getNode().getFloor())) {
        nextNonStraight = pathList.get(i);
        break;
      }
    }
    if (nextNonStraight == null) {
      nextNonStraight = pathList.get(pathList.size() - 1);
    }
    return nextNonStraight;
  }
}
