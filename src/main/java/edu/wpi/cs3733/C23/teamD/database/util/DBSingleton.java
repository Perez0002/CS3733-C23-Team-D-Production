package edu.wpi.cs3733.C23.teamD.database.util;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DBSingleton {

  private static DBSingleton instance;
  private Session session;
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  private DBSingleton() {
    StandardServiceRegistry standardServiceRegistry =
        new StandardServiceRegistryBuilder()
            .configure("edu/wpi/cs3733/C23/teamD/hibernate.cfg.xml")
            .build();
    try {
      SessionFactory sessionFactory =
          new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
      session = sessionFactory.openSession();
    } catch (Exception e) {
      // The registry would be destroyed by the SessionFactory, but we had trouble building the
      // SessionFactory
      // so destroy it manually.
      e.printStackTrace();
      StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
    }
  }

  public static Session getSession() {
    if (instance == null) {
      instance = new DBSingleton();
      return instance.session;
    }
    try {
      // tries to query an empty table to refresh the session so it doesn't time out
      instance.session.createQuery("SELECT s from RefreshSession s").getResultList();
      return instance.session;
    } catch (Exception ex) {
      instance = new DBSingleton();
      return instance.session;
    }
  }

  // runs the query on the empty RefreshSession every 2 minutes so it doesn't time out. lasts an
  // hour
  public static void refreshSession() {
    final Runnable beeper =
        new Runnable() {
          public void run() {
            DBSingleton.getSession();
          }
        };
    final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 2, 2, MINUTES);
    scheduler.schedule(
        new Runnable() {
          public void run() {
            beeperHandle.cancel(true);
          }
        },
        60 * 60,
        SECONDS);
  }
}
