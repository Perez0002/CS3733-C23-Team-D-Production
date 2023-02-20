package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.user.entities.Setting;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class SettingIDaoImpl implements IDao<Setting> {
    private final Session session = DBSingleton.getSession();

    private ArrayList<Setting> settings = new ArrayList<>();

    public SettingIDaoImpl() {
        this.refresh();
    }

    @Override
    public Setting get(Setting s) {
        return this.settings.stream()
                .filter(setting -> s.getEmployee().equals(setting.getEmployee()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Setting s) {
        session.beginTransaction();
        try {
            session.persist(s);
            session.getTransaction().commit();

            this.settings.add(s);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void update(Setting s) {
        session.beginTransaction();
        try {
            session.merge(s);
            session.getTransaction().commit();

            int index =
                    IntStream.range(0, this.settings.size())
                            .filter(i -> this.settings.get(i).getEmployee().equals(s.getEmployee()))
                            .findFirst()
                            .orElse(-1);

            this.settings.remove(index);
            this.settings.add(s);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public ArrayList<Setting> getAll() {
        return this.settings;
    }

    @Override
    public void refresh() {
        ArrayList<Setting> javaSettings;
        session.beginTransaction();
        try {
            javaSettings =
                    (ArrayList<Setting>)
                            session.createQuery("SELECT s FROM Setting s", Setting.class).getResultList();
            session.getTransaction().commit();
            this.settings = javaSettings;
        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Setting s) {
        session.beginTransaction();
        try {
            Query q = session.createQuery("DELETE Setting where employee=:id");
            q.setParameter("id", s.getEmployee().getEmployeeID());
            int deleted = q.executeUpdate();
            session.getTransaction().commit();

            this.settings.remove(s);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void downloadCSV(Setting s) {}

    @Override
    public void uploadCSV(Setting s) {}
}
