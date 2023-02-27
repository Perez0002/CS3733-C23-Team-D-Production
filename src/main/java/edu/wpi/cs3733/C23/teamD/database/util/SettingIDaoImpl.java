package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import edu.wpi.cs3733.C23.teamD.user.entities.Setting;
import jakarta.persistence.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.hibernate.Session;

public class SettingIDaoImpl implements IDao<Setting> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<Setting> settings = new ArrayList<>();

  public SettingIDaoImpl() {
    this.refresh();
  }

  @Override
  public Setting get(Setting s) {
    return this.settings.stream()
        .filter(setting -> s.getEmployeeID().equals(setting.getEmployeeID()))
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
              .filter(i -> this.settings.get(i).getEmployeeID().equals(s.getEmployeeID()))
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
      Query q = session.createQuery("DELETE Setting where employeeID=:id");
      q.setParameter("id", s.getEmployeeID().getEmployeeID());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      this.settings.remove(s);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void downloadCSV(Setting s) {
    try {
      File file = new File("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Setting.csv");
      FileWriter fileWriter = new FileWriter(file, false);
      for (Setting set : this.settings) {
        String oneObject =
            String.join(
                ",",
                Integer.toString(set.getEmployeeID().getEmployeeID()),
                Integer.toString(set.getConfetti()),
                Integer.toString(set.getDarkmode()));
        fileWriter.write(oneObject + "\n");
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void uploadCSV(Setting s) {
    try {
      BufferedReader fileReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Setting.csv"));
      session.beginTransaction();
      session.createQuery("DELETE FROM Setting");
      session.getTransaction().commit();
      while (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        Setting set = new Setting();
        for (Employee e : FDdb.getInstance().getAllEmployees()) {
          if (e.getEmployeeID() == Integer.parseInt(data[0])) {
            set = new Setting(e, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            break;
          }
        }
        this.update(set);
      }
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
