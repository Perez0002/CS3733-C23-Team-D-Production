package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import jakarta.persistence.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.IntStream;
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

      int index =
          IntStream.range(0, this.nodes.size())
              .filter(i -> this.nodes.get(i).getNodeID().equals(n.getNodeID()))
              .findFirst()
              .orElse(-1);

      this.nodes.remove(index);
      this.nodes.add(n);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
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
      Query q2 = session.createQuery("DELETE Node where id=:id");
      q2.setParameter("id", n.getNodeID());
      int deleted = q2.executeUpdate();
      session.getTransaction().commit();

      this.nodes.remove(n);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  public void nodeEdgeSwap(Node oldNode, Node newNode) {
    FDdb dbFacade = FDdb.getInstance();

    this.save(newNode);

    session.beginTransaction();
    Query q =
        session.createQuery("SELECT e FROM Edge e WHERE fromNode=:fromnode OR toNode=:tonode");
    q.setParameter("fromnode", oldNode);
    q.setParameter("tonode", oldNode);
    ArrayList<Edge> edges = (ArrayList<Edge>) q.getResultList();
    session.getTransaction().commit();

    for (Edge oldEdge : edges) {
      Edge newEdge;
      if (oldEdge.getFromNode().equals(oldNode)) {
        newEdge = new Edge(newNode, oldEdge.getToNode());
      } else {
        newEdge = new Edge(oldEdge.getFromNode(), newNode);
      }
      dbFacade.deleteEdge(oldEdge);
      dbFacade.saveEdge(newEdge);
    }

    this.delete(oldNode);
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
      query = session.createQuery("DELETE FROM Move");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM LocationName ");
      query.executeUpdate();
      query = session.createQuery("DELETE FROM Node");
      query.executeUpdate();
      session.getTransaction().commit();
      while (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        Node n = new Node();
        n.setNodeID(data[0]);
        n.setXcoord(Integer.parseInt(data[1]));
        n.setYcoord(Integer.parseInt(data[2]));
        n.setFloor(data[3]);
        n.setBuilding(data[4]);
        FDdb.getInstance().saveNode(n);
      }
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void downloadCSV(Node node) {
    try {
      FileWriter fw =
              new FileWriter("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Node.csv", false);
      PrintWriter pw = new PrintWriter(fw, false);
      pw.flush();
      pw.close();
      fw.close();
      BufferedWriter fileWriter =
          new BufferedWriter(
              new FileWriter("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Node.csv"));
      for (Node n : this.nodes) {
        String oneObject =
            String.join(
                ",",
                n.getNodeID(),
                Integer.toString(n.getXcoord()),
                Integer.toString(n.getYcoord()),
                n.getFloor(),
                n.getBuilding());
        fileWriter.write(oneObject);
        fileWriter.newLine();
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
