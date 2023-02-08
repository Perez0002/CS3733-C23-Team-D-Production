package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class NodeDaoImpl implements DaoPlus<Node> {
  private Session session = Ddb.getDBsession();

  @Override
  public Node get(Node n) {
    Node nq = null;
    session.beginTransaction();
    try {
      nq = session.get(Node.class, n.getNodeID());
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
    return nq;
  }

  public Node get(String nodeID) {
    Node nq = null;
    session.beginTransaction();
    try {
      nq = session.get(Node.class, nodeID);
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
    return nq;
  }

  @Override
  public void save(Node n) {
    session.beginTransaction();
    try {
      session.persist(n);
      session.getTransaction().commit();
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
      session.getTransaction().rollback();
    }
  }

  @Override
  public List<Node> getAll() {
    ArrayList<Node> javaNodes = null;
    session.beginTransaction();
    try {
      javaNodes =
          (ArrayList<Node>) session.createQuery("SELECT n FROM Node n", Node.class).getResultList();
      session.getTransaction().commit();

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
    return javaNodes;
  }

  @Override
  public void delete(Node n) {
    EdgeDaoImpl eDao = new EdgeDaoImpl();
    session.beginTransaction();
    Query q =
        session.createQuery("SELECT e FROM Edge e WHERE fromNode=:fromnode OR toNode=:tonode");
    q.setParameter("fromnode", n);
    q.setParameter("tonode", n);
    ArrayList<Edge> edges = (ArrayList<Edge>) q.getResultList();
    session.getTransaction().commit();
    if (edges.size() > 0) {
      for (Edge oldEdge : edges) {
        eDao.delete(oldEdge);
      }
    }
    session.beginTransaction();
    try {
      Query q2 = session.createQuery("DELETE Node where id=:id");
      q2.setParameter("id", n.getNodeID());
      int deleted = q2.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  public void nodeEdgeSwap(Node oldNode, Node newNode) {
    EdgeDaoImpl eDao = new EdgeDaoImpl();
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
        eDao.delete(oldEdge);
        eDao.save(newEdge);
      } else {
        newEdge = new Edge(oldEdge.getFromNode(), newNode);
        eDao.delete(oldEdge);
        eDao.save(newEdge);
      }
    }
    this.delete(oldNode);
  }
}
