package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import lombok.Getter;

public class PathNode {
  @Getter private Node node;

  @Getter private LocationName location;

  public PathNode(Node node, LocationName location) {
    this.node = node;

    this.location = location;
  }
}
