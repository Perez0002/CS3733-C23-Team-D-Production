package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Edge;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class EdgeDaoImpl implements DaoPlus<Edge> {
  private Session session = Ddb.getDBsession();

  @Override
  public Edge get(Edge e) {
    Edge eq = null;
    session.beginTransaction();
    try {
      eq = session.get(Edge.class, e.getEdgeID());
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
    return eq;
  }

  @Override
  public void save(Edge e) {
    session.beginTransaction();
    try {
      session.persist(e);
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void update(Edge e) {
    session.beginTransaction();
    try {
      session.merge(e);
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public List<Edge> getAll() {
    ArrayList<Edge> javaEdges = null;
    session.beginTransaction();
    try {
      javaEdges =
          (ArrayList<Edge>) session.createQuery("SELECT e FROM Edge e", Edge.class).getResultList();
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
    return javaEdges;
  }

  @Override
  public void delete(Edge e) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("DELETE Edge where id=:id");
      q.setParameter("id", e.getEdgeID());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }
}
