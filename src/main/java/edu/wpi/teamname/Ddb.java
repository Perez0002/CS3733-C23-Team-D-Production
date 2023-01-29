package edu.wpi.teamname;

import oracle.ucp.proxy.annotation.Pre;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Ddb {

  private static final File logFile = new File("logfile.txt");
  /**
   * Establishes the connection to the database
   * @return A connection object which is used to access the database
   */
  protected static Connection makeConnection() {
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
  protected static ArrayList<Edge> createJavaEdges(Connection conn, ArrayList<Node> Nodes) {
    ResultSet rset = null;
    ArrayList<Edge> edgeList = new ArrayList<Edge>();
    String statement = "SELECT * FROM Edges";
    boolean startExists;
    boolean endExists;
    try {
      PreparedStatement pstmt = conn.prepareStatement(statement);
      rset = pstmt.executeQuery();
      while (rset.next()) {
        Edge tempEdge = new Edge();
        tempEdge.setStartNode(rset.getString("startnode"));
        tempEdge.setEndNode(rset.getString("endnode"));
        startExists=false;
        endExists=false;
        for (Node node : Nodes) {
          if (tempEdge.getEndNode().equals(node.getNodeID()))
            endExists=true;
          else if (tempEdge.getStartNode().equals(node.getNodeID()))
            startExists=true;
        }
        if(startExists && endExists)
        edgeList.add(tempEdge);
      }
      rset.close();
      return edgeList;
    } catch (SQLException e) {
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
  protected static ArrayList<Node> createJavaNodes(Connection conn) {
    ResultSet rset = null;
    ArrayList<Node> nodeList = new ArrayList<Node>();
    String statement = "SELECT * FROM Nodes";
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
   * Creates the list of moves from the database where each move has nodeID,
   * longName and moveDate fields
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @return A list of all the moves in the database
   */
  protected ArrayList<Move> createJavaMoves(Connection conn){
    ResultSet rset = null;
    ArrayList<Move> moveList = new ArrayList<Move>();
    String statement = "SELECT * FROM Moves";
    try {
      Move tempMove = new Move();
      PreparedStatement pstmt = conn.prepareStatement(statement);
      rset = pstmt.executeQuery();
      while (rset.next()) {
        tempMove.setLongName(rset.getString("nodeID"));
        tempMove.setNodeID(rset.getString("longName"));
        Date checkDate = rset.getDate("moveDate");
        if(!checkDate.equals(tempMove.getMoveDate()))
          tempMove.setMoveDate(checkDate);
        moveList.add(tempMove);
      }
      rset.close();
      return moveList;
    } catch (SQLException e) {
      return moveList;
    }
  }
  /**
   * Creates the list of locationNames from the database where each locationName has
   * longName, locationType and shortName fields
   *
   * @param conn The connection to the DB which allows for queries and updates
   * @return A list of all the locationNames in the database
   */
  protected ArrayList<locationName> createJavaLocat(Connection conn){
    ResultSet rset = null;
    ArrayList<locationName> locationList = new ArrayList<locationName>();
    String statement = "SELECT * FROM LocationNames";
    try {
      locationName tempLoc = new locationName();
      PreparedStatement pstmt = conn.prepareStatement(statement);
      rset = pstmt.executeQuery();
      while (rset.next()) {
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
    String statement = "UPDATE Nodes SET xcoord= ?, ycoord= ?WHERE nodeID = ?";
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
  protected static boolean updateNameOfLocation(Connection conn, String nodeID, locationName curLoc, String name) {
    String statement = "UPDATE locationNames SET longname= ? WHERE longName = ?";
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
  protected static boolean deleteNode(
      Connection conn, Node node, ArrayList<Node> Nodes, ArrayList<Edge> Edges) {
    String deleteNode = "DELETE FROM Nodes WHERE nodeID = ?";
    ArrayList<String> edgesToRemove = new ArrayList<String>();
    try {
      PreparedStatement pstmt = conn.prepareStatement(deleteNode);
      pstmt.setString(1, node.getNodeID());
      pstmt.executeUpdate();
      for (Edge edge : Edges) {
        String deleteEdge = "DELETE FROM Edges WHERE (startNode = ? AND endNode = ?)";
        pstmt = conn.prepareStatement(deleteEdge);
        String startNode= edge.getStartNode();
        String endNode = edge.getEndNode();
        if (startNode.equals(node.getNodeID())) {
          pstmt.setString(1, edge.getStartNode());
          pstmt.setString(2, edge.getEndNode());
          pstmt.executeUpdate();
          Edges.remove(edge);
        } else if (endNode.equals(node.getNodeID())) {
          pstmt.setString(1, edge.getStartNode());
          pstmt.setString(2, edge.getEndNode());
          pstmt.executeUpdate();
          Edges.remove(edge);
        }
        updateLogFile(
                "Deleted edge between"
                        + startNode
                        + " & "
                        + endNode);
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
  protected static boolean deleteEdge(Connection conn, String startNode, String endNode, ArrayList<Edge> Edges) {
    String deleteEdge = "DELETE FROM Edges WHERE (startNode = ? AND endNode = ?)";
    try {
      PreparedStatement pstmt = conn.prepareStatement(deleteEdge);
      pstmt.setString(1, startNode);
      pstmt.setString(1, endNode);
      pstmt.executeUpdate();
      for (Edge edge : Edges) {
        if (edge.getStartNode().equals(startNode) && edge.getEndNode().equals(endNode)) {
          updateLogFile(
                  "Deleted edge between"
                          + startNode
                          + " & "
                          + endNode);
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

  protected ArrayList<PatientTransportData> getJavaPatientForms(Connection conn){
    ResultSet rset = null;
    ArrayList<PatientTransportData> formList = new ArrayList<PatientTransportData>();
    String statement = "SELECT * FROM PatientTransportData";
    try {
      PatientTransportData tempform = new PatientTransportData();
      PreparedStatement pstmt = conn.prepareStatement(statement);
      rset = pstmt.executeQuery();
      while (rset.next()) {
        tempform.setPatientID(rset.getString("patientID"));
        tempform.setStartRoom(rset.getString("startRoom"));
        tempform.setEndRoom(rset.getString("endRoom"));
        tempform.setEquipment((ArrayList<String>) rset.getObject("equipment"));
        tempform.setReason(rset.getString("reason"));
        tempform.setSendTo((String[]) rset.getObject("sendTo"));
        formList.add(tempform);
      }
      rset.close();
      return formList;
    } catch (SQLException e) {
      return formList;
    }
  }

  protected boolean insertNewForm(Connection conn, ArrayList<Object> values){
    String statement = "INSERT INTO PatientTransportData PatientTransportData(patientID,startRoom,endRoom,equipment,reason,sendTo) VALUES(?,?,?,?,?,?)";
    try {
      PreparedStatement pstmnt = conn.prepareStatement(statement);
      for(int i=0; i<values.size();i++){
        if(values.isEmpty()) {
          System.out.println("No values found");
          return true;
        }
        pstmnt.setObject(i,values.get(i));

      }
      pstmnt.executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }
}
