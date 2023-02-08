package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class MoveDaoImpl implements DaoPlus<Move> {
  private Session session = Ddb.getDBsession();

  @Override
  public Move get(Move m) {
    Move mq = null;
    session.beginTransaction();
    try {
      mq = session.get(Move.class, m.getMoveDate());
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
    return mq;
  }

  @Override
  public void save(Move m) {
    session.beginTransaction();
    try {
      session.persist(m);
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void update(Move m) {
    session.beginTransaction();
    try {
      session.merge(m);
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public List<Move> getAll() {
    ArrayList<Move> javaMoves = null;
    session.beginTransaction();
    try {
      javaMoves =
          (ArrayList<Move>) session.createQuery("SELECT m FROM Move m", Move.class).getResultList();
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
    return javaMoves;
  }

  @Override
  public void delete(Move m) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("DELETE Move where id=:id");
      q.setParameter("id", m.getMoveDate());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }
}
