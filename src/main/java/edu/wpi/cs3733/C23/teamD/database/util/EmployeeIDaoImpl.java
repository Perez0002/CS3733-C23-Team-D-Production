package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import jakarta.persistence.Query;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.hibernate.Session;

public class EmployeeIDaoImpl implements IDao<Employee> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<Employee> employees = new ArrayList<>();

  public EmployeeIDaoImpl() {
    this.refresh();
  }

  @Override
  public Employee get(Employee e) {
    return this.employees.stream()
        .filter(employee -> e.getEmployeeID() == (employee.getEmployeeID()))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void save(Employee e) {
    session.beginTransaction();
    try {
      session.persist(e);
      session.getTransaction().commit();

      this.employees.add(e);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void update(Employee e) {
    session.beginTransaction();
    try {
      session.merge(e);
      session.getTransaction().commit();

      int index =
          IntStream.range(0, this.employees.size())
              .filter(i -> this.employees.get(i).getEmployeeID() == (e.getEmployeeID()))
              .findFirst()
              .orElse(-1);

      this.employees.remove(index);
      this.employees.add(e);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public ArrayList<Employee> getAll() {
    return this.employees;
  }

  @Override
  public void refresh() {
    ArrayList<Employee> javaEmployees;
    session.beginTransaction();
    try {
      javaEmployees =
          (ArrayList<Employee>)
              session.createQuery("SELECT m FROM Employee m", Employee.class).getResultList();
      session.getTransaction().commit();
      this.employees = javaEmployees;
    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void delete(Employee e) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("DELETE Employee where employeeID=:id");
      q.setParameter("id", e.getEmployeeID());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      this.employees.remove(e);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public void downloadCSV(Employee emp) {
    try {
      File file = new File("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Employee.csv");
      FileWriter fileWriter = new FileWriter(file, false);
      DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
      for (Employee employee : this.employees) {
        String oneObject =
            String.join(
                ",",
                Integer.toString(employee.getEmployeeID()),
                employee.getEmployeeType(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPassword(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                format.format(employee.getBirthday()),
                employee.getAddress());
        fileWriter.write(oneObject + "\n");
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void uploadCSV(Employee emp) {
    try {
      BufferedReader fileReader =
          new BufferedReader(
              new FileReader("src/main/resources/edu/wpi/cs3733/C23/teamD/data/Employee.csv"));
      session.beginTransaction();
      session.createQuery("DELETE FROM Setting ");
      session.createQuery("DELETE FROM Employee");
      session.getTransaction().commit();
      DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
      while (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        Employee employee =
            new Employee(
                Integer.parseInt(data[0]),
                data[1],
                data[2],
                data[3],
                data[4],
                data[5],
                data[6],
                format.parse(data[7]),
                data[8]);
        this.save(employee);
      }
      fileReader.close();
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }
}
