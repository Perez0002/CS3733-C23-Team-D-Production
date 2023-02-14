package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.DBSingleton;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import org.hibernate.Session;

public class Ddb {

  private static final File logFile = new File("logfile.txt");

  @Getter private static Session DBsession = DBSingleton.getSession();

  /**
   * Creates a list of all the nodes stored in the wpi database
   *
   * @return a list of all nodes stored in the database
   */
  public static ArrayList<Node> createJavaNodes() {
    DBsession.beginTransaction();
    ArrayList<Node> nodeList =
        new ArrayList<Node>(DBsession.createQuery("SELECT n FROM Node n").getResultList());
    DBsession.getTransaction().commit();
    return nodeList;
  }

  /**
   * Writes the log String to the logfile
   *
   * @param log The string that needs to be logged
   */
  private static void updateLogFile(String log) {
    try {
      if (!logFile.exists()) logFile.createNewFile();
      FileWriter logStream = new FileWriter(logFile, true);
      BufferedWriter writer = new BufferedWriter(logStream);
      writer.write(log + "\n");
      writer.close();
      logStream.close();
    } catch (IOException e) {
      System.out.println("failed to create file");
    }
  }

  public static void connectNodestoLocations(ArrayList<NodePathfinding> nodes) {
    ArrayList<Move> moves = FDdb.getInstance().getAllMoves();
    for (NodePathfinding node : nodes) {
      for (Move move : moves) {
        if (node.getNodeID().equals(move.getNode().getNodeID())) {
          node.setLocation(move.getLocation());
        }
      }
    }
  }
  /** @param csvFilePaths */
  public static void csv2DBInsertNodes(String csvFilePaths) {
    try {
      DBsession.beginTransaction();
      BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePaths));
      String lineText = null;
      lineReader.readLine(); // skip header line
      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        Node node =
            new Node(Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3], data[4]);
        node.setNodeID(data[0]);
        DBsession.persist(node);
        LocationName loc = new LocationName(data[6], data[7], data[5]);
        DBsession.persist(loc);
        Move move = new Move(node, loc, new Date());
        DBsession.persist(move);
      }
      lineReader.close();
      // execute the remaining queries
      System.out.println("Data entered successfully.");
      // closing connection
      DBsession.getTransaction().commit();
    } catch (IOException ex) {
      System.err.println(ex);
    } catch (Exception ex) {
      ex.printStackTrace();
      DBsession.getTransaction().rollback();
    }
  }

  public static void csv2DBInsertEdges(String csvFilePaths) {
    try {
      ArrayList<Node> nodes = FDdb.getInstance().getAllNodes();
      DBsession.beginTransaction();
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
        edge.genEdgeID();
        DBsession.persist(edge);
      }
      DBsession.getTransaction().commit();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
      DBsession.getTransaction().rollback();
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

  public static ArrayList<EdgePathfinding> edgetoPathfinding(ArrayList<Edge> Db) {
    ArrayList<EdgePathfinding> edgePList = new ArrayList<EdgePathfinding>();
    for (Edge edge : Db) {
      EdgePathfinding newEdge = new EdgePathfinding(edge);
      edgePList.add(newEdge);
    }
    return edgePList;
  }

  public static ArrayList<NodePathfinding> nodeToPathfinding(ArrayList<Node> Db) {
    ArrayList<NodePathfinding> nodePList = new ArrayList<NodePathfinding>();
    for (Node node : Db) {
      NodePathfinding newNode = new NodePathfinding(node);
      nodePList.add(newNode);
    }
    return nodePList;
  }
}
