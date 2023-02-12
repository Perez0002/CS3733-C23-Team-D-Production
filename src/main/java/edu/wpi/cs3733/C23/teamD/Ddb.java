package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.entities.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import org.hibernate.Session;

public class Ddb {

  private static final File logFile = new File("logfile.txt");

  @Getter private static Session DBsession = DBSingleton._DB.getSession();

  /**
   * @param Nodes the list of all the nodes from the database
   * @return the list of all the edges stored in the database
   */
  public static ArrayList<Edge> createJavaEdges(ArrayList<Node> Nodes) {
    DBsession.beginTransaction();
    ArrayList<Edge> edgeList =
        new ArrayList<Edge>(DBsession.createQuery("SELECT e FROM Edge e").getResultList());
    for (Edge edge : edgeList) {
      for (Node node : Nodes) {
        if (edge.getFromNode().nodeEquals(node)) {
          node.getNodeEdges().add(edge);
        } else if (edge.getToNode().nodeEquals(node)) {
          node.getNodeEdges().add(edge);
        }
      }
    }
    DBsession.getTransaction().commit();
    return edgeList;
  }

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

  /** @return */
  public static ArrayList<Move> createJavaMoves() {
    DBsession.beginTransaction();
    ArrayList<Move> moveList =
        new ArrayList<Move>(DBsession.createQuery("SELECT m FROM Move m").getResultList());
    DBsession.getTransaction().commit();
    return moveList;
  }

  /** @return */
  public static ArrayList<LocationName> createJavaLocat() {
    DBsession.beginTransaction();
    ArrayList<LocationName> locationNames =
        new ArrayList<LocationName>(
            DBsession.createQuery("SELECT l FROM LocationName l").getResultList());
    DBsession.getTransaction().commit();
    return locationNames;
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

  /**
   * Inserts a new patientTransportForm with the attributes from the given form into the
   * ServiceRequestForm
   *
   * @param form
   * @return
   */
  public static boolean insertNewForm(ServiceRequest form) {
    try {
      DBsession.beginTransaction();
      DBsession.persist(form);
      DBsession.getTransaction().commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      DBsession.getTransaction().rollback();
      return false;
    }
  }

  public static void connectNodestoLocations(ArrayList<Node> nodes) {
    ArrayList<Move> moves = new ArrayList<Move>(createJavaMoves());
    for (Node node : nodes) {
      for (Move move : moves) {
        if (node.nodeEquals(move.getNode())) {
          node.setLocation(move.getLocation());
        }
      }
    }
  }

  /** @return */
  public static ArrayList<PatientTransportRequest> getPatientTransportData() {
    DBsession.beginTransaction();
    ArrayList<PatientTransportRequest> patientTransList =
        new ArrayList<>(
            DBsession.createQuery("SELECT p FROM PatientTransportRequest p").getResultList());
    DBsession.getTransaction().commit();
    return patientTransList;
  }

  public static ArrayList<SanitationRequest> createSanitationRequestList() {
    DBsession.beginTransaction();
    ArrayList<SanitationRequest> sanitationReqList =
        new ArrayList<>(
            DBsession.createQuery("SELECT s FROM SanitationRequest s").getResultList());
    DBsession.getTransaction().commit();
    return sanitationReqList;
  }

  public static ArrayList<ServiceRequest> createServiceList() {
    DBsession.beginTransaction();
    ArrayList<ServiceRequest> serviceRequests =
        new ArrayList<>(
            DBsession.createQuery("SELECT s FROM ServiceRequest s").getResultList());
    DBsession.getTransaction().commit();
    return serviceRequests;
  }

  /**
   * Updates the Object obj in the database
   *
   * @param obj the object to update
   * @return true if the update succeeded or false if not
   */
  public static boolean updateObj(Object obj) {
    try {
      DBsession.beginTransaction();
      DBsession.merge(obj);
      DBsession.getTransaction().commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      DBsession.getTransaction().rollback();
      return false;
    }
  }

  private static boolean deleteObj(Object obj) {
    try {
      DBsession.beginTransaction();
      DBsession.remove(obj);
      DBsession.getTransaction().commit();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      DBsession.getTransaction().rollback();
      return false;
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
        Node node = new Node(Integer.valueOf(data[1]), Integer.valueOf(data[2]), data[3], data[4]);
        node.getNodeID();
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
      ArrayList<Node> nodes = new ArrayList<>(createJavaNodes());
      DBsession.beginTransaction();
      BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePaths));
      String lineText = null;
      lineReader.readLine(); // skip header line
      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        Edge edge = new Edge();
        for (Node node : nodes) {
          if (data[0].equals(node.getNodeID())) {
            edge.setFromNode(node);
          } else if (data[1].equals(node.getNodeID())) {
            edge.setToNode(node);
          }
        }
        edge.genEdgeID();
        System.out.println(edge.getEdgeID());
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

  public boolean addNewNode(Node node) {
    try {
      DBsession.beginTransaction();
      node.setLocation(new LocationName());
      Move move = new Move(node, node.getLocation());
      DBsession.persist(node);
      DBsession.persist(node.getLocation());
      DBsession.persist(move);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      DBsession.getTransaction().rollback();
      return false;
    }
  }
}
