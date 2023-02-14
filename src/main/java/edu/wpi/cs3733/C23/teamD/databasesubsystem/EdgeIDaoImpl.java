package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.entities.Edge;
import edu.wpi.cs3733.C23.teamD.entities.Node;
import jakarta.persistence.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.hibernate.Session;

public class EdgeIDaoImpl implements IDao<Edge> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<Edge> edges = new ArrayList<>();

  public EdgeIDaoImpl() {
    this.refresh();
  }

  @Override
  public Edge get(Edge e) {
    return this.edges.stream()
        .filter(edge -> e.getEdgeID().equals(edge.getEdgeID()))
        .findFirst()
        .orElse(null);
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

      int index =
          IntStream.range(0, this.edges.size())
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
      Query q = session.createQuery("DELETE Edge where edgeID=:id");
      q.setParameter("id", e.getEdgeID());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      this.edges.remove(e);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void uploadCSV(Edge edge) {
    try {
      BufferedReader fileReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Edge.csv"));
      session.beginTransaction();
      session.createQuery("DELETE FROM Edge");
      session.getTransaction().commit();
      while (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        Edge e = new Edge();
        e.setEdgeID(data[0]);
        for (Node node : FDdb.getInstance().getAllNodes()) {
          if (node.getNodeID().equals(data[1])) e.setToNode(node);
          if (node.getNodeID().equals(data[2])) e.setFromNode(node);
        }
        FDdb.getInstance().saveEdge(e);
      }
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void downloadCSV(Edge edge) {
    try {
      FileWriter fw =
          new FileWriter("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Edge.csv", false);
      PrintWriter pw = new PrintWriter(fw, false);
      pw.flush();
      pw.close();
      fw.close();
      BufferedWriter fileWriter =
          new BufferedWriter(
              new FileWriter("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Edge.csv"));
      for (Edge e : this.edges) {
        String toNodeID = "";
        String fromNodeID = "";
        for (Node n : FDdb.getInstance().getAllNodes()) {
          if (e.getToNodeID().equals(n.getNodeID())) {
            toNodeID = n.getNodeID();
          } else if (e.getFromNodeID().equals(n.getNodeID())) {
            fromNodeID = n.getNodeID();
          }
        }
        String oneObject = String.join(",", e.getEdgeID(), toNodeID, fromNodeID);
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
