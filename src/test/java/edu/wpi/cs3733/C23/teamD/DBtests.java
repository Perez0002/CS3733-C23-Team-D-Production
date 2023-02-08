package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.entities.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DBtests {
  @Test
  public void nodeConstructors() {
    Node node = new Node();
    Node node2 = new Node(1, 1, "floorofhell", "buildingofhell");
    node.setXcoord(node2.getXcoord());
    node.setYcoord(node2.getYcoord());
    node.setFloor(node2.getFloor());
    node.setBuilding(node2.getBuilding());
    node.setNodeID(
        node.getFloor()
            + "X"
            + String.format("%04d", node.getXcoord())
            + "Y"
            + String.format("%04d", node.getYcoord()));
    Assertions.assertEquals(node, node2);
  }
}
