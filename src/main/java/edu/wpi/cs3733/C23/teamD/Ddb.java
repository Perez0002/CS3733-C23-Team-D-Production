package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.entities.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Ddb {

  private static final File logFile = new File("logfile.txt");
  /**
   * Establishes the connection to the database
   *
   * @return A connection object which is used to access the database
   */
  public static Connection makeConnection() {
    Connection conn = null;
    final String url =
        "jdbc:postgresql://wpi-softeng-postgres-db.coyfss2f91ba.us-east-1.rds.amazonaws.com:2112/dbd";
    final String user = "teamd";
    final String pass = "7Lcge2CRNGTYWRgLD57vmz9SBFIfq2C3";
    try {
      conn = DriverManager.getConnection(url, user, pass);
      System.out.println("Success");
    } catch (SQLException e) {
      System.out.println("failed");
    }
    return conn;
  }

  /**
   * Creates the list of edges from the database where each edge has an edgeId, startNode and
   * endNode where the startNode and endNode fields are references to Node objects
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @param Nodes The list of all the nodes in the database, contained locally in a list
   * @return A list of all the edges in the database
   */
  public static ArrayList<Edge> createJavaEdges(Connection conn, ArrayList<Node> Nodes) {
    ResultSet rset = null;
    ArrayList<Edge> edgeList = new ArrayList<Edge>();
    String statement = "SELECT * FROM Edge";
    try {
      PreparedStatement pstmt = conn.prepareStatement(statement);
      rset = pstmt.executeQuery();
      while (rset.next()) {
        Edge tempEdge = new Edge();
        for (Node node : Nodes) {
          if (node.getNodeID().equals(rset.getString("node1"))) tempEdge.setFromNode(node);
          else if (node.getNodeID().equals(rset.getString("node2"))) tempEdge.setToNode(node);
        }
        tempEdge.genCost();
        tempEdge.genEdgeID();
        edgeList.add(tempEdge);
      }
      rset.close();
      return edgeList;
    } catch (SQLException e) {
      e.printStackTrace();
      return edgeList;
    }
  }

  /**
   * Creates the list of nodes from the database where each node has nodeID, xcoord, ycoord, floor,
   * building, nodeType, longName and shortName fields
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @return A list of all the nodes in the database
   */
  public static ArrayList<Node> createJavaNodes(Connection conn) {
    ResultSet rset = null;
    ArrayList<Node> nodeList = new ArrayList<Node>();
    String statement = "SELECT * FROM Node";
    try {
      PreparedStatement pstmt = conn.prepareStatement(statement);
      rset = pstmt.executeQuery();
      while (rset.next()) {
        Node tempNode = new Node();
        tempNode.setNodeID(rset.getString("nodeID"));
        tempNode.setXcoord(rset.getInt("xcoord"));
        tempNode.setYcoord(rset.getInt("ycoord"));
        tempNode.setFloor(rset.getString("floor"));
        tempNode.setBuilding(rset.getString("building"));
        nodeList.add(tempNode);
      }
      rset.close();
      return nodeList;
    } catch (SQLException e) {
      return nodeList;
    }
  }

  /**
   * Creates the list of moves from the database where each move has nodeID, longName and moveDate
   * fields
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @return A list of all the moves in the database
   */
  public static ArrayList<Move> createJavaMoves(Connection conn) {
    ResultSet rset = null;
    ArrayList<Move> moveList = new ArrayList<Move>();
    String statement = "SELECT * FROM Move";
    try {
      PreparedStatement pstmt = conn.prepareStatement(statement);
      rset = pstmt.executeQuery();
      while (rset.next()) {
        Move tempMove = new Move();
        tempMove.setLongName(rset.getString("nodeID"));
        tempMove.setNodeID(rset.getString("longName"));
        Date checkDate = rset.getDate("moveDate");
        if (!checkDate.equals(tempMove.getMoveDate())) tempMove.setMoveDate(checkDate);
        moveList.add(tempMove);
      }
      rset.close();
      return moveList;
    } catch (SQLException e) {
      return moveList;
    }
  }
  /**
   * Creates the list of locationNames from the database where each locationName has longName,
   * locationType and shortName fields
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @return A list of all the locationNames in the database
   */
  public static ArrayList<locationName> createJavaLocat(Connection conn) {
    ResultSet rset = null;
    ArrayList<locationName> locationList = new ArrayList<locationName>();
    String statement = "SELECT * FROM LocationName";
    try {
      PreparedStatement pstmt = conn.prepareStatement(statement);
      rset = pstmt.executeQuery();
      while (rset.next()) {
        locationName tempLoc = new locationName();
        tempLoc.setLongName(rset.getString("longName"));
        tempLoc.setShortName(rset.getString("shortName"));
        tempLoc.setLocationType(rset.getString("locationType"));
        locationList.add(tempLoc);
      }
      rset.close();
      return locationList;
    } catch (SQLException e) {
      return locationList;
    }
  }

  /**
   * Updates the x-coordinate and y-coordinate locally and on the database
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @param node The node that will have its coordinates changed
   * @param xcoord The new xcoord value
   * @param ycoord The new ycoord value
   * @return true if updating the database succeeded and false if it failed
   */
  protected static boolean updateNodeCoords(Connection conn, Node node, int xcoord, int ycoord) {
    String statement = "UPDATE Node SET xcoord= ?, ycoord= ?WHERE nodeID = ?";
    try {
      PreparedStatement pstmt = conn.prepareStatement(statement);
      pstmt.setInt(1, xcoord);
      pstmt.setInt(2, ycoord);
      pstmt.setString(3, node.getNodeID());
      pstmt.executeUpdate();
      updateLogFile(
          "Changed node with nodeID: "
              + node.getNodeID()
              + " xcoord: "
              + node.getXcoord()
              + " ycoord: "
              + node.getYcoord()
              + " newX: "
              + xcoord
              + " newY: "
              + ycoord);
      node.setXcoord(xcoord);
      node.setYcoord(ycoord);
      statement = "INSERT Move SET nodeID= ?, longName= ? movedate = ?";
      pstmt = conn.prepareStatement(statement);
      pstmt.setString(1, node.getNodeID());
      pstmt.setString(2, node.getLocation().getLongName());
      java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
      pstmt.setDate(3, date);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Updates the longName locally and on the database
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @param nodeID The ID of the node that will have it's longName changed
   * @param curLoc The locationName of the node that needs to be changed
   * @param name The new name that
   * @return true if updating the database succeeded and false if it failed
   */
  protected static boolean updateNameOfLocation(
      Connection conn, String nodeID, locationName curLoc, String name) {
    String statement = "UPDATE locationName SET longname= ? WHERE longName = ?";
    try {
      PreparedStatement pstmt = conn.prepareStatement(statement);
      pstmt.setString(1, name);
      pstmt.setString(2, curLoc.getLongName());
      pstmt.executeUpdate();
      updateLogFile(
          "Updated node with nodeID: "
              + nodeID
              + " oldlongname: "
              + curLoc.getLongName()
              + " new longname: "
              + name);
      curLoc.setLongName(name);
      return true;

    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Deletes a node and deletes any edges it has with other nodes from the database and from the
   * lists of nodes and edges
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @param node The node that will have it's longName changed
   * @param Nodes The list of all the nodes in the database, contained locally in a list
   * @param Edges The list of all the edges in the database, contained locally in a list
   * @return true if updating the database succeeded and false if it failed
   */
  private static boolean deleteNode(
      Connection conn, Node node, ArrayList<Node> Nodes, ArrayList<Edge> Edges) {
    String deleteNode = "DELETE FROM Node WHERE nodeID = ?";
    ArrayList<String> edgesToRemove = new ArrayList<String>();
    try {
      PreparedStatement pstmt = conn.prepareStatement(deleteNode);
      pstmt.setString(1, node.getNodeID());
      pstmt.executeUpdate();
      for (Edge edge : Edges) {
        String deleteEdge = "DELETE FROM Edges WHERE (startNode = ? AND endNode = ?)";
        pstmt = conn.prepareStatement(deleteEdge);
        String startNode = edge.getFromNode().getNodeID();
        String endNode = edge.getToNode().getNodeID();
        if (startNode.equals(node.getNodeID())) {
          pstmt.setString(1, startNode);
          pstmt.setString(2, endNode);
          pstmt.executeUpdate();
          Edges.remove(edge);
        } else if (endNode.equals(node.getNodeID())) {
          pstmt.setString(1, startNode);
          pstmt.setString(2, endNode);
          pstmt.executeUpdate();
          Edges.remove(edge);
        }
        updateLogFile("Deleted edge between" + startNode + " & " + endNode);
      }
      updateLogFile("Deleted " + " " + node.getAll());
      Nodes.remove(node);
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Deletes an edge from the database and from the list of edges
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @param startNode The startNode of the edge to be deleted
   * @param endNode The endNode of the edge to be deleted
   * @param Edges The list of all the edges in the database, contained locally in a list
   * @return true if updating the database succeeded and false if it failed
   */
  private static boolean deleteEdge(
      Connection conn, String startNode, String endNode, ArrayList<Edge> Edges) {
    String deleteEdge = "DELETE FROM Edges WHERE (startNode = ? AND endNode = ?)";
    try {
      PreparedStatement pstmt = conn.prepareStatement(deleteEdge);
      pstmt.setString(1, startNode);
      pstmt.setString(1, endNode);
      pstmt.executeUpdate();
      for (Edge edge : Edges) {
        if (edge.getFromNode().equals(startNode) && edge.getToNode().equals(endNode)) {
          updateLogFile("Deleted edge between" + startNode + " & " + endNode);
          Edges.remove(edge);
          return true;
        }
      }
      return false;
    } catch (SQLException e) {
      return false;
    }
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

  public static boolean insertNewForm(Connection conn, PatientTransportData form) {
    String statement =
        "INSERT INTO PatientTransportData(patientID,startRoom,endRoom,equipment,reason,sendTo,status) VALUES(?,?,?,?,?,?,CAST(? AS STAT))";
    try {
      PreparedStatement pstmnt = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
      pstmnt.setString(1, form.getPatientID());
      pstmnt.setString(2, form.getStartRoom());
      pstmnt.setString(3, form.getEndRoom());
      pstmnt.setString(4, String.join(",", form.getEquipment()));
      pstmnt.setString(5, form.getReason());
      pstmnt.setString(6, String.join(",", form.getSendTo()));
      pstmnt.setString(7, form.getStat().toString());
      pstmnt.executeUpdate();
      ResultSet id = pstmnt.getGeneratedKeys();
      id.next();
      form.setPatientTransportID(id.getInt(1));
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  protected static ArrayList<PatientTransportData> getPatientTransportData(Connection conn) {
    String statement = "SELECT * FROM PatientTransportData";
    ArrayList<PatientTransportData> transportList = new ArrayList<PatientTransportData>();
    try {
      PreparedStatement pstmnt = conn.prepareStatement(statement);
      ResultSet rset = pstmnt.executeQuery();
      while (rset.next()) {
        PatientTransportData transportForm = new PatientTransportData();
        transportForm.setPatientID(rset.getString("patientID"));
        transportForm.setPatientTransportID(rset.getInt("patienttransportid"));
        transportForm.setStartRoom(rset.getString("startroom"));
        transportForm.setEndRoom(rset.getString("endroom"));
        List<String> stringList = Arrays.asList((rset.getString("equipment")).split(","));
        ArrayList<String> strings = new ArrayList<>(stringList);
        transportForm.setEquipment(strings);
        transportForm.setSendTo((rset.getString("sendTo")).split(","));
        transportForm.setReason(rset.getString("reason"));
        transportForm.setStat(PatientTransportData.status.valueOf(rset.getString("status")));
        transportList.add(transportForm);
      }
      return transportList;
    } catch (SQLException e) {
      return null;
    }
  }

  public static void updateObjString(Connection conn, String stmnt, String pk, String newThing) {
    try {
      PreparedStatement pstmnt;
      pstmnt = conn.prepareStatement(stmnt);
      pstmnt.setString(1, newThing);
      pstmnt.setString(2, pk);
      pstmnt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void updateObjInt(Connection conn, String stmnt, String pk, int newThing) {
    try {
      PreparedStatement pstmnt;
      pstmnt = conn.prepareStatement(stmnt);
      pstmnt.setInt(1, newThing);
      pstmnt.setString(2, pk);
      pstmnt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void csv2DBInsertions(String tablename, String csvFilePath) {
    Connection conn = makeConnection();
    try {
      conn.setAutoCommit(false);
      String sql = "";
      if (tablename.equals("Node")) {
        sql = "INSERT INTO node values (?, ?, ?, ?, ?)";
      } else if (tablename.equals("Edge")) {
        sql = "INSERT INTO edge values (?, ?)";
      } else if (tablename.equals("LocationName")) {
        sql = "INSERT INTO locationname values (?, ?, ?)";
      } else {
        System.out.println("The table does not exist.");
      }

      PreparedStatement statement = conn.prepareStatement(sql);
      BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
      String lineText = null;
      int count = 0;
      lineReader.readLine(); // skip header line

      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        if (tablename.equals("Node")) {
          String nodeID = data[0];
          String xcoord = data[1];
          String ycoord = data[2];
          String floor = data[3];
          String building = data[4];

          statement.setString(1, nodeID);
          statement.setInt(2, Integer.parseInt(xcoord));
          statement.setInt(3, Integer.parseInt(ycoord));
          statement.setString(4, floor);
          statement.setString(5, building);

          statement.addBatch();

          if (count % 20 == 0) {
            statement.executeBatch();
          }
        } else if (tablename.equals("Edge")) {
          String node1 = data[0];
          String node2 = data[1];

          statement.setString(1, node1);
          statement.setString(2, node2);

          statement.addBatch();

          if (count % 20 == 0) {
            statement.executeBatch();
          }
        } else if (tablename.equals("LocationName")) {
          String longName = data[6];
          String shortName = data[7];
          String nodeType = data[5];

          statement.setString(1, longName);
          statement.setString(2, shortName);
          statement.setString(3, nodeType);

          statement.addBatch();

          if (count % 20 == 0) {
            statement.executeBatch();
          }
        }
      }
      lineReader.close();
      // execute the remaining queries
      statement.executeBatch();
      System.out.println("Data entered successfully.");

      // closing connection
      conn.commit();
      conn.close();

    } catch (IOException ex) {
      System.err.println(ex);
    } catch (SQLException ex) {
      ex.printStackTrace();
      try {
        conn.rollback();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
