package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.Ddb;
import edu.wpi.cs3733.C23.teamD.entities.LocationName;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class LocationNameDaoImpl implements DaoPlus<LocationName>
{
   private Session session = Ddb.getDBsession();

   @Override
   public LocationName get(LocationName l) {
      LocationName lq = null;
      session.beginTransaction();
      try {
         lq = session.get(LocationName.class, l.getLongName());
         session.getTransaction().commit();
      } catch (Exception ex) {
         session.getTransaction().rollback();
      }
      return lq;
   }

   @Override
   public void save(LocationName l) {
      session.beginTransaction();
      try {
         session.persist(l);
         session.getTransaction().commit();
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
      } catch (Exception ex) {
         session.getTransaction().rollback();
      }
   }

   public void updatePK(LocationName oldLoc, LocationName newLoc) {
      session.beginTransaction();
      try {
         this.delete(oldLoc);
         this.save(newLoc);
         session.getTransaction().commit();
      } catch (Exception ex) {
         session.getTransaction().rollback();
      }
   }

   @Override
   public List<LocationName> getAll() {
      ArrayList<LocationName> javaLocationNames = null;
      session.beginTransaction();
      try {
         javaLocationNames =
                 (ArrayList<LocationName>) session.createQuery("SELECT l FROM LocationName l", LocationName.class).getResultList();
         session.getTransaction().commit();
      } catch (Exception ex) {
         session.getTransaction().rollback();
      }
      return javaLocationNames;
   }

   @Override
   public void delete(LocationName l) {
      session.beginTransaction();
      try {
         Query q = session.createQuery("DELETE LocationName where id=:id");
         q.setParameter("id", l.getLongName());
         int deleted = q.executeUpdate();
         session.getTransaction().commit();
      } catch (Exception ex) {
         session.getTransaction().rollback();
      }
   }
}
