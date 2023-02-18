package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import jakarta.persistence.Query;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.IntStream;
import org.hibernate.Session;

public class MoveIDaoImpl implements IDao<Move> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<Move> moves = new ArrayList<>();

  public MoveIDaoImpl() {
    this.refresh();
  }

  @Override
  public Move get(Move m) {
    return this.moves.stream()
        .filter(move -> m.getMoveDate().equals(move.getMoveDate()))
        .findFirst()
        .orElse(null);
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
      //      this.delete(m);
      //      this.save(m);
      session.merge(m);

      int index =
          IntStream.range(0, this.moves.size())
              .filter(i -> this.moves.get(i).getMoveDate().equals(m.getMoveDate()))
              .findFirst()
              .orElse(-1);

      //      this.moves.remove(index);
      //      this.moves.add(m);

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
      Query q = session.createQuery("DELETE Move where moveDate=:id");
      q.setParameter("id", m.getMoveDate());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      this.moves.remove(m);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void uploadCSV(Move move) {
    try {
      BufferedReader fileReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Move.csv"));
      session.beginTransaction();
      session.createQuery("DELETE FROM Move");
      session.getTransaction().commit();
      DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
      while (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        Node curNode = new Node();
        LocationName curLocat = new LocationName();
        for (Node node : FDdb.getInstance().getAllNodes()) {
          if (node.getNodeID().equals(data[0])) {
            curNode = node;
            break;
          }
        }
        for (LocationName locat : FDdb.getInstance().getAllLocationNames()) {
          if (locat.getLongName().equals(data[1])) {
            curLocat = locat;
            break;
          }
        }
        Move m = new Move(curNode, curLocat, format.parse(data[2]));
        FDdb.getInstance().saveMove(m);
      }
      fileReader.close();
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void downloadCSV(Move move) {
    try {
      File file = new File("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Move.csv");
      FileWriter fileWriter = new FileWriter(file, false);
      DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
      for (Move m : this.moves) {
        String oneObject =
            String.join(",", m.getNodeID(), m.getLongName(), format.format(m.getMoveDate()));
        fileWriter.write(oneObject + "\n");
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Move> getMovesFromDate(Date date) {
    session.beginTransaction();
    Query q = session.createQuery("SELECT from Move where moveDate > :now");
    q.setParameter("now", date);
    ArrayList<Move> moves = new ArrayList<Move>(q.getResultList());
    session.getTransaction().commit();
    return moves;
  }
  public ArrayList<Move> getCertainMoveFromDate(Date date, LocationName loc) {
    session.beginTransaction();
    Query q = session.createQuery("SELECT from Move where moveDate > :now where location = :thislocation");
    q.setParameter("now", date);
    q.setParameter("thislocation", loc.getLongName());
    ArrayList<Move> moves = new ArrayList<Move>(q.getResultList());
    session.getTransaction().commit();
    return moves;
  }
}
