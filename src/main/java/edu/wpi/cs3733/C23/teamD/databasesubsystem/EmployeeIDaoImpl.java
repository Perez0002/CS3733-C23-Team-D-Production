package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.entities.Employee;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.stream.IntStream;

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
                    (ArrayList<Employee>) session.createQuery("SELECT m FROM Employee m", Employee.class).getResultList();
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
}
