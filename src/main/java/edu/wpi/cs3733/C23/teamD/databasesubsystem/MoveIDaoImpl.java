package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class MoveIDaoImpl implements IDao<Move> {
  private final Session session = Ddb.getDBsession();

  private ArrayList<Move> moves = new ArrayList<>();

  public MoveIDaoImpl() {
    this.refresh();
  }
  @Override
  public Move get(Move m) {
    return this.moves.stream().filter(move -> m.getMoveDate().equals(move.getMoveDate())).findFirst().orElse(null);
  }

  @Override
  public void save(Move m) {
    session.beginTransaction();
    try {
      session.persist(m);
      session.getTransaction().commit();

      this.moves.add(m);

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

      int index = IntStream.range(0, this.moves.size())
              .filter(i -> this.moves.get(i).getMoveDate().equals(m.getMoveDate()))
              .findFirst()
              .orElse(-1);

      this.moves.remove(index);
      this.moves.add(m);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public ArrayList<Move> getAll() {
    return this.moves;
  }

  @Override
  public void refresh() {
    ArrayList<Move> javaMoves;
    session.beginTransaction();
    try {
      javaMoves =
              (ArrayList<Move>) session.createQuery("SELECT m FROM Move m", Move.class).getResultList();
      session.getTransaction().commit();
      this.moves = javaMoves;
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void delete(Move m) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("DELETE Move where id=:id");
      q.setParameter("id", m.getMoveDate());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      this.moves.remove(m);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }
}
