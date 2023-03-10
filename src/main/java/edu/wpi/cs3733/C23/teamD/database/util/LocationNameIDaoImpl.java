package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import jakarta.persistence.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.hibernate.Session;

public class LocationNameIDaoImpl implements IDao<LocationName> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<LocationName> locationNames = new ArrayList<>();

  public LocationNameIDaoImpl() {
    this.refresh();
  }

  @Override
  public LocationName get(LocationName l) {
    return this.locationNames.stream()
        .filter(locationName -> l.getLongName().equals(locationName.getLongName()))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void save(LocationName l) {
    session.beginTransaction();
    try {
      session.persist(l);
      session.getTransaction().commit();

      this.locationNames.add(l);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  // TODO delete old and add new
  public void update(LocationName l) {
    session.beginTransaction();
    try {
      session.merge(l);
      session.getTransaction().commit();

      int index =
          IntStream.range(0, this.locationNames.size())
              .filter(i -> this.locationNames.get(i).getLongName().equals(l.getLongName()))
              .findFirst()
              .orElse(-1);

      this.locationNames.remove(index);
      this.locationNames.add(l);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  public void updatePK(LocationName oldLoc, LocationName newLoc) {
    try {
      this.delete(oldLoc);
      this.save(newLoc);
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public ArrayList<LocationName> getAll() {
    return this.locationNames;
  }

  @Override
  public void refresh() {
    ArrayList<LocationName> javaLocationNames;
    session.beginTransaction();
    try {
      javaLocationNames =
          (ArrayList<LocationName>)
              session
                  .createQuery("SELECT l FROM LocationName l", LocationName.class)
                  .getResultList();
      session.getTransaction().commit();
      this.locationNames = javaLocationNames;
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void delete(LocationName l) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("DELETE LocationName where longName=:id");
      q.setParameter("id", l.getLongName());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      this.locationNames.remove(l);
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void uploadCSV(LocationName locat) {
    try {
      BufferedReader fileReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/LocationName.csv"));
      session.beginTransaction();
      session.createQuery("DELETE FROM LocationName");
      session.getTransaction().commit();
      while (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        LocationName l =
            new LocationName(
                data[0],
                data[1].equals("empty") ? "" : data[1],
                data[2].equals("empty") ? "" : data[2]);
        FDdb.getInstance().saveLocationName(l);
      }
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void downloadCSV(LocationName locat) {
    try {
      File file = new File("src/main/resources/edu/wpi/cs3733/C23/teamD/data/LocationName.csv");
      FileWriter fileWriter = new FileWriter(file, false);
      for (LocationName l : this.locationNames) {
        String oneObject =
            String.join(
                ",",
                l.getLongName(),
                l.getShortName().equals("") ? "empty" : l.getShortName(),
                l.getLocationType().equals("") ? "empty" : l.getLocationType());
        fileWriter.write(oneObject + "\n");
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void saveKiosk(LocationName l) {
    try {
      File file = new File("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Kiosk.csv");
      FileWriter fileWriter = new FileWriter(file, false);
      String oneObject = String.join(",", l.getLongName(), l.getShortName(), l.getLocationType());
      fileWriter.write(oneObject + "\n");
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public LocationName getKiosk() {
    try {
      BufferedReader fileReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Kiosk.csv"));
      if (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        LocationName l = new LocationName(data[0], data[1], data[2]);
        fileReader.close();
        return l;
      }
      fileReader.close();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
