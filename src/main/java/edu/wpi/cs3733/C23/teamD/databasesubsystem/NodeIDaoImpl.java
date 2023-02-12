package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.DBSingleton;
import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import jakarta.persistence.Query;
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
    return this.nodes.stream().filter(node -> n.getNodeID().equals(node.getNodeID())).findFirst().orElse(null);
  }

  public Node get(String nodeID) {
    return this.nodes.stream().filter(node -> nodeID.equals(node.getNodeID())).findFirst().orElse(null);
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

      int index = IntStream.range(0, this.nodes.size())
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
}
