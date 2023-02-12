package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.DBSingleton;
import edu.wpi.cs3733.C23.teamD.entities.Edge;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class EdgeIDaoImpl implements IDao<Edge> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<Edge> edges = new ArrayList<>();

  public EdgeIDaoImpl() {
    this.refresh();
  }

  @Override
  public Edge get(Edge e) {
    return this.edges.stream().filter(edge -> e.getEdgeID().equals(edge.getEdgeID())).findFirst().orElse(null);
  }

  @Override
  public void save(Edge e) {
    session.beginTransaction();
    try {
      session.persist(e);
      session.getTransaction().commit();

      this.edges.add(e);

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

      int index = IntStream.range(0, this.edges.size())
              .filter(i -> this.edges.get(i).getEdgeID().equals(e.getEdgeID()))
              .findFirst()
              .orElse(-1);

      this.edges.remove(index);
      this.edges.add(e);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public ArrayList<Edge> getAll() {
    return this.edges;
  }

  @Override
  public void refresh() {
    ArrayList<Edge> javaEdges;
    session.beginTransaction();
    try {
      javaEdges =
              (ArrayList<Edge>) session.createQuery("SELECT e FROM Edge e", Edge.class).getResultList();
      session.getTransaction().commit();
      this.edges = javaEdges;
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void delete(Edge e) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("DELETE Edge where id=:id");
      q.setParameter("id", e.getEdgeID());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      this.edges.remove(e);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }
}
