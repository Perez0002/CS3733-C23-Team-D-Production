package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.DBSingleton;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class LocationNameIDaoImpl implements IDao<LocationName> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<LocationName> locationNames = new ArrayList<>();

  public LocationNameIDaoImpl() {
    this.refresh();
  }

  @Override
  public LocationName get(LocationName l) {
    return this.locationNames.stream().filter(locationName -> l.getLongName().equals(locationName.getLongName())).findFirst().orElse(null);
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
  public void update(LocationName l) {
    session.beginTransaction();
    try {
      session.merge(l);
      session.getTransaction().commit();

      int index = IntStream.range(0, this.locationNames.size())
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

      this.locationNames.remove(oldLoc);
      this.locationNames.add(newLoc);

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
      Query q = session.createQuery("DELETE LocationName where id=:id");
      q.setParameter("id", l.getLongName());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      this.locationNames.remove(l);
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }
}
