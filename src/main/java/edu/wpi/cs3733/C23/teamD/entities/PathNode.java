package edu.wpi.cs3733.C23.teamD.entities;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PathNode
{
   @Getter
   private String nodeID;
   @Getter
   private int nodeX;
   @Getter
   private int nodeY;
   @Getter
   private String nodeFloor;
   @Getter
   private String nodeBuilding;
   @Getter
   private String nodeType;
   @Getter
   private String longName;
   @Getter
   private String shortName;
   @Getter
   private ArrayList<PathEdge> nodeEdges;

   public PathNode(int nodeX, int nodeY, String nodeFloor, String nodeBuilding, String nodeType, String longName, String shortName)
   {
      this.nodeID = nodeFloor + "X" + nodeX + "Y" + nodeY; // Needs formatting
      this.nodeX = nodeX;
      this.nodeY = nodeY;
      this.nodeFloor = nodeFloor;
      this.nodeBuilding = nodeBuilding;
      this.nodeType = nodeType;
      this.longName = longName;
      this.shortName = shortName;
   }
}
