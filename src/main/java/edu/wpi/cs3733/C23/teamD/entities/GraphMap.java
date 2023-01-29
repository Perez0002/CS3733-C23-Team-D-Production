package edu.wpi.cs3733.C23.teamD.entities;

import edu.wpi.cs3733.C23.teamD.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class GraphMap
{
   private HashMap<String, PathNode> nodeMap;
   private HashMap<String, PathEdge> edgeMap;


   public void init()
   {
      // do something lol;
   }

   public void initFromCSV(String nodePath, String edgePath)
   {
      try (InputStream in = App.class.getResourceAsStream(nodePath)) {
         assert in != null;
         BufferedReader reader = new BufferedReader(new InputStreamReader(in));
         String line = reader.readLine();

         while ((line = reader.readLine()) != null) {
            String[] nodeValues = line.split(",");
            nodeMap.put(
                    nodeValues[0],
                    new PathNode(
                            Integer.parseInt(nodeValues[1]),
                            Integer.parseInt(nodeValues[2]),
                            nodeValues[3],
                            nodeValues[4],
                            nodeValues[5],
                            nodeValues[6],
                            nodeValues[7]));
         }
         reader.close();
      } catch (IOException x) {
         x.printStackTrace();
         System.err.println("Could not find file for Nodes!");
      }

      try (InputStream in = App.class.getResourceAsStream(edgePath)) {
         assert in != null;
         BufferedReader reader = new BufferedReader(new InputStreamReader(in));
         String line = reader.readLine();
         while ((line = reader.readLine()) != null) {
            String[] edgeValues = line.split(",");
            PathEdge base = new PathEdge(nodeMap.get(edgeValues[1]), nodeMap.get(edgeValues[0]));
            PathEdge reverse = new PathEdge(nodeMap.get(edgeValues[0]), nodeMap.get(edgeValues[1]));

            edgeMap.put(base.getEdgeID(), base);
            edgeMap.put(reverse.getEdgeID(), reverse);

            nodeMap.get(edgeValues[1]).getNodeEdges().add(base);
            nodeMap.get(edgeValues[0]).getNodeEdges().add(reverse);
         }
         reader.close();
      } catch (IOException x) {
         x.printStackTrace();
         System.err.println("Could not find file for Edges!");
      }
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
