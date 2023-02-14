package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Ddb {
  public static void connectNodestoLocations(ArrayList<Node> nodes) {
    ArrayList<Move> moves = FDdb.getInstance().getAllMoves();
    for (Node node : nodes) {
      for (Move move : moves) {
        if (node.nodeEquals(move.getNode())) {
          node.setLocation(move.getLocation());
        }
      }
    }
  }
  /** @param csvFilePaths */
  public static void csv2DBInsertNodes(String csvFilePaths) {
    try {
      BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePaths));
      String lineText = null;
      lineReader.readLine(); // skip header line
      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        Node node =
            new Node(Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3], data[4]);
        node.setNodeID(data[0]);
        FDdb.getInstance().saveNode(node);
        LocationName loc = new LocationName(data[6], data[7], data[5]);
        FDdb.getInstance().saveLocationName(loc);
        Move move = new Move(node, loc, new Date());
        FDdb.getInstance().saveMove(move);
      }
      lineReader.close();
      // execute the remaining queries
      System.out.println("Data entered successfully.");
      // closing connection
    } catch (IOException ex) {
      System.err.println(ex);
    }
  }

  public static void csv2DBInsertEdges(String csvFilePaths) {
    try {
      ArrayList<Node> nodes = FDdb.getInstance().getAllNodes();
      BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePaths));
      String lineText = null;
      lineReader.readLine(); // skip header line
      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        Edge edge = new Edge();
        for (Node node : nodes) {
          if (data[2].equals(node.getNodeID())) {
            edge.setFromNode(node);
          } else if (data[1].equals(node.getNodeID())) {
            edge.setToNode(node);
          }
        }
        edge.setEdgeID(data[0]);
        FDdb.getInstance().saveEdge(edge);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void oldCSVtoNewCSV() {
    HashMap<String, String> oldtoNew = new HashMap<String, String>();
    try {
      BufferedReader lineReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/nodes.csv"));
      String lineText = null;
      lineReader.readLine(); // skip header line
      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        oldtoNew.put(data[8], data[0]);
      }
      lineReader.close();
      lineReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/edges.csv"));
      BufferedWriter lineWriter =
          new BufferedWriter(
              new FileWriter("src/main/resources/edu/wpi/cs3733/C23/teamD/data/edges3.csv"));
      lineWriter.write(lineReader.readLine());
      lineWriter.newLine();
      lineText = null;
      while (lineReader.ready()) {
        lineText = lineReader.readLine();
        String[] data = lineText.split(",");
        String newStartNode = oldtoNew.get(data[1]);
        String newendNode = oldtoNew.get(data[2]);
        lineWriter.write(
            String.join(",", newStartNode + "_" + newendNode, newStartNode, newendNode));
        lineWriter.newLine();
      }
      lineWriter.close();
      lineReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
