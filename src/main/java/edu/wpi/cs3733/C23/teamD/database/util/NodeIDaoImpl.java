package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.database.entities.*;
import jakarta.persistence.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Session;

public class NodeIDaoImpl implements IDao<Node> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<Node> nodes = new ArrayList<>();

  public NodeIDaoImpl() {
    this.refresh();
  }

  @Override
  public Node get(Node n) {
    return this.nodes.stream()
        .filter(node -> n.getNodeID().equals(node.getNodeID()))
        .findFirst()
        .orElse(null);
  }

  public Node get(String nodeID) {
    return this.nodes.stream()
        .filter(node -> nodeID.equals(node.getNodeID()))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void save(Node n) {
    session.beginTransaction();
    try {
      session.persist(n);
      session.getTransaction().commit();

      this.nodes.add(n);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void update(Node n) {
    session.beginTransaction();
    try {
      session.merge(n);
      session.getTransaction().commit();
    } catch (Exception ex) {
      ex.printStackTrace();
      session.getTransaction().rollback();
    }
  }

  public ArrayList<Object> updatePK(Node n) {
    ArrayList<Object> objectList = new ArrayList<>();

    Node newNode = new Node(n);
    newNode.setNodeID();
    this.save(newNode);
    objectList.addAll(this.nodeMoveSwap(n, newNode));
    objectList.addAll(this.nodeEdgeSwap(n, newNode));
    //    System.out.println("Node ID: " + n.getNodeID());
    this.deleteOnlyNode(n);
    objectList.add(newNode);

    return objectList;
  }

  @Override
  public ArrayList<Node> getAll() {
    return this.nodes;
  }

  @Override
  public void refresh() {
    ArrayList<Node> javaNodes;
    session.beginTransaction();
    try {
      javaNodes =
          (ArrayList<Node>) session.createQuery("SELECT n FROM Node n", Node.class).getResultList();
      session.getTransaction().commit();
      this.nodes = javaNodes;
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void delete(Node n) {
    FDdb dbFacade = FDdb.getInstance();
    ArrayList<Move> movesWithNode = new ArrayList<>();
    for (Move m : dbFacade.getAllMoves()) {
      if (m.getNode().nodeEquals(n)) {
        movesWithNode.add(m);
      }
    }
    for (Move m : movesWithNode) {
      PastMoves tempMove = new PastMoves(n.getNodeID(), m.getLongName(), m.getMoveDate());
      dbFacade.savePastMove(tempMove);
      dbFacade.deleteMove(m);
    }

    session.beginTransaction();
    Query q =
        session.createQuery("SELECT e FROM Edge e WHERE fromNode=:fromnode OR toNode=:tonode");
    q.setParameter("fromnode", n);
    q.setParameter("tonode", n);
    ArrayList<Edge> edges = (ArrayList<Edge>) q.getResultList();
    session.getTransaction().commit();
    if (edges.size() > 0) {
      for (Edge oldEdge : edges) {
        dbFacade.deleteEdge(oldEdge);
      }
    }

    session.beginTransaction();
    try {
      Query q2 = session.createQuery("DELETE Node where nodeID=:id");
      q2.setParameter("id", n.getNodeID());
      int deleted = q2.executeUpdate();
      session.getTransaction().commit();
      //      System.out.println("node being deleted" + n.getNodeID());
      this.nodes.remove(n);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  public ArrayList<Edge> nodeEdgeSwap(Node oldNode, Node newNode) {
    FDdb dbFacade = FDdb.getInstance();

    ArrayList<Edge> edgeList = new ArrayList<>();
    session.beginTransaction();
    Query q =
        session.createQuery("SELECT e FROM Edge e WHERE fromNode=:fromnode OR toNode=:tonode");
    q.setParameter("fromnode", oldNode);
    q.setParameter("tonode", oldNode);
    ArrayList<Edge> edges = (ArrayList<Edge>) q.getResultList();
    session.getTransaction().commit();

    for (Edge oldEdge : edges) {
      Edge newEdge;
      if (oldEdge.getFromNode().nodeEquals(oldNode)) {
        newEdge = new Edge(newNode, oldEdge.getToNode());
      } else if (oldEdge.getToNode().nodeEquals(oldNode)) {
        newEdge = new Edge(oldEdge.getFromNode(), newNode);
      } else {
        newEdge = new Edge();
      }
      dbFacade.deleteEdge(oldEdge);
      dbFacade.saveEdge(newEdge);
      edgeList.add(newEdge);
    }
    return edgeList;
  }

  private void deleteOnlyNode(Node n) {
    session.beginTransaction();
    try {
      Query q2 = session.createQuery("DELETE Node where nodeID=:id");
      q2.setParameter("id", n.getNodeID());
      int deleted = q2.executeUpdate();
      session.getTransaction().commit();

      this.nodes.remove(n);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  private ArrayList<Move> nodeMoveSwap(Node oldNode, Node newNode) {
    ArrayList<Move> moveList = new ArrayList<>();
    try {
      FDdb dbFacade = FDdb.getInstance();
      ArrayList<Move> moves = new ArrayList<>(dbFacade.getAllMoves());
      for (int i = 0; i < moves.size(); i++) {
        Move m = moves.get(i);
        if (moves.get(i).getNode().nodeEquals(oldNode)) {
          Move move = new Move(newNode, m.getLocation(), m.getMoveDate());
          moveList.add(move);
          FDdb.getInstance().deleteMove(m);
          FDdb.getInstance().saveMove(move);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return moveList;
  }

  @Override
  public void uploadCSV(Node node) {
    try {
      BufferedReader fileReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Node.csv"));
      session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("DELETE FROM Edge");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM SecurityServiceRequest ");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM ComputerServiceRequest ");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM SanitationRequest ");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM AVRequest ");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM PatientTransportRequest ");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM ServiceRequest");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM Move");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM LocationName ");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM Node");
      query.executeUpdate();
      session.getTransaction().commit();
      Node n = new Node();
      while (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        n = new Node();
        n.setNodeID(data[0]);
        n.setXcoord(Integer.parseInt(data[1]));
        n.setYcoord(Integer.parseInt(data[2]));
        n.setFloor(data[3]);
        n.setBuilding(data[4]);
        this.save(n);
      }
      FDdb.getInstance().saveNode(n);
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void downloadCSV(Node node) {
    try {
      File file = new File("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Node.csv");
      FileWriter fileWriter = new FileWriter(file, false);
      for (Node n : this.nodes) {
        String oneObject =
            String.join(
                ",",
                n.getNodeID(),
                String.format("%04d", n.getXcoord()),
                String.format("%04d", n.getYcoord()),
                n.getFloor(),
                n.getBuilding());
        fileWriter.write(oneObject + "\n");
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Node getAssociatedNode(LocationName loc) {
    FDdb dbFacade = FDdb.getInstance();
    ArrayList<Move> moves = dbFacade.getAllCurrentMoves(new Date());
    for (Move m : moves) {
      if (m.getLocation().getLongName().equals(loc.getLongName())) {
        return m.getNode();
      }
    }
    return null;
  }
}
