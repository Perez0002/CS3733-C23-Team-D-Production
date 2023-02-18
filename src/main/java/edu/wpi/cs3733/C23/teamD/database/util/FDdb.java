package edu.wpi.cs3733.C23.teamD.database.util;

import edu.wpi.cs3733.C23.teamD.database.entities.*;
import edu.wpi.cs3733.C23.teamD.servicerequest.entities.*;
import edu.wpi.cs3733.C23.teamD.user.entities.Employee;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FDdb {
  private static final FDdb instance = new FDdb();
  private final EdgeIDaoImpl edgeIDao;
  private final NodeIDaoImpl nodeIDao;
  private final LocationNameIDaoImpl locationNameIDao;
  private final MoveIDaoImpl moveIDao;

  private final ServiceRequestIDaoImpl serviceRequestIDao;

  private final PastMovesIDaoImpl pastMovesIDao;
  private final EmployeeIDaoImpl employeeIDao;

  private FDdb() {
    this.edgeIDao = new EdgeIDaoImpl();
    this.nodeIDao = new NodeIDaoImpl();
    this.locationNameIDao = new LocationNameIDaoImpl();
    this.moveIDao = new MoveIDaoImpl();
    this.serviceRequestIDao = new ServiceRequestIDaoImpl();
    this.pastMovesIDao = new PastMovesIDaoImpl();
    this.employeeIDao = new EmployeeIDaoImpl();
  }

  public void refreshAll() {
    edgeIDao.refresh();
    nodeIDao.refresh();
    locationNameIDao.refresh();
    moveIDao.refresh();
    serviceRequestIDao.refresh();
    pastMovesIDao.refresh();
    employeeIDao.refresh();
  }

  public static FDdb getInstance() {
    return instance;
  }

  // nodeDao wrapper methods
  public Node getNode(Node n) {
    return nodeIDao.get(n);
  }

  public Node getNode(String nodeID) {
    return nodeIDao.get(nodeID);
  }

  public ArrayList<Node> getAllNodes() {
    return nodeIDao.getAll();
  }

  public void saveNode(Node n) {
    nodeIDao.save(n);
  }

  public void updateNode(Node n) {
    nodeIDao.update(n);
  }

  public void updateNodePK(Node n) {
    nodeIDao.updatePK(n);
  }

  public void deleteNode(Node n) {
    nodeIDao.delete(n);
  }

  public void nodeEdgeSwap(Node oldNode, Node newNode) {
    nodeIDao.nodeEdgeSwap(oldNode, newNode);
  }

  public void refreshNodes() {
    nodeIDao.refresh();
  }

  // edgeDao wrapper methods
  public Edge getEdge(Edge e) {
    return edgeIDao.get(e);
  }

  public ArrayList<Edge> getAllEdges() {
    return edgeIDao.getAll();
  }

  public void saveEdge(Edge e) {
    edgeIDao.save(e);
  }

  public void updateEdge(Edge e) {
    edgeIDao.update(e);
  }

  public void deleteEdge(Edge e) {
    edgeIDao.delete(e);
  }

  public void refreshEdges() {
    edgeIDao.refresh();
  }

  // locationNameDao wrapper methods
  public LocationName getNode(LocationName l) {
    return locationNameIDao.get(l);
  }

  public ArrayList<LocationName> getAllLocationNames() {
    return locationNameIDao.getAll();
  }

  public void saveLocationName(LocationName l) {
    locationNameIDao.save(l);
  }

  public void updateLocationName(LocationName l) {
    locationNameIDao.update(l);
  }

  public void deleteLocationName(LocationName l) {
    locationNameIDao.delete(l);
  }

  public void refreshLocationNames() {
    locationNameIDao.refresh();
  }

  // moveDao wrapper methods
  public Move getMove(Move m) {
    return moveIDao.get(m);
  }

  public ArrayList<Move> getAllMoves() {
    return moveIDao.getAll();
  }

  public void saveMove(Move m) {
    moveIDao.save(m);
  }

  public void updateMove(Move m) {
    moveIDao.update(m);
  }

  public void deleteMove(Move m) {
    moveIDao.delete(m);
  }

  public void refreshMoves() {
    moveIDao.refresh();
  }

  // ServiceRequestDao wrappers
  public ServiceRequest getServiceRequest(ServiceRequest s) {
    return serviceRequestIDao.get(s);
  }

  public ArrayList<ServiceRequest> getAllGenericServiceRequests() {
    return serviceRequestIDao.getAll();
  }

  public ArrayList<PatientTransportRequest> getAllPatientTransportRequests() {
    return serviceRequestIDao.getAllPatientTransportRequests();
  }

  public ArrayList<SanitationRequest> getAllSanitationRequests() {
    return serviceRequestIDao.getAllSanitationRequests();
  }

  public ArrayList<ComputerServiceRequest> getAllComputerServiceRequests() {
    return serviceRequestIDao.getAllComputerRequests();
  }

  public ArrayList<SecurityServiceRequest> getAllSecurityServiceRequests() {
    return serviceRequestIDao.getAllSecurityRequests();
  }

  public ArrayList<AVRequest> getAllAVRequests() {
    return serviceRequestIDao.getAllAVRequests();
  }

  public void saveServiceRequest(ServiceRequest s) {
    serviceRequestIDao.save(s);
  }

  public void updateServiceRequest(ServiceRequest s) {
    serviceRequestIDao.update(s);
  }

  public void deleteServiceRequest(ServiceRequest s) {
    serviceRequestIDao.delete(s);
  }

  public void refreshServiceRequests() {
    serviceRequestIDao.refresh();
  }

  // PastMovesDao wrappers
  public PastMoves getPastMove(PastMoves m) {
    return pastMovesIDao.get(m);
  }

  public ArrayList<PastMoves> getAllPastMoves() {
    return pastMovesIDao.getAll();
  }

  public void savePastMove(PastMoves m) {
    pastMovesIDao.save(m);
  }

  public void updatePastMove(PastMoves m) {
    pastMovesIDao.update(m);
  }

  public void deletePastMove(PastMoves m) {
    pastMovesIDao.delete(m);
  }

  public void refreshPastMoves() {
    pastMovesIDao.refresh();
  }

  public void downloadCSV() {
    nodeIDao.downloadCSV(new Node());
    locationNameIDao.downloadCSV(new LocationName());
    moveIDao.downloadCSV(new Move());
    edgeIDao.downloadCSV(new Edge());
  }

  public void uploadCSV() {
    nodeIDao.uploadCSV(new Node());
    locationNameIDao.uploadCSV(new LocationName());
    moveIDao.uploadCSV(new Move());
    edgeIDao.uploadCSV(new Edge());
    refreshEdges();
    refreshNodes();
    refreshMoves();
    refreshLocationNames();
  }

  // EmployeeDao wrappers
  public Employee getEmployee(Employee e) {
    return employeeIDao.get(e);
  }

  public ArrayList<Employee> getAllEmployees() {
    return employeeIDao.getAll();
  }

  public void saveEmployee(Employee e) {
    employeeIDao.save(e);
  }

  public void updateEmployee(Employee e) {
    employeeIDao.update(e);
  }

  public void deleteEmployee(Employee e) {
    employeeIDao.delete(e);
  }

  public void refreshEmployees() {
    employeeIDao.refresh();
  }

  public HashMap<Node,Move> getAllCurrentMoves(){
    HashMap<Node, Move> moveMap = new HashMap<>();
    for(Move m: getAllMoves()){
      for(Node n: getAllNodes()){
        if(m.getNode().nodeEquals(n)){
        moveMap.put(n,m);
        }
      }
    }
    return moveMap;
  }

  public ArrayList<Move> getMoveFromDay(Date date){
    return moveIDao.getMovesFromDate(date);
  }
}
