package edu.wpi.cs3733.C23.teamD.pathfinding.entities;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import java.util.ArrayList;
import lombok.Getter;

public class PathNode {
  @Getter private Node node;

  @Getter private LocationName location;

  @Getter private ArrayList<PathEdge> edgeList;

  public PathNode(Node node, LocationName location) {
    this.node = node;
    this.location = location;
    this.edgeList = new ArrayList<PathEdge>();
  }

  public void addEdge(PathEdge edge) {
    this.edgeList.add(edge);
  }
}
