package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.entities.PastMoves;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class PastMovesIDaoImpl implements IDao<PastMoves> {
    private final Session session = DBSingleton.getSession();

    private ArrayList<PastMoves> pastMoves = new ArrayList<>();

    public PastMovesIDaoImpl() {
        this.refresh();
    }

    @Override
    public PastMoves get(PastMoves m) {
        return this.pastMoves.stream()
                .filter(pastMove -> m.getMoveDate().equals(pastMove.getMoveDate()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(PastMoves m) {
        session.beginTransaction();
        try {
            session.persist(m);
            session.getTransaction().commit();

            this.pastMoves.add(m);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void update(PastMoves m) {
        session.beginTransaction();
        try {
            session.merge(m);
            session.getTransaction().commit();

            int index =
                    IntStream.range(0, this.pastMoves.size())
                            .filter(i -> this.pastMoves.get(i).getMoveDate().equals(m.getMoveDate()))
                            .findFirst()
                            .orElse(-1);

            this.pastMoves.remove(index);
            this.pastMoves.add(m);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public ArrayList<PastMoves> getAll() {
        return this.pastMoves;
    }

    @Override
    public void refresh() {
        ArrayList<PastMoves> javaPastMoves;
        session.beginTransaction();
        try {
            javaPastMoves =
                    (ArrayList<PastMoves>) session.createQuery("SELECT m FROM PastMoves m", PastMoves.class).getResultList();
            session.getTransaction().commit();
            this.pastMoves = javaPastMoves;
        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void delete(PastMoves m) {
        session.beginTransaction();
        try {
            Query q = session.createQuery("DELETE PastMoves where id=:id");
            q.setParameter("id", m.getMoveDate());
            int deleted = q.executeUpdate();
            session.getTransaction().commit();

            this.pastMoves.remove(m);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }
}
