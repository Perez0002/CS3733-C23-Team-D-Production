package edu.wpi.cs3733.C23.teamD.entities;

import lombok.Getter;
import lombok.Setter;

public class PathEdge
{
   @Getter
   private String edgeID;

   @Getter
   private PathNode fromNode;

   @Getter
   private PathNode toNode;
   @Getter
   double cost;

   public PathEdge(PathNode fromNode, PathNode toNode)
   {
      this.edgeID = fromNode.getNodeID() + "_" + toNode.getNodeID();
      this.fromNode = fromNode;
      this.toNode = toNode;
      this.cost =
              Math.sqrt(
                      Math.pow(fromNode.getNodeX() - toNode.getNodeX(), 2)
                              + Math.pow(fromNode.getNodeY() - toNode.getNodeY(), 2));
   }
}
