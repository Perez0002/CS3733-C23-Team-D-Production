package edu.wpi.cs3733.C23.teamD.databasesubsystem;

import edu.wpi.cs3733.C23.teamD.entities.ComputerServiceRequest;
import edu.wpi.cs3733.C23.teamD.entities.PatientTransportRequest;
import edu.wpi.cs3733.C23.teamD.entities.SanitationRequest;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequest;
import jakarta.persistence.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.hibernate.Session;

public class ServiceRequestIDaoImpl implements IDao<ServiceRequest> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<ServiceRequest> masterList = new ArrayList<>();
  private ArrayList<PatientTransportRequest> patientTransportRequestList = new ArrayList<>();
  private ArrayList<SanitationRequest> sanitationRequestList = new ArrayList<>();
  private ArrayList<ComputerServiceRequest> computerServiceRequests = new ArrayList<>();

  public ServiceRequestIDaoImpl() {
    this.refresh();
  }

  @Override
  public ServiceRequest get(ServiceRequest s) {
    if (s instanceof PatientTransportRequest) {
      return this.patientTransportRequestList.stream()
          .filter(
              patientTransportRequest ->
                  s.getServiceRequestId() == (patientTransportRequest.getServiceRequestId()))
          .findFirst()
          .orElse(null);
    } else if (s instanceof SanitationRequest) {
      return this.sanitationRequestList.stream()
          .filter(
              sanitationRequest ->
                  s.getServiceRequestId() == (sanitationRequest.getServiceRequestId()))
          .findFirst()
          .orElse(null);
    } else if (s instanceof  ComputerServiceRequest) {
      return this.computerServiceRequests.stream()
              .filter(
                      computerServiceRequest ->
                              s.getServiceRequestId() == (computerServiceRequest.getServiceRequestId()))
              .findFirst()
              .orElse(null);
    } else {
      return this.masterList.stream()
          .filter(
              serviceRequest -> s.getServiceRequestId() == (serviceRequest.getServiceRequestId()))
          .findFirst()
          .orElse(null);
    }
  }

  @Override
  public void save(ServiceRequest s) {
    session.beginTransaction();
    try {
      session.persist(s);
      session.getTransaction().commit();

      if (s instanceof PatientTransportRequest) {
        this.patientTransportRequestList.add((PatientTransportRequest) s);
      } else if (s instanceof SanitationRequest) {
        this.sanitationRequestList.add((SanitationRequest) s);
      } else if (s instanceof ComputerServiceRequest) {
        this.computerServiceRequests.add((ComputerServiceRequest) s);
      }

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

      int index = this.findIndexOfObj(s);

      if (s instanceof PatientTransportRequest) {
        this.patientTransportRequestList.remove(index);
        this.patientTransportRequestList.add((PatientTransportRequest) s);
      } else if (s instanceof SanitationRequest) {
        this.sanitationRequestList.remove(index);
        this.sanitationRequestList.add((SanitationRequest) s);
      } else if (s instanceof ComputerServiceRequest) {
        this.computerServiceRequests.remove(index);
        this.computerServiceRequests.add((ComputerServiceRequest) s);
      }

      int masterIndex =
          IntStream.range(0, this.masterList.size())
              .filter(
                  i -> this.masterList.get(i).getServiceRequestId() == (s.getServiceRequestId()))
              .findFirst()
              .orElse(-1);

      this.masterList.remove(masterIndex);
      this.masterList.add(s);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  @Override
  public ArrayList<ServiceRequest> getAll() {
    return this.masterList;
  }

  public ArrayList<PatientTransportRequest> getAllPatientTransportRequests() {
    return this.patientTransportRequestList;
  }

  public ArrayList<SanitationRequest> getAllSanitationRequests() {
    return this.sanitationRequestList;
  }

  public ArrayList<ComputerServiceRequest> getAllComputerRequests() {
    return this.computerServiceRequests;
  }

  @Override
  public void refresh() {
    ArrayList<ServiceRequest> javaMasterList = new ArrayList<>();
    ArrayList<PatientTransportRequest> javaPatientTransportRequestList;
    ArrayList<SanitationRequest> javaSanitationRequestList;
    ArrayList<ComputerServiceRequest> javaComputerServiceRequestList;

    session.beginTransaction();
    try {
      javaPatientTransportRequestList =
          (ArrayList<PatientTransportRequest>)
              session
                  .createQuery(
                      "SELECT p FROM PatientTransportRequest p", PatientTransportRequest.class)
                  .getResultList();
      javaSanitationRequestList =
          (ArrayList<SanitationRequest>)
              session
                  .createQuery("SELECT p FROM SanitationRequest p", SanitationRequest.class)
                  .getResultList();
      session.getTransaction().commit();
      javaComputerServiceRequestList =
              (ArrayList<ComputerServiceRequest>)
                      session
                              .createQuery("SELECT p FROM ComputerServiceRequest p", ComputerServiceRequest.class)
                              .getResultList();
      session.getTransaction().commit();

      javaMasterList.addAll(javaPatientTransportRequestList);
      javaMasterList.addAll(javaSanitationRequestList);
      javaMasterList.addAll(javaComputerServiceRequestList);

      this.patientTransportRequestList = javaPatientTransportRequestList;
      this.sanitationRequestList = javaSanitationRequestList;
      this.computerServiceRequests = javaComputerServiceRequestList;

      this.masterList = javaMasterList;

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

      if (s instanceof PatientTransportRequest) {
        this.patientTransportRequestList.remove(s);
      } else if (s instanceof SanitationRequest) {
        this.sanitationRequestList.remove(s);
      }

      this.masterList.remove(s);

    } catch (Exception ex) {
      session.getTransaction().rollback();
    }
  }

  private int findIndexOfObj(ServiceRequest s) {
    int index = -1;
    if (s instanceof PatientTransportRequest) {
      index =
          IntStream.range(0, this.patientTransportRequestList.size())
              .filter(
                  i ->
                      this.patientTransportRequestList.get(i).getServiceRequestId()
                          == (s.getServiceRequestId()))
              .findFirst()
              .orElse(-1);
    } else if (s instanceof SanitationRequest) {
      index =
          IntStream.range(0, this.sanitationRequestList.size())
              .filter(
                  i ->
                      this.sanitationRequestList.get(i).getServiceRequestId()
                          == (s.getServiceRequestId()))
              .findFirst()
              .orElse(-1);
    }

    return index;
  }
}
