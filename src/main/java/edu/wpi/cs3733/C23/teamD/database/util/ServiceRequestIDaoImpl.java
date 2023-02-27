package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.database.entities.LocationName;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.*;
import jakarta.persistence.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.hibernate.Session;
import org.hibernate.TransactionException;
import edu.wpi.cs3733.C23.teamD.user.entities.*;
public class ServiceRequestIDaoImpl implements IDao<ServiceRequest> {
  private final Session session = DBSingleton.getSession();

  private ArrayList<ServiceRequest> masterList = new ArrayList<>();
  private ArrayList<PatientTransportRequest> patientTransportRequestList = new ArrayList<>();
  private ArrayList<SanitationRequest> sanitationRequestList = new ArrayList<>();
  private ArrayList<ComputerServiceRequest> computerServiceRequests = new ArrayList<>();
  private ArrayList<SecurityServiceRequest> securityServiceRequests = new ArrayList<>();
  private ArrayList<AVRequest> avRequests = new ArrayList<>();

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
    } else if (s instanceof ComputerServiceRequest) {
      return this.computerServiceRequests.stream()
          .filter(
              computerServiceRequest ->
                  s.getServiceRequestId() == (computerServiceRequest.getServiceRequestId()))
          .findFirst()
          .orElse(null);
    } else if (s instanceof SecurityServiceRequest) {
      return this.securityServiceRequests.stream()
          .filter(
              securityServiceRequest ->
                  s.getServiceRequestId() == (securityServiceRequest.getServiceRequestId()))
          .findFirst()
          .orElse(null);
    } else if (s instanceof AVRequest) {
      return this.avRequests.stream()
          .filter(avRequest -> s.getServiceRequestId() == (avRequest.getServiceRequestId()))
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
      } else if (s instanceof SecurityServiceRequest) {
        this.securityServiceRequests.add((SecurityServiceRequest) s);
      } else if (s instanceof AVRequest) {
        this.avRequests.add((AVRequest) s);
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
      } else if (s instanceof SecurityServiceRequest) {
        this.securityServiceRequests.remove(index);
        this.securityServiceRequests.add((SecurityServiceRequest) s);
      } else if (s instanceof AVRequest) {
        this.avRequests.remove(index);
        this.avRequests.add((AVRequest) s);
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
      ex.printStackTrace();
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

  public ArrayList<SecurityServiceRequest> getAllSecurityRequests() {
    return this.securityServiceRequests;
  }

  public ArrayList<AVRequest> getAllAVRequests() {
    return this.avRequests;
  }

  @Override
  public void refresh() {
    ArrayList<ServiceRequest> javaMasterList = new ArrayList<>();
    ArrayList<PatientTransportRequest> javaPatientTransportRequestList;
    ArrayList<SanitationRequest> javaSanitationRequestList;
    ArrayList<ComputerServiceRequest> javaComputerServiceRequestList;
    ArrayList<SecurityServiceRequest> javaSecurityRequestList;
    ArrayList<AVRequest> javaAVRequestList;

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
      javaComputerServiceRequestList =
          (ArrayList<ComputerServiceRequest>)
              session
                  .createQuery(
                      "SELECT p FROM ComputerServiceRequest p", ComputerServiceRequest.class)
                  .getResultList();
      javaSecurityRequestList =
          (ArrayList<SecurityServiceRequest>)
              session
                  .createQuery(
                      "SELECT p FROM SecurityServiceRequest p", SecurityServiceRequest.class)
                  .getResultList();
      javaAVRequestList =
          (ArrayList<AVRequest>)
              session.createQuery("SELECT p FROM AVRequest p", AVRequest.class).getResultList();
      session.getTransaction().commit();

      javaMasterList.addAll(javaPatientTransportRequestList);
      javaMasterList.addAll(javaSanitationRequestList);
      javaMasterList.addAll(javaComputerServiceRequestList);
      javaMasterList.addAll(javaSecurityRequestList);
      javaMasterList.addAll(javaAVRequestList);

      this.patientTransportRequestList = javaPatientTransportRequestList;
      this.sanitationRequestList = javaSanitationRequestList;
      this.computerServiceRequests = javaComputerServiceRequestList;
      this.securityServiceRequests = javaSecurityRequestList;
      this.avRequests = javaAVRequestList;

      this.masterList = javaMasterList;

    } catch (Exception ex) {
      ex.printStackTrace();
      session.getTransaction().rollback();
    }
  }

  @Override
  public void delete(ServiceRequest s) {
    session.beginTransaction();
    try {
      Query q = session.createQuery("DELETE ServiceRequest where serviceRequestId=:id");
      q.setParameter("id", s.getServiceRequestId());
      int deleted = q.executeUpdate();
      session.getTransaction().commit();

      if (s instanceof PatientTransportRequest) {
        this.patientTransportRequestList.remove(s);
      } else if (s instanceof SanitationRequest) {
        this.sanitationRequestList.remove(s);
      } else if (s instanceof ComputerServiceRequest) {
        this.computerServiceRequests.remove(s);
      } else if (s instanceof SecurityServiceRequest) {
        this.securityServiceRequests.remove(s);
      } else if (s instanceof AVRequest) {
        this.avRequests.remove(s);
      }

      this.masterList.remove(s);

    } catch (TransactionException ex) {
      ex.printStackTrace();
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
    } else if (s instanceof ComputerServiceRequest) {
      index =
          IntStream.range(0, this.computerServiceRequests.size())
              .filter(
                  i ->
                      this.computerServiceRequests.get(i).getServiceRequestId()
                          == (s.getServiceRequestId()))
              .findFirst()
              .orElse(-1);
    } else if (s instanceof SecurityServiceRequest) {
      index =
          IntStream.range(0, this.securityServiceRequests.size())
              .filter(
                  i ->
                      this.securityServiceRequests.get(i).getServiceRequestId()
                          == (s.getServiceRequestId()))
              .findFirst()
              .orElse(-1);
    } else if (s instanceof AVRequest) {
      index =
          IntStream.range(0, this.avRequests.size())
              .filter(
                  i -> this.avRequests.get(i).getServiceRequestId() == (s.getServiceRequestId()))
              .findFirst()
              .orElse(-1);
    }

    return index;
  }

  @Override
  public void uploadCSV(ServiceRequest serv) {
    try {
      BufferedReader fileReader =
              new BufferedReader(
                      new FileReader(
                              "src/main/resources/edu/wpi/cs3733/C23/teamD/data/ServiceRequest.csv"));
      session.beginTransaction();
      session.createQuery("DELETE FROM ServiceRequest");
      session.getTransaction().commit();
      while (fileReader.ready()) {
        String[] data = fileReader.readLine().split(",");
        Employee emp = null;
        for (Employee e : FDdb.getInstance().getAllEmployees()) {
          if (e.getEmployeeID() == Integer.parseInt(data[3])) {
            emp = e;
            break;
          }
        }
        LocationName loc = null;
        for (LocationName l : FDdb.getInstance().getAllLocationNames()) {
          if (l.getLongName() == data[6]) {
            loc = l;
            break;
          }
        }
        ServiceRequest sans =
                new ServiceRequest(
                        Integer.parseInt(data[0]),
                        ServiceRequest.Status.valueOf(data[1].trim()),
                        emp,
                        data[4],
                        data[5],
                        loc,
                        data[6]);
        FDdb.getInstance().saveServiceRequest(sans);
      }
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void downloadCSV(ServiceRequest serv) {
    try {
      File file = new File("src/main/resources/edu/wpi/cs3733/C23/teamD/data/ServiceRequest.csv");
      FileWriter fileWriter = new FileWriter(file, false);
      for (ServiceRequest s : this.masterList) {
        fileWriter.write(
                String.join(
                        ",",
                        Integer.toString(s.getServiceRequestId()),
                        s.getServiceRequestType(),
                        s.getStat().name(),
                        Integer.toString(s.getAssociatedStaff().getEmployeeID()),
                        s.getReason(),
                        s.getServiceRequestType(),
                        s.getLocation().getLongName(),
                        s.getUrgency())
                        + "\n");
      }
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
