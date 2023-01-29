package edu.wpi.cs3733.C23.teamD.entities;

import java.util.HashMap;

public class GraphMap
{
   private HashMap<String, PathNode> nodeMap;
   private HashMap<String, PathEdge> edgeMap;


   public void init()
   {
      // do something lol;
   }

   public PathNode getNode(String nodeID)
   {
      return nodeMap.get(nodeID);
   }

   public PathEdge getEdge(String edgeID)
   {
      return edgeMap.get(edgeID);
   }
   // hlep.
}
