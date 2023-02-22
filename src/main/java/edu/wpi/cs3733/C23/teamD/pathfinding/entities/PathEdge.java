package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import lombok.Getter;

public class PathEdge {
  @Getter Edge edge;
  @Getter private PathNode toNode;
  @Getter private PathNode fromNode;

  public PathEdge(PathNode fromNode, PathNode toNode) {
    this.edge = null;
    this.toNode = toNode;
    this.fromNode = fromNode;
  }

  public void setEdge(Edge edge) {
    this.edge = edge;
  }

  public double getCost() {
    if (toNode.getLocation().getLocationType().equals("ELEV")
        && fromNode.getLocation().getLocationType().equals("ELEV")) {
      return 5000;
    } else if (toNode.getLocation().getLocationType().equals("STAI")
        && fromNode.getLocation().getLocationType().equals("STAI")) {
      return 25000;
    } else {
      return Math.sqrt(
          Math.pow(toNode.getNode().getXcoord() - fromNode.getNode().getXcoord(), 2)
              + Math.pow(toNode.getNode().getYcoord() - fromNode.getNode().getYcoord(), 2));
    }
  }
}
