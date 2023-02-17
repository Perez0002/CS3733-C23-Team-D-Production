package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import lombok.Getter;

public class PathEdge {
  @Getter private PathNode toNode;
  @Getter private PathNode fromNode;

  public PathEdge(PathNode toNode, PathNode fromNode) {
    this.toNode = toNode;
    this.fromNode = fromNode;
  }

  public double getCost() {
    return Math.sqrt(
        Math.pow(toNode.getNode().getXcoord() + fromNode.getNode().getXcoord(), 2)
            + Math.pow(toNode.getNode().getYcoord() + fromNode.getNode().getYcoord(), 2));
  }
}
