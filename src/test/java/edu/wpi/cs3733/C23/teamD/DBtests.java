package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.DBSingleton;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.*;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.PatientTransportRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.SanitationRequest;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.ServiceRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

  @Test
  public void edgeConstructors() {
    Node node = new Node(1, 1, "floor", "building");
    Node node1 = new Node(2, 2, "floor", "building");
    Edge edge = new Edge();
    Edge edge1 = new Edge(node1, node);
    edge.setToNode(edge1.getToNode());
    edge.setFromNode(edge1.getFromNode());
    edge.genEdgeID();
    edge.genCost();
    Assertions.assertEquals(edge, edge1);
  }

  @Test
  public void moveConstructors() {
    Node node = new Node(1, 1, "floor", "building");
    LocationName loc1 = new LocationName("Anasthesia", "room1", "DoctorRoom");
    Move move = new Move(node, loc1, new Date());
    Move move1 = new Move();
    move1.setNode(move.getNode());
    move1.setLocation(move.getLocation());
    move1.setMoveDate(move.getMoveDate());
    Assertions.assertEquals(move1, move);
  }

  @Test
  public void locNameConstructors() {
    Node node = new Node(1, 1, "floor", "building");
    Node node1 = new Node(2, 2, "floor", "building");
    List<Move> moves = new ArrayList<Move>();
    LocationName loc = new LocationName();
    LocationName loc1 = new LocationName("Anasthesia", "room1", "DoctorRoom");
    loc.setLongName(loc1.getLongName());
    loc.setShortName(loc1.getShortName());
    loc.setLocationType(loc1.getLocationType());
    loc.setMoves(moves);
    loc1.setMoves(moves);
    Assertions.assertEquals(loc1.getLocationType(), loc.getLocationType());
    Assertions.assertEquals(loc1.getShortName(), loc.getShortName());
    Assertions.assertEquals(loc1.getLongName(), loc.getLongName());
    Assertions.assertEquals(loc1.getMoves(), loc.getMoves());
    node.setLocation(loc);
    node1.setLocation(loc);
    Assertions.assertEquals(node.getLocationType(), node1.getLocationType());
    Assertions.assertEquals(node.getLongName(), node1.getLongName());
    Assertions.assertEquals(node.getShortName(), node1.getShortName());
  }

  @Test
  public void patientTransportDataTest() {
    PatientTransportRequest transport = new PatientTransportRequest();
    PatientTransportRequest transport1 =
        new PatientTransportRequest(
            0,
            "patient1",
            "room1",
            "room2",
            "defib",
            "for testing purposes",
            "thisperson;thisperson2",
            ServiceRequest.Status.PROCESSING,
            new Date());
    transport.setPatientID(transport1.getPatientID());
    transport.setStat(transport1.getStat());
    transport.setReason(transport1.getReason());
    transport.setEndRoom(transport1.getEndRoom());
    transport.setStartRoom(transport1.getStartRoom());
    transport.setEquipment(transport1.getEquipment());
    transport.setAssociatedStaff(transport1.getAssociatedStaff());
    transport.setServiceRequestType(transport1.getServiceRequestType());
    transport.setDateAndTime(transport1.getDateAndTime());
    transport.setServiceRequestId(transport1.getServiceRequestId());
    Assertions.assertEquals(transport1.getStat(), transport.getStat());
    Assertions.assertEquals(transport1.getDateAndTime(), transport.getDateAndTime());
    Assertions.assertEquals(transport1.getServiceRequestId(), transport.getServiceRequestId());
    Assertions.assertEquals(transport1.getServiceRequestType(), transport.getServiceRequestType());
    Assertions.assertEquals(transport1.getEquipment(), transport.getEquipment());
    Assertions.assertEquals(transport1.getStartRoom(), transport.getStartRoom());
    Assertions.assertEquals(transport1.getEndRoom(), transport.getEndRoom());
    Assertions.assertEquals(transport1.getReason(), transport.getReason());
    Assertions.assertEquals(transport1.getPatientID(), transport.getPatientID());
  }

  @Test
  public void sanitationRequestDatatests() {
    SanitationRequest sanitation = new SanitationRequest();
    SanitationRequest sanitation1 =
        new SanitationRequest(
            0,
            "location1",
            "for testing purposes",
            1,
            "thisperson;thisperson2",
            ServiceRequest.Status.PROCESSING,
            new Date());
    sanitation.setStat(sanitation1.getStat());
    sanitation.setReason(sanitation1.getReason());
    sanitation.setBioLevel(sanitation1.getBioLevel());
    sanitation.setLocation(sanitation1.getLocation());
    sanitation.setAssociatedStaff(sanitation1.getAssociatedStaff());
    sanitation.setServiceRequestType(sanitation1.getServiceRequestType());
    sanitation.setDateAndTime(sanitation1.getDateAndTime());
    sanitation.setServiceRequestId(sanitation1.getServiceRequestId());
    Assertions.assertEquals(sanitation1.getStat(), sanitation.getStat());
    Assertions.assertEquals(sanitation1.getDateAndTime(), sanitation.getDateAndTime());
    Assertions.assertEquals(sanitation1.getServiceRequestId(), sanitation.getServiceRequestId());
    Assertions.assertEquals(
        sanitation1.getServiceRequestType(), sanitation.getServiceRequestType());
    Assertions.assertEquals(sanitation1.getReason(), sanitation.getReason());
    Assertions.assertEquals(sanitation1.getBioLevel(), sanitation.getBioLevel());
    Assertions.assertEquals(sanitation1.getLocation(), sanitation.getLocation());
  }

  @Test
  public void testCSVConversion() {
    try {
      Ddb.oldCSVtoNewCSV();
      BufferedReader readEdges2 =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/edges2.csv"));
      BufferedReader readEdges3 =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/edges3.csv"));
      String lineText2 = null;
      String lineText3 = null;
      readEdges3.readLine();
      readEdges2.readLine();
      while (((lineText2 = readEdges2.readLine()) != null)
          && ((lineText3 = readEdges3.readLine()) != null)) {
        Assertions.assertEquals(lineText2, lineText3);
      }
      readEdges3.close();
      readEdges2.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void createNodesandEdges() {

    DBSingleton.getSession().beginTransaction();
    ArrayList<Edge> edgeList =
        new ArrayList<Edge>(
            DBSingleton.getSession().createQuery("SELECT e FROM Edge e").getResultList());

    ArrayList<Node> Nodes =
        new ArrayList<Node>(
            DBSingleton.getSession().createQuery("SELECT n FROM Node n").getResultList());
    for (Edge edge : edgeList) {
      for (Node node : Nodes) {
        if (edge.getFromNode().nodeEquals(node)) {
          node.getNodeEdges().add(edge);
        } else if (edge.getToNode().nodeEquals(node)) {
          node.getNodeEdges().add(edge);
        }
      }
    }
    DBSingleton.getSession().getTransaction().commit();
    ArrayList<Node> nodeList = new ArrayList<>(FDdb.getInstance().getAllNodes());
    Assertions.assertEquals(Nodes, nodeList);
    Assertions.assertEquals(edgeList, FDdb.getInstance().getAllEdges());
  }
}
