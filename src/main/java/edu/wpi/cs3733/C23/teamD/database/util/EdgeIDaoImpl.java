package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
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
      //      System.out.println("Edge added: " + e.getEdgeID());
      //      System.out.println("Edge fromNode: " + e.getFromNode().getNodeID());
      //      System.out.println("Edge toNode: " + e.getToNode().getNodeID());
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

  public void updatePK(Edge oldEdge, Edge newEdge) {
    try {
      this.delete(oldEdge);
      this.save(newEdge);
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
      javaEdges = new ArrayList<Edge>(session.createQuery("SELECT e FROM Edge e").getResultList());
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
      //      System.out.println("Edge deleted: " + e.getEdgeID());
    } catch (Exception ex) {
      ex.printStackTrace();
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
      File file = new File("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Edge.csv");
      FileWriter fileWriter = new FileWriter(file, false);
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
        fileWriter.write(oneObject + "\n");
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
