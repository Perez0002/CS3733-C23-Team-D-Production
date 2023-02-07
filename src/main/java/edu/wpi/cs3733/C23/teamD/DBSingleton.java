package edu.wpi.cs3733.C23.teamD;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public enum DBSingleton {
  _DB;

  Session session;

  DBSingleton() {
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

  public Session getSession() {
    return session;
  }
}
