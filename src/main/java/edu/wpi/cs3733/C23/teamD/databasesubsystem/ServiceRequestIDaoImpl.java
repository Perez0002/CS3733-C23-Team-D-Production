package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.DBSingleton;
import edu.wpi.cs3733.C23.teamD.entities.PatientTransportRequest;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequest;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequest;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ServiceRequestIDaoImpl implements IDao<ServiceRequest> {
    private final Session session = DBSingleton.getSession();

    private ArrayList<ServiceRequest> masterList = new ArrayList<>();
    private ArrayList<PatientTransportRequest> patientTransportRequestList = new ArrayList<>();
    private ArrayList<SanitationRequest> sanitationRequestList = new ArrayList<>();

    public ServiceRequestIDaoImpl() {
        this.refresh();
    }

    @Override
    public ServiceRequest get(ServiceRequest s) {
        return this.masterList.stream().filter(ServiceRequestForm -> s.getServiceRequestId() == (ServiceRequestForm.getServiceRequestId())).findFirst().orElse(null);
    }

    @Override
    public void save(ServiceRequest s) {
        session.beginTransaction();
        try {
            session.persist(s);
            session.getTransaction().commit();

            this.masterList.add(s);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void update(ServiceRequest s) {
        session.beginTransaction();
        try {
            session.merge(s);
            session.getTransaction().commit();

            int index = IntStream.range(0, this.masterList.size())
                    .filter(i -> this.masterList.get(i).getServiceRequestId() == (s.getServiceRequestId()))
                    .findFirst()
                    .orElse(-1);

            this.masterList.remove(index);
            this.masterList.add(s);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public ArrayList<ServiceRequest> getAll() {
        return this.masterList;
    }

    @Override
    public void refresh() {
        ArrayList<ServiceRequest> javamasterList;
        session.beginTransaction();
        try {
            javamasterList =
                    (ArrayList<ServiceRequest>) session.createQuery("SELECT s FROM ServiceRequest s", ServiceRequest.class).getResultList();
            session.getTransaction().commit();
            this.masterList = javamasterList;
        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void delete(ServiceRequest s) {
        session.beginTransaction();
        try {
            Query q = session.createQuery("DELETE ServiceRequest where id=:id");
            q.setParameter("id", s.getServiceRequestId());
            int deleted = q.executeUpdate();
            session.getTransaction().commit();

            this.masterList.remove(s);

        } catch (Exception ex) {
            session.getTransaction().rollback();
        }
    }
}
