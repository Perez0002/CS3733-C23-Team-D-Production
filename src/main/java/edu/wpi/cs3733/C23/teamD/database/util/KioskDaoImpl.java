package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.user.entities.Kiosk;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;

public class KioskDaoImpl implements IDao<Kiosk> {
  private final Session session = DBSingleton.getSession();

  @Override
  public Kiosk get(Kiosk k) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("SELECT k From Kiosk k where IPaddress=:id");
      q.setParameter("id", k.getIPaddress());
      k = (Kiosk) q.getSingleResult();
      session.getTransaction().commit();
      return k;
    } catch (Exception e) {
      session.getTransaction().rollback();
      return null;
    }
  }

  @Override
  public void save(Kiosk k) {
    session.beginTransaction();
    try {
      session.persist(k);
      session.getTransaction().commit();
    } catch (Exception e) {
      session.getTransaction().rollback();
      e.printStackTrace();
    }
  }

  @Override
  public void update(Kiosk k) {
    session.beginTransaction();
    try {
      session.merge(k);
      session.getTransaction().commit();
    } catch (Exception e) {
      session.getTransaction().rollback();
      e.printStackTrace();
    }
  }

  @Override
  public List getAll() {
    return null;
  }

  @Override
  public void refresh() {}

  @Override
  public void delete(Kiosk k) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("DELETE Kiosk where IPaddress=:id");
      q.setParameter("id", k.getIPaddress());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void uploadCSV(Kiosk kiosk) {}

  @Override
  public void downloadCSV(Kiosk kiosk) {}
}
